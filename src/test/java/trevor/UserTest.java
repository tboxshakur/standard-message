package trevor;

import org.junit.Test;

import com.example.exceptions.MessageValidationException;
import com.example.protobuf.UserProtos.UserMessage;
import com.example.standard_messages.User;
import com.example.storage.LocalStorage;

public class UserTest
{

	@Test(expected = MessageValidationException.class)
	public void testRead() throws MessageValidationException
	{
		User user = new User(UserMessage.newBuilder().setEmail("trevorjbox@gmail.com").setFirstName("Trevor").setLastName("Box").setPasswordHash("password-hash").build());

		LocalStorage.write(user);

	}

}
