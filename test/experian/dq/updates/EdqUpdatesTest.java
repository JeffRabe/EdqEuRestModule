package experian.dq.updates;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

public class EdqUpdatesTest extends TestCase {
	
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
		String command = "GetAvailebleData";
		
		String args[] = new String[3];
		args[0] = username;
		args[1] = password;
		args[2] = command;
		
		EdqUpdates.main(args);
	}
}
