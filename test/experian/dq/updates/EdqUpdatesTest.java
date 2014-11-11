package experian.dq.updates;

import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class EdqUpdatesTest {
	
	private static EnvTestHelper tester;
	
	@BeforeClass
	public static void setup()
	throws IOException
	{
		tester = EnvTestHelper.getInstance();
	}
	
	@Test
	public void edqUpdates_getAvailableData_succeeds()
	{
		String username = tester.getEuUsername();
		String password = tester.getEuPassword();
		String command = "GetAvailableData";
		
		String args[] = new String[3];
		args[0] = command;
		args[1] = username;
		args[2] = password;
		
		
		EdqUpdates.main(args);
	}
}
