package experian.dq.updates.commands;

import java.util.List;

import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetPackageDataFilesArgs;
import experian.dq.updates.restapi.DataFile;
import experian.dq.updates.restapi.Package;
import experian.dq.updates.restapi.PackageGroup;

public class GetPackageDataFiles extends EuServiceCommand {
	
	public GetPackageDataFiles() { }
	
	/**
	 * This execute implementation gets all the data files for
	 * a given data package.  The information for a given data
	 * files can be seein in the DataFile class.
	 * 
	 * @see DataFile.java
	 */
	public String execute( EuArguments args )
	throws Exception
	{
		
		if( !(args instanceof GetPackageDataFilesArgs ) ){
			throw new IllegalArgumentException(
					"Incorrect parameters passed for command: " 
							+ this.getClass().getName()
			);
		}
		
		GetPackageDataFilesArgs commandArgs = (GetPackageDataFilesArgs) args;
		StringBuilder buf = new StringBuilder();
		
		String username = commandArgs.getUsername();
		String password = commandArgs.getPassword();
		String dataCode = commandArgs.getDataPackageCode();
		setEuService( username, password );
		
		// get list of pacakges
		List<PackageGroup> availablePackages = getEuService().getAvailablePackages();
		
		for( PackageGroup dataGroup : availablePackages ){
			List<Package> groupPackages = dataGroup.getPackages();
			
			String packageGroupCode = dataGroup.getPackageGroupCode();
			
			// Only get the data files for the specified package
			if( !packageGroupCode.equals(dataCode) )
				continue;
			
			for( Package dataPack : groupPackages ){
				List<DataFile> dataFiles = dataPack.getDataFiles();
				
				for( DataFile file : dataFiles ){
					buf.append(file.toString());
					buf.append("\n");
				}
			}
		}
		
		return buf.toString();
	}
}
