package com.example.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message;

public class StandardMessageRegistrar
{

	public static Map<Class<? extends StandardMessage<? extends Message>>, Message> registrar = new ConcurrentHashMap<>();

	public static void put(Class<? extends StandardMessage<? extends Message>> c, Message message)
	{
		registrar.put(c, message);
	}

}
