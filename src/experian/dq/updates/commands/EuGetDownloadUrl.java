package experian.dq.updates.commands;

import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetDownloadUrlArgs;
import experian.dq.updates.restapi.DataFile;

/**
 * This command class prints the download Url for a given data file.
 * The command needs the filename, md5 hash, and file size to be
 * specified.
 * 
 * The URI can be used for an HTTP GET to download the file.
 * 
 * @author Jeff Rabe
 *
 */
public class EuGetDownloadUrl extends EuServiceCommand {
	
	private String filename;
	private String md5;
	long size;
	private DataFile datafile;
	
	public EuGetDownloadUrl() { }
	
	public String execute( EuArguments args )
	throws Exception
	{
		
		if( !( args instanceof GetDownloadUrlArgs ) ){
			throw new IllegalArgumentException(
					"Incorrect parameters passed for command: " 
							+ this.getClass().getName()
			);
		}
		
		GetDownloadUrlArgs commandArgs = (GetDownloadUrlArgs) args;
		
		
		try{
		
			String username = commandArgs.getUsername();
			String password = commandArgs.getPassword();
			setEuService( username, password );
			
			this.filename = commandArgs.getFilename();
			this.md5 = commandArgs.getMd5Hash();
			this.size = Long.parseLong( commandArgs.getFileSize() );
			this.datafile = new DataFile(filename, md5, size );
			
			String downloadUri = this.getEuService().getDownloadUri(this.datafile);
			return downloadUri;
			
		}catch( NumberFormatException nfe ){
			throw new IllegalArgumentException(
					"The specified arguments contain an invalid file size: "
					+ commandArgs.getFileSize()
				);
			
		}
	}

}
