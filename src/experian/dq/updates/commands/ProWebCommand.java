package experian.dq.updates.commands;

import com.qas.proweb.QasException;
import com.qas.proweb.QuickAddress;

public abstract class ProWebCommand implements EuCommand {
	
	public static final String COMMAND = "";
	
	private String wsdl;
	private QuickAddress quickAddress;
	
	public ProWebCommand( String wsdl )
	throws QasException
	{
		this.wsdl = wsdl;
	}

	@Override
	public boolean matchesCommand( String command ){
		return command.equalsIgnoreCase(COMMAND);
	}
	
	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public QuickAddress getQuickAddress() {
		return quickAddress;
	}

	public void setQuickAddress(QuickAddress quickAddress) {
		this.quickAddress = quickAddress;
	}

}
