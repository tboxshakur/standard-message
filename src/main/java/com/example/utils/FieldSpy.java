package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FieldSpy
{

	public static void getFields(Object obj)
	{

		for (Field f : obj.getClass().getDeclaredFields())
		{
			System.out.format("Name: %s%n", f.getName());

			System.out.format("Type: %s%n", f.getType());
			System.out.format("GenericType: %s%n", f.getGenericType());
		}

	}

	public static void getPrivateFields(Object obj)
	{
		// List<Field> privateFields = new ArrayList<>();

		for (Field f : obj.getClass().getDeclaredFields())
		{
			if (Modifier.isPrivate(f.getModifiers()))
			{
				// privateFields.add(field);
				System.out.format("Name:%s Type:%s GenericType:%s%n", f.getName(), f.getType(), f.getGenericType());

			}
		}

	}

}
