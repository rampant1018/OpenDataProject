import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ParserTest {
	Parser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	@Test
	public void testReadFile() {
		String path = "testfile/query";
		Charset encoding = StandardCharsets.UTF_8;
		
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String expected =  encoding.decode(ByteBuffer.wrap(encoded)).toString();
		
		String result = null;
		try {
			result = parser.readFile(path, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, result);
	}

}
