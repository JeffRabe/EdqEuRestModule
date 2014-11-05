package experian.dq.updates;

import experian.dq.updates.commands.EuCommand;
import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetAvailableDataArgs;
import experian.dq.updates.commands.args.GetDownloadUrlArgs;
import experian.dq.updates.commands.args.GetInstalledDataArgs;
import experian.dq.updates.restapi.EdqEuRest;

public class EdqUpdates {

	public static final String USAGE = "EdqUpdates command params";
	
	private static final boolean DEBUG = true;
	
	private static EdqEuRest euService;
	private static String username;
	private static String password;
	private static String command;
	
	public static void main( String []args ){
		EdqEuRest euService = new EdqEuRest();
		
		try{
			
			parseArguments( args );
			executeCommand( args );
			
			
		}catch(Exception e){
			euService.logMessage(e);
			System.exit(1);
		}
			
		System.exit(0);
	}
	
	
	private static void parseArguments( String []args )
	throws Exception
	{
		
		if( args.length != 2 )
			throw new Exception(USAGE);
			
		if( args[0].equals("") || args[1].equals("") )
			throw new Exception(USAGE);
			
		command = args[0];
		
		
		
	}
	
	private static void executeCommand( String []args )
	throws Exception
	{
		try{
			Class<?> commandClass = Class.forName( command );
			Object commandObj = commandClass.newInstance();
			if( !(commandObj instanceof EuCommand ) ) {
				throw new IllegalArgumentException(
						"Invalid Command: " + command );
			}
			
			EuArguments commandArgs = null;
			
			switch( commandObj.getClass().getName() ){
				
				case "GetDownloadUrlArgs":
					if( !hasSufficientArgs(args, 6) )
						return;
					
					// username, password, filename, md5, filezie
					commandArgs = new GetDownloadUrlArgs( args[1], args[2],
											args[3], args[4], args[5] );
					break;
					
				case "GetInstalledDataArgs":
					if( !hasSufficientArgs(args, 2) )
						return;
					
					// wsdl
					commandArgs = new GetInstalledDataArgs(args[1]);
					break;
					
				case "GetAvailableDataArgs":
					if( !hasSufficientArgs(args, 3) )
						return;
					
					// username, password
					commandArgs = new GetAvailableDataArgs( args[1], args[2] );
					
					break;
					
				default:
					throw new IllegalArgumentException(
							"Unsupported Command: " + command
							);
				
			}
			
			EuCommand euCommand = (EuCommand) commandObj;
			euCommand.execute( commandArgs );
			
		}catch( ClassNotFoundException cnf ){
			throw new IllegalArgumentException(
					"Invalid command specified: " + command
			);
		}
	}
	
	/*
	private EuArguments getCommandArguments( String commandClassName, 
															String []args){
		
	}
	*/
	
	private static boolean hasSufficientArgs( String []args, 
													int totalRequired ){
		if( args.length < totalRequired ){
			throw new IllegalArgumentException(
					"The specified command requires more arguments: " 
					+ command 
			);
		}
		
		for( int i = 0 ; i < args.length; i++ ){
			String currentArg = args[i];
			if( currentArg.trim().equals("") ){
				throw new IllegalArgumentException(
						"The specified command does not allow empty parameters"
				);
			}
		}
		
		return true;
	}
}
