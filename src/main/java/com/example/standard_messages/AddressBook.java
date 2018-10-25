package com.example.standard_messages;

import java.util.UUID;

import com.example.core.StandardMessage;
import com.example.exceptions.MessageValidationException;
import com.example.protobuf.AddressBookProtos.AddressBookMessage;
import com.google.protobuf.Message;

public class AddressBook extends StandardMessage<AddressBookMessage>
{

	public AddressBook(AddressBookMessage message) throws MessageValidationException
	{
		super(message);
	}

	@Override
	public void validate(AddressBookMessage message) throws MessageValidationException
	{
		if (message == null)
		{
			throw new MessageValidationException("Null message");
		}

		if (message.getPeopleList() == null)
		{
			throw new MessageValidationException("Null people");
		}

		if (message.getPeopleCount() < 1)
		{
			throw new MessageValidationException("No people");
		}

	}

	@Override
	public int compareTo(AddressBookMessage o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UUID getKey()
	{
		return UUID.fromString(getMessage().getId());
	}

}
