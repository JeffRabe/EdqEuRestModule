package experian.dq.updates;

import experian.dq.updates.commands.EuCommand;
import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetAvailableDataArgs;
import experian.dq.updates.commands.args.GetDownloadUrlArgs;
import experian.dq.updates.commands.args.GetInstalledDataArgs;
import experian.dq.updates.commands.args.GetPackageDataFilesArgs;
import experian.dq.updates.restapi.EdqEuRest;


/**
 * Main entry point for the Java Component of the Java EU Linux solution.
 * The main method looks for a command string, and then other arguments.
 * The command string represents the class name in the
 * experian.dq.updates.commands package.
 * 
 * Different commands accept different arguments.
 * 
 * @author Jeff Rabe
 *
 */
public class EdqUpdates {

	public static final String USAGE = "EdqUpdates command [params]";

	private static String command;
	
	/************* MAIN ************************/
	public static void main( String []args ){
		EdqEuRest euService = new EdqEuRest();
		
		try{
			
			// execute wrapper method
			executeEdqUpdates( args );
			
			// Exit 0 for success to calling process
			System.exit(0);
			
		}catch(Exception e){
			euService.logMessage(e);
			System.exit(1);
		}
			
		
	}

	/**
	 * This wrapper method performs all the required operations of main,
	 * but returns a String instead of having a void signature.  This
	 * method also doesn't exit the JVM.  This methodology is used to
	 * facilitate unit testing.  Here we can rely on the string return
	 * value for unit tests, and we don't have to exit(1) or exit(0)
	 * from the JVM to indicate failure or success (repsectively)
	 * to the OS.
	 * 
	 * @param args command line arguments passed unaltered from main
	 * @return String the output of the command executed
	 * @throws Exception On parse error, web service error, or other error.
	 */
	public static String executeEdqUpdates( String []args )
	throws Exception
	{
		// Determine if the arguments are correct and valid
		parseArguments( args );
		
		// Execute the specified command
		String output = executeCommand( args );
		
		// return output
		return output;
	
	}
	
	/*
	 * Helper method to parse out the command line arguments.
	 * Basic validation occurs here.
	 */
	private static void parseArguments( String []args )
	throws Exception
	{
		
		if( args.length < 2 )
			throw new Exception(USAGE);
			
		if( args[0].equals("") || args[1].equals("") )
			throw new Exception(USAGE);
			
		command = args[0];
	}
	
	/*
	 * Helper method to execute the command specified in the arguments.
	 * 
	 */
	private static String executeCommand( String []args )
	throws Exception
	{
		try{
			String fqCommandName = "experian.dq.updates.commands." + command;
			Class<?> commandClass = Class.forName( fqCommandName );
			Object commandObj = commandClass.newInstance();
			if( !(commandObj instanceof EuCommand ) ) {
				throw new IllegalArgumentException(
						"Invalid Command: " + command );
			}
			
			// Parse the arguments to the command object
			EuArguments commandArgs = getCommandArguments( args );

			EuCommand euCommand = (EuCommand) commandObj;
			return euCommand.execute( commandArgs );
			
		}catch( ClassNotFoundException cnf ){
			throw new IllegalArgumentException(
					"Invalid command specified: " + command
			);
		}
	}
	
	/*
	 * Helper method to parse the Argument class that will be passed to the
	 * command object to be executed.  All argument classes implement
	 * EuArguments
	 * 
	 */
	private static EuArguments getCommandArguments( String []args ){
		
		switch( command ){
		
			case "GetDownloadUrl":
				if( !hasSufficientArgs(args, 6) )
					break;
				
				// username, password, filename, md5, filezie
				return new GetDownloadUrlArgs( args[1], args[2],
										args[3], args[4], args[5] );
				
			case "GetInstalledData":
				if( !hasSufficientArgs(args, 2) )
					break;
				
				// wsdl
				return new GetInstalledDataArgs(args[1]);
				
			case "GetAvailableData":
				if( !hasSufficientArgs(args, 3) )
					break;
				
				// username, password
				return new GetAvailableDataArgs( args[1], args[2] );
				
			case "GetPackageDataFiles":
				if( !hasSufficientArgs(args, 4) )
					break;
				
				return new GetPackageDataFilesArgs( args[1], 
														args[2], args[3]);
			// Default case is that we don't recognize the command
			default:	break;
				
		}
		// Get your weirdo command out of here!  Freak!
		throw new IllegalArgumentException(
				"Unsupported Command: " + command
				);
	}
	
	
	
	/*
	 * Determine if the command line arguments have sufficient
	 * parameters, and that none are empty
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
