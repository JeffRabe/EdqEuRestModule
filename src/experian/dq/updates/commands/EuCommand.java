package experian.dq.updates.commands;

import java.text.ParseException;

import com.qas.proweb.QasException;

import experian.dq.updates.commands.args.EuArguments;

public interface EuCommand {
	
	public boolean execute( EuArguments args) 
			throws QasException, ParseException, Exception;

}
