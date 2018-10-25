package com.example.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.example.exceptions.MessageValidationException;
import com.example.exceptions.UnsetKeyException;
import com.example.protobuf.JavaFieldValueProtos.JavaFieldValueMessage;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Message;

import com.googlecode.protobuf.format.JsonFormat;

public abstract class StandardMessage<T extends Message> implements Comparable<T>
{
	private UUID key;

	public UUID getKey()
	{
		return key;
	}

	@SuppressWarnings("unchecked")
	public static <M extends StandardMessage<?>> M wrap(Class<M> standard_message_class, Message message, M default_value)
	{
		try
		{
			Constructor<M> ct = standard_message_class.getConstructor(new Class[]
			{ message.getClass() });
			Object obj = ct.newInstance(new Object[]
			{ message });
			return (M) obj;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return default_value;
	}

	private T message;

	public UUID setKey(Message message) throws MessageValidationException
	{
		try
		{
			FieldDescriptor fieldDescriptor = message.getDescriptorForType().findFieldByName("id");
			Object obj = message.getField(fieldDescriptor);
			return UUID.fromString((String) obj);
		} catch (Exception e)
		{
			throw new MessageValidationException("Failed to parse UUID from field \"id\" in message!");
		}
	}

	public StandardMessage(final T message) throws MessageValidationException
	{
		this.message = normalize(message);
		this.key = setKey(getMessage());
		validate(getMessage());
	}

	/**
	 * Opportunity to create a new message from the original
	 * 
	 * @param message
	 * @return
	 */
	public T normalize(final T message)
	{
		return message;
	}

	/**
	 * Called after normalization
	 * 
	 * @param message
	 */
	public abstract void validate(final T message) throws MessageValidationException;

	public T getMessage()
	{
		return message;
	}

	public void writeStuff()
	{
		message.getAllFields().forEach((field, obj) ->
		{

			List<JavaFieldValueMessage> java_field_values = new LinkedList<>();
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try
			{
				oos = new ObjectOutputStream(bytesOut);
				oos.writeObject(obj);
				oos.flush();
				byte[] bytes = bytesOut.toByteArray();

				java_field_values.add(JavaFieldValueMessage.newBuilder().setBytes(ByteString.copyFrom(bytes)).setClassName(obj.getClass().getName()).setName(field.getFullName()).build());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(java_field_values);

			Descriptor d = field.getMessageType();

			// Class c = Class.forName(className)
			// String className = c.getName();
			// String classAsPath = className.replace('.', '/') + ".class";
			// InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath);

			System.out.println(d.getFields());

			System.out.println(String.format("%s:%s", field.getFullName(), message.getField(field)));

			System.out.format("Message Type :%s%n", field.getMessageType().getName());
			field.getMessageType().getFields().forEach(element ->
			{

				// TODO recursively loop through all types and add to a map for translation to
				// search index

				if (element.getJavaType().equals(JavaType.STRING))
				{
					System.out.format("String Type :%s%n", element.getJavaType());
				} else
				{
					System.out.format("Message Type :%s%n", element.getJavaType());
				}
			});

			// System.out.println(String.format("AllFields: %s %s", field, obj));
		});
	}

	@Override
	public String toString()
	{
		return message.toString();
	}

	public String toJSON()
	{
		return new JsonFormat().printToString(message);
	}

	public String getType()
	{
		return message.getClass().getSimpleName();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof StandardMessage))
		{
			System.out.println("not assignable from StandardMessage");
			return false;
		}

		StandardMessage<?> other = (StandardMessage<?>) obj;

		if (!Objects.equals(getMessage(), other.getMessage()))
		{
			return false;
		}

		return true;
	}

}
