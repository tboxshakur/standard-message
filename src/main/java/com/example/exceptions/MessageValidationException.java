package com.example.exceptions;

public class MessageValidationException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643311726787709735L;

	public MessageValidationException(String message)
	{
		super(message);
	}

}
