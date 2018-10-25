package com.example.standard_messages;

import com.example.core.StandardMessage;
import com.example.exceptions.MessageValidationException;
import com.example.protobuf.TestFieldsProtos.TestFieldsMessage;

public class TestFields extends StandardMessage<TestFieldsMessage>
{

	public TestFields(TestFieldsMessage message) throws MessageValidationException
	{
		super(message);
	}

	@Override
	public int compareTo(TestFieldsMessage o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validate(TestFieldsMessage message) throws MessageValidationException
	{
		// TODO Auto-generated method stub
	}

}
