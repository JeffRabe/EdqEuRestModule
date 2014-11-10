package experian.dq.updates.commands;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import experian.dq.updates.EnvTestHelper;
import experian.dq.updates.commands.args.GetInstalledDataArgs;

public class GetInstalledDataTest extends TestCase {

	private static EnvTestHelper tester;
	
	@BeforeClass
	public static void setup()
	throws IOException
	{
		tester = EnvTestHelper.getInstance();
	}
	
	@Test
	public void getInstalledData_hasOutput()
	throws Exception
	{
		String wsdl = tester.getProWebWsdlUrn();
		GetInstalledData command = new GetInstalledData();
		GetInstalledDataArgs args = new GetInstalledDataArgs(wsdl);
		
		String availableData = command.execute(args);
		assertTrue( !availableData.trim().isEmpty() );
	}
}
