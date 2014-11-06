package experian.dq.updates.commands.args;

public class GetPackageDataFilesArgs extends EuServiceArgs {

	private String dataPackageCode;
	
	public GetPackageDataFilesArgs( String username, String password,
												String dataCode ){
		super(username, password);
		
		if( dataCode == null || dataCode.trim().equals("") ){
			throw new IllegalArgumentException(
					"dataCode is required."
				);
		}
		
		this.dataPackageCode = dataCode;
	}

	public String getDataPackageCode() {
		return dataPackageCode;
	}

	public void setDataPackageCode(String dataPackageCode) {
		this.dataPackageCode = dataPackageCode;
	}
	
	
}
