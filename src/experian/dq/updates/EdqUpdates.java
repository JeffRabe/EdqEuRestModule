package experian.dq.updates;

import java.util.List;

import experian.dq.updates.restapi.EdqEuRest;
import experian.dq.updates.restapi.PackageGroup;

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
			executeCommand();
			
			
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
			
			
		
	}
	
	private static void executeCommand()
	throws Exception
	{
		List<PackageGroup> packages = euService.getAvailablePackages();
		for( PackageGroup pack : packages ){
			System.out.println(pack);
		}
		
	}
}
