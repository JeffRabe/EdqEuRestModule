package experian.dq.updates.commands;

import com.qas.proweb.QasException;
import com.qas.proweb.QuickAddress;

public abstract class ProWebCommand implements EuCommand {
	
	private String wsdl;
	private QuickAddress quickAddress;
	
	public ProWebCommand() { }
	
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
