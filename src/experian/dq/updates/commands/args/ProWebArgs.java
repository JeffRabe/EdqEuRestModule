package experian.dq.updates.commands.args;

public abstract class ProWebArgs implements EuArguments {
	
	String wsdl;
	
	
	
	public ProWebArgs( String wsdl ){
		this.wsdl = wsdl;
	}

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
	
	
}
