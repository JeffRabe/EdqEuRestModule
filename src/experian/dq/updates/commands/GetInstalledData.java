package experian.dq.updates.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.qas.proweb.DataSet;
import com.qas.proweb.LicensedSet;
import com.qas.proweb.QasException;
import com.qas.proweb.QuickAddress;

import experian.dq.updates.commands.args.EuArguments;
import experian.dq.updates.commands.args.GetInstalledDataArgs;

/**
 * EU Command that shows the currently installed QAS Pro Web Dat Sets.
 * The Data Set Id (ISO Code) and Data vintage will be printed.
 * The data vintage is converted to the standard yyyy-mm-dd format
 * for compatibility with the EU rest service.
 * 
 * @author a06606a
 *
 */
public class GetInstalledData extends ProWebCommand {

	
	/**
	 * Print the currently installed Pro Web Data Sets line by line.
	 */
	@Override
	public boolean execute( EuArguments args )
	throws QasException, ParseException
	{
		
		// check for correct argument type for command
		if( !(args instanceof GetInstalledDataArgs ) ){
			throw new IllegalArgumentException(
					"Incorrect parameters passed for command: " 
							+ this.getClass().getName()
			);
		}
		
		// read in params
		GetInstalledDataArgs commandArgs = (GetInstalledDataArgs) args;
		
		setWsdl( commandArgs.getWsdl() );
		setQuickAddress( new QuickAddress(getWsdl()) );
		
		// execute helper method to print data info
		return getInstalledData();
	}
	
	/*
	 * Helper method to print the current data set information
	 * using QAS Pro Web.
	 */
	private boolean getInstalledData()
	throws QasException, ParseException
	{
		DataSet currentData[] = this.getQuickAddress().getAllDataSets();
		for( int i = 0; i < currentData.length; i++ ){
			DataSet data = currentData[i];
			String dataId = data.getID();
			LicensedSet dataDetail[] = 
					this.getQuickAddress().getDataMapDetail(dataId);
			
			LicensedSet set = dataDetail[0];
			String vintage = set.getVersion();
			String vintageDate = convertPwDate(vintage);
			int daysLeft = set.getDataDaysLeft();
			String dataInfo = String.format("%s\t%s\t%d", 
										dataId, vintageDate, daysLeft);
			System.out.println(dataInfo);
		}
		
		return true;
	}
	
	/*
	 * Convert the Date format returned by the Pro Web
	 * GetDataSetDetail() method to the standard yyyy-mm-dd format.
	 */
	private String convertPwDate( String dataVersion )
	throws ParseException
	{
		Date date = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(dataVersion);
		return date.toString();
	}
}
