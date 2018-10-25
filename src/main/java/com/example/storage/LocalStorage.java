package com.example.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.example.core.StandardMessage;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;

public class LocalStorage
{

	private static final File base_dir = new File(String.format("%s/messages", System.getProperty("user.home")));

	public static <T extends Message> boolean write(StandardMessage<T> message)
	{

		final File file = getStorageFile(message.getMessage(), message.getKey().toString());
		file.getParentFile().mkdirs();
		FileOutputStream output = null;
		try
		{
			output = new FileOutputStream(file);
			message.getMessage().writeTo(output);
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();

		} finally
		{
			if (output != null)
			{
				try
				{
					output.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <M extends StandardMessage<?>> M read(Class<M> standard_message_class, MessageOrBuilder m_or_b, String key, M default_value)
	{
		FileInputStream is = null;
		try
		{
			// Method m = standard_message_class.getDeclaredMethod("getdefaultInstance");
			// Message message = (Message)m.invoke(null, "");
			// System.out.println(message);

			// Field message_field = null;
			//
			// Class c = standard_message_class;
			// while (c.getSuperclass() != null && message_field == null)
			// {
			// for (Field f : c.getDeclaredFields())
			// {
			//
			// if (f.getName().equals("message"))
			// {
			// message_field = f;
			//
			// }
			//
			// }
			//
			// if (message_field == null)
			// {
			// System.out.println("No message field found in class " + c.getName());
			// } else
			// {
			// System.out.println("Found message field: " +
			// message_field.toGenericString());
			// }
			//
			// c = c.getSuperclass();
			// }

			// message_field.setAccessible(true);

			// message_field.get
			//
			// Class field_class = message_field.getType();
			//
			// Class c_ = field_class;
			// while (c_.getSuperclass() != null)
			// {
			// System.out.println(String.format("Class: %s", c_.getName()));
			// for (Method m : c_.getMethods())
			// {
			// System.out.println(String.format("%s %s", m.getName(), c_.getName()));
			// }
			// if (c_.getSimpleName().equals("MessageOrBuilder"))
			// {
			// }
			// // System.out.println("No message field found in class " + c.getName());
			// c_ = c_.getSuperclass();
			// }

			// Method method =
			// field_class.getSuperclass().getDeclaredMethod("getDefaultInstanceForType");
			//
			// method.setAccessible(true);
			// Object message_object = method.invoke(null, "doit");

			// Field f = standard_message_class.getDeclaredField("message");
			// f.setAccessible(true);
			// Class field_class = f.getType();
			// Method method = field_class.getMethod("getDefaultInstanceForType");
			// method.setAccessible(true);
			// Object message_object = method.invoke(null, null);

			Message message = m_or_b.getDefaultInstanceForType();
			is = new FileInputStream(getStorageFile(message, key));
			Constructor<M> ct = standard_message_class.getConstructor(new Class[]
			{ message.getClass() });
			Object obj = ct.newInstance(new Object[]
			{ message.getParserForType().parseFrom(is) });
			return (M) obj;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (IOException e)
				{

					e.printStackTrace();
				}
			}
		}
		return default_value;

	}

	@SuppressWarnings("unchecked")
	public static <M extends Message> M read(MessageOrBuilder m_or_b, String key, M default_value)
	{

		FileInputStream is = null;
		try
		{
			Message m = m_or_b.getDefaultInstanceForType();
			is = new FileInputStream(getStorageFile(m, key));
			return (M) m.getParserForType().parseFrom(is);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (IOException e)
				{

					e.printStackTrace();
				}
			}
		}
		return default_value;

	}

	private static File getStorageFile(Message message, String key)
	{
		return new File(String.format("%s/%s/%s.pb", base_dir.getAbsolutePath(), message.getClass().getSimpleName(), key));
	}

}
