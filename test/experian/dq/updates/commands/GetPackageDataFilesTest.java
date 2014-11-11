package experian.dq.updates.commands;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import experian.dq.updates.EnvTestHelper;
import experian.dq.updates.commands.args.GetPackageDataFilesArgs;
import experian.dq.updates.restapi.EdqEuRest;
import experian.dq.updates.restapi.PackageGroup;
import experian.dq.updates.restapi.Package;

public class GetPackageDataFilesTest {

	private static EnvTestHelper tester;
	
	@BeforeClass
	public static void starup()
	throws Exception
	{
		tester = EnvTestHelper.getInstance();
	}
	
	/**
	 * Test the case that making a request for the data files
	 * of a valid package actually returns output.
	 * 
	 * @throws Exception
	 */
	@Test
	public void GetPackageDataFiles_hasOuptut()
	throws Exception
	{
		String username = tester.getEuUsername();
		String password = tester.getEuPassword();
		
		EdqEuRest euService = new EdqEuRest( username, password );
		List<PackageGroup> packageGroups = euService.getAvailablePackages();
		PackageGroup packageGroup = packageGroups.get(0);
		List<Package> packages = packageGroup.getPackages();
		
		Package dataPack = packages.get(0);
		String dataId = dataPack.getPackageCode();
		GetPackageDataFiles command = new GetPackageDataFiles();
		GetPackageDataFilesArgs args = 
				new GetPackageDataFilesArgs( username, password, dataId );
		
		String output = command.execute(args);
		assertTrue( !output.trim().isEmpty() );
	}
}
