package experian.dq.updates.commands;

import experian.dq.updates.restapi.EdqEuRest;

public abstract class EuServiceCommand implements EuCommand {
	
	public static final String COMMAND = "";;
	
	private EdqEuRest euService;
	private String username;
	private String password;
		
	public EuServiceCommand( String username, String password )
	{
		this.username = username;
		this.password = password;
		this.euService = new EdqEuRest( username, password );
	}

	@Override
	public boolean matchesCommand( String command ){
		return command.equalsIgnoreCase(COMMAND);
	}
	
	public EdqEuRest getEuService() {
		return euService;
	}

	public void setEuService(EdqEuRest euService) {
		this.euService = euService;
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
