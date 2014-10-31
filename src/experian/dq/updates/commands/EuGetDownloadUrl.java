package experian.dq.updates.commands;

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
	
	public static final String COMMAND = "GetDownloadUri";
	
	private String filename;
	private String md5;
	long size;
	private DataFile datafile;
	
	public EuGetDownloadUrl( String username, String password, 
									String filename, String md5, String size ){
		super(username, password);
		this.filename = filename;
		this.md5 = md5;
		this.size = new Long(size);
		this.datafile = new DataFile(filename, md5, this.size);
	}
	
	public boolean execute()
	throws Exception
	{
		String downloadUri = this.getEuService().getDownloadUri(this.datafile);
		System.out.println(downloadUri);
		return true;
	}

}
