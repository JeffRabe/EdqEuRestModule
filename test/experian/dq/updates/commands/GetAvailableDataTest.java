package experian.dq.updates.commands;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import experian.dq.updates.EnvTestHelper;
import experian.dq.updates.commands.args.GetAvailableDataArgs;

public class GetAvailableDataTest {
	
	private EnvTestHelper tester;
	
	@BeforeClass
	public void setup()
	throws IOException
	{
		tester = EnvTestHelper.getInstance();
	}
	
	@Test
	public void availableData_hasOutput()
	throws Exception
	{
		
		String username = tester.getEuUsername();
		String password = tester.getEuPassword();

		GetAvailableDataArgs args = new GetAvailableDataArgs(username, password);
		GetAvailableData command = new GetAvailableData();
		
		String output = command.execute(args);
		
		assert( !output.isEmpty() );
	}
}
