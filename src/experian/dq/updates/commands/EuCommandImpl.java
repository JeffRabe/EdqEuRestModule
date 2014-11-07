package experian.dq.updates.commands;

import java.lang.reflect.Modifier;
import java.text.ParseException;

import com.qas.proweb.QasException;

import experian.dq.updates.commands.args.EuArguments;

public abstract class EuCommandImpl implements EuCommand{

	public abstract String execute( EuArguments args) 
					throws QasException, ParseException, Exception;
	
	public static boolean isValidCommand( String command ){
		
		Class commandClass = null;
		try{
			commandClass = getCommandClass( command );
			return true;
			
		}catch( ClassNotFoundException cnf ){
			return false;
		}
		
		
	}
	
	public static Class<?> getCommandClass( String command )
	throws ClassNotFoundException
	{
		
			Package classPackage = EuCommandImpl.class.getPackage();
			String packageName = classPackage.getName();
			String fullyQualifiedName = packageName + "." + command;
			Class<?> commandClass = Class.forName(fullyQualifiedName);
			
			if( commandClass.isInterface() ){
				throw new IllegalArgumentException(
						"The specified command is not valid.");
			}
			
			if( Modifier.isAbstract(commandClass.getModifiers()) ){
				throw new IllegalArgumentException(
						"The specified Command is not valid.");
			}
			
			return commandClass;
			
	}
	
}
