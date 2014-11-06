package experian.dq.updates.commands;

import experian.dq.updates.restapi.EdqEuRest;

public abstract class EuServiceCommand implements EuCommand {
	
	private EdqEuRest euService;

	public EuServiceCommand(){ }
	
	public EdqEuRest getEuService() {
		return euService;
	}

	public void setEuService(EdqEuRest euService) {
		this.euService = euService;
	}
	
	public void setEuService( String username, String password ){
		this.euService = new EdqEuRest(username, password);
	}
	
}
