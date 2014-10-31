package experian.dq.updates.commands;

import java.text.ParseException;

import com.qas.proweb.QasException;

public interface EuCommand {
	
	public boolean execute() throws QasException, ParseException, Exception;
	public boolean matchesCommand( String command );
}
