package com.example.standard_messages;

import com.example.core.StandardMessage;
import com.example.exceptions.MessageValidationException;
import com.example.protobuf.UserProtos.UserMessage;
import com.google.protobuf.Message;

public class User extends StandardMessage<UserMessage>
{

	public User(UserMessage message) throws MessageValidationException
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(UserMessage message) throws MessageValidationException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(UserMessage o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	

	

}
