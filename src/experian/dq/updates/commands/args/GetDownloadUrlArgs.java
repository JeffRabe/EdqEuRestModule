package experian.dq.updates.commands.args;

public class GetDownloadUrlArgs extends EuServiceArgs {

	private String filename;
	private String md5Hash;
	private String fileSize;
	
	public GetDownloadUrlArgs( String username, String password,
								String filename, String md5Hash,
									String fileSize ){
		super( username, password );
		
		// check for required / empty args
		if( filename == null || filename.trim().equals("") ){
			throw new IllegalArgumentException(
					"filename is required."
				);
		}
		
		if( md5Hash == null || md5Hash.trim().equals("") ){
			throw new IllegalArgumentException(
					"md5Hash is required."
				);
		}
		
		if( fileSize == null || fileSize.trim().equals("") ){
			throw new IllegalArgumentException(
					"fileSize is required."
				);
		}
		
		this.filename = filename;
		this.md5Hash = md5Hash;
		this.fileSize = fileSize;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMd5Hash() {
		return md5Hash;
	}

	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	
}
