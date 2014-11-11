package experian.dq.updates.commands;

import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import experian.dq.updates.EnvTestHelper;
import experian.dq.updates.commands.args.GetAvailableDataArgs;
import experian.dq.updates.commands.args.GetInstalledDataArgs;

public class GetAvailableDataTest {
	
	private static EnvTestHelper tester;
	
	@BeforeClass
	public static void setup()
	throws IOException
	{
		tester = EnvTestHelper.getInstance();
	}
	
	/**
	 * Test that calling the GetAvailableData command
	 * actually produces string output.  Presumably if an account exists
	 * the call to get available packages will make at least one
	 * data set available.
	 * @throws Exception
	 */
	@Test
	public void availableData_hasOutput()
	throws Exception
	{
		
		String username = tester.getEuUsername();
		String password = tester.getEuPassword();

		GetAvailableDataArgs args = new GetAvailableDataArgs(username, password);
		GetAvailableData command = new GetAvailableData();
		
		String output = command.execute(args);
		
		assertTrue( !output.trim().isEmpty() );
	}
	
	/**
	 * Test that passing the incorrect argument class
	 * throws an illegalArgumentException
	 */
	@Test( expected=IllegalArgumentException.class)
	public void availableData_incorrectArgClass_throwsException()
	throws Exception
	{
		GetAvailableData command = new GetAvailableData();
		String wsdl = tester.getProWebWsdlUrn();
		GetInstalledDataArgs wrongArgs = new GetInstalledDataArgs(wsdl);
		command.execute(wrongArgs);
	}
	
	
}
