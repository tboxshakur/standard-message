package trevor;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import com.example.core.StandardMessage;
import com.example.exceptions.MessageValidationException;
import com.example.protobuf.TestFieldsProtos.TestFieldsMessage;
import com.example.protobuf.TestFieldsProtos.TestFieldsMessage.Corpus;
import com.example.protobuf.TestFieldsProtos.TestFieldsMessage.InnerMessage;
import com.example.standard_messages.TestFields;
import com.example.storage.LocalStorage;
import com.google.protobuf.ByteString;

public class TestFieldsTest
{

	@Test
	public void readWrite() throws MessageValidationException
	{

		String key = "86a8447e-2be2-4546-89c7-e01a6d308ad9";

		TestFieldsMessage message = TestFieldsMessage.newBuilder()//
				.setId(key)//
				.setBooleanTest(true)//
				.setCorpus(Corpus.IMAGES)//
				.setDoubleTest(11)//
				.setIdString(UUID.randomUUID().toString())//
				.setIntTest(22)//
				.setLongTest(33)//
				.setTestByteString(ByteString.copyFrom("Message".getBytes()))//
				.putProjects("a", Corpus.LOCAL)//
				.putProjects("b", Corpus.UNIVERSAL)//
				.addInnerMessages(InnerMessage.newBuilder()//
						.setDoubleTest(11)//
						.setIdString(UUID.randomUUID().toString())//
						.setIntTest(22)//
						.setLongTest(33)//
						.setTestByteString(ByteString.copyFrom("Message".getBytes()))//
						.build()//
				).build();//

		TestFields fields = StandardMessage.wrap(TestFields.class, message, null);

		LocalStorage.write(fields);

		TestFields obj = LocalStorage.read(TestFields.class, TestFieldsMessage.getDefaultInstance(), key, null);

		assertEquals(fields, obj);

	}

}
