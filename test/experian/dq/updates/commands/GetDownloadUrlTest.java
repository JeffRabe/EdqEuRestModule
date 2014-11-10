package experian.dq.updates.commands;

import java.util.List;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import experian.dq.updates.EnvTestHelper;
import experian.dq.updates.commands.args.GetDownloadUrlArgs;
import experian.dq.updates.restapi.DataFile;
import experian.dq.updates.restapi.EdqEuRest;
import experian.dq.updates.restapi.Package;
import experian.dq.updates.restapi.PackageGroup;

public class GetDownloadUrlTest extends TestCase {

	private static EnvTestHelper tester;
	
	@BeforeClass
	public static void starup()
	throws Exception
	{
		tester = EnvTestHelper.getInstance();
	}
	
	/**
	 * Test that a making a request for a datafile download URL
	 * actually returns a result.  This test is performed by
	 * actually making a request for a data pacakge, then
	 * selecting a file from that package as the GetDataFileUrl
	 * argument.
	 * @throws Exception
	 */
	@Test
	public void getDownloadUrl_hasOutput()
	throws Exception
	{
		
		String username = tester.getEuUsername();
		String password = tester.getEuPassword();
		EdqEuRest euService = new EdqEuRest( username, password );
		
		List<PackageGroup> packageGroups = euService.getAvailablePackages();
		
		//(packageGroups.size() > 0 );
		
		PackageGroup firstPackage = packageGroups.get(1);
		
		List<Package> packages = firstPackage.getPackages();
		Package firstPack = packages.get(0);
		List<DataFile> dataFiles = firstPack.getDataFiles();
		DataFile firstFile = dataFiles.get(0);
		String filename = firstFile.getFileName();
		String md5Hash = firstFile.getMD5Hash();
		String fileSize = String.valueOf( firstFile.getSize() );
		
		EuGetDownloadUrl command = new EuGetDownloadUrl();
		GetDownloadUrlArgs args = 
						new GetDownloadUrlArgs(username, password, filename,
											md5Hash, fileSize );
		
		String downloadUrl = command.execute(args);
		assertTrue( !downloadUrl.trim().isEmpty() );
	}
}
