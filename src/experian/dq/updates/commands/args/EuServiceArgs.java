package experian.dq.updates.commands.args;

public abstract class EuServiceArgs implements EuArguments {
	
	private String username;
	private String password;
	
	
	public EuServiceArgs( String username, String password ){
		
		// Check for null / empt args
		if( username == null || username.trim().equals("") ){
			throw new IllegalArgumentException(
					"username is required."
				);
		}
		
		if( password == null || username.trim().equals("") ){
			throw new IllegalArgumentException(
					"password is required."
					);
		}
		
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
