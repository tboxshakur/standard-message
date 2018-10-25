package trevor;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.exceptions.MessageValidationException;
import com.example.protobuf.AddressBookProtos.AddressBookMessage;
import com.example.protobuf.PersonProtos.PersonMessage;
import com.example.protobuf.PersonProtos.PersonMessage.PhoneNumberMessage;
import com.example.protobuf.PersonProtos.PersonMessage.PhoneType;
import com.example.standard_messages.AddressBook;
import com.example.storage.LocalStorage;

public class AddressBookTest
{

	private static AddressBook book;

	@BeforeClass
	public static void start() throws MessageValidationException
	{
		book = new AddressBook(AddressBookMessage.newBuilder().setId(UUID.fromString("c326b90a-50c8-498a-8e54-dd8757d736e8").toString()).addPeople(PersonMessage.newBuilder().addPhones(PhoneNumberMessage.newBuilder().setNumber("1231231234").setType(PhoneType.HOME))).build());
	}

	@Test(expected = MessageValidationException.class)
	public void test() throws MessageValidationException
	{
		new AddressBook(AddressBookMessage.newBuilder().build());
	}

	@Test
	public void testSerialization() throws MessageValidationException
	{
		LocalStorage.write(book);
		LocalStorage.read(AddressBook.class, AddressBookMessage.newBuilder(), book.getMessage().getId(), null);
	}

	@Test(expected = MessageValidationException.class)
	public void testNoId() throws MessageValidationException
	{
		AddressBook book = new AddressBook(AddressBookMessage.newBuilder().addPeople(PersonMessage.newBuilder().addPhones(PhoneNumberMessage.newBuilder().setNumber("1231231234").setType(PhoneType.HOME))).build());
		LocalStorage.write(book);
	}

	@Test
	public void readNew()
	{
		AddressBook book = LocalStorage.read(AddressBook.class, AddressBookMessage.getDefaultInstance(), "c326b90a-50c8-498a-8e54-dd8757d736e8", null);
		System.out.println(book);
	}

	@Test
	public void read() throws MessageValidationException
	{
		AddressBook book = LocalStorage.read(AddressBook.class, AddressBookMessage.getDefaultInstance(), "c326b90a-50c8-498a-8e54-dd8757d736e8", null);

		System.out.println(book.getMessage().getAllFields());

		assertEquals(AddressBookTest.book, book);

		System.out.println(book.getMessage().getId());
		System.out.format("Wrapped message: %s", book);
	}

}
