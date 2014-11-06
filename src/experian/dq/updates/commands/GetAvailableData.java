package experian.dq.updates.commands;

import java.util.List;

import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetAvailableDataArgs;
import experian.dq.updates.restapi.PackageGroup;

public class GetAvailableData extends EuServiceCommand
{
	
	public GetAvailableData() { }
	
	public boolean execute( EuArguments args )
	throws Exception
	{
		
		if( !(args instanceof GetAvailableDataArgs) ){
			throw new IllegalArgumentException(
					"Incorrect parameters passed for command: " 
							+ this.getClass().getName()
			);
		}
		
		GetAvailableDataArgs commandArgs = (GetAvailableDataArgs) args;
		String username = commandArgs.getUsername();
		String password = commandArgs.getPassword();
		
		setEuService(username, password);
		List<PackageGroup> availablePackages = getEuService().getAvailablePackages();
		for( PackageGroup dataPack : availablePackages ){
			System.out.println( dataPack );
		}
		
		return true;
	}

}
