package experian.dq.updates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * This class populates environment specific values 
 * (service credentials, service endpoints, etc.)
 * for this solution based on a config.properties file. 
 * @author Jeff Rabe
 *
 */
public class EnvTestHelper {

	public static final String PROPERTIES_FILE = "config.properties";
	
	private String euUsername = "";
	private String euPassword = "";
	private String proWebWsdlUrn = "";
	private Properties prop;
	
	private static EnvTestHelper instance;
	
	public static EnvTestHelper getInstance()
	throws IOException
	{
		if( instance == null )
			instance = new EnvTestHelper();
		
		return instance;
	}
	
	private EnvTestHelper()
	throws IOException
	{
		loadProperties();
	}
	
	private void loadProperties()
	throws IOException
	{
		prop = new Properties();
		FileInputStream fileStream = new FileInputStream( new File(PROPERTIES_FILE));

		prop.load(fileStream);
		euUsername = prop.getProperty("username");
		euPassword = prop.getProperty("password");
		proWebWsdlUrn = prop.getProperty("wsdl");
	}

	public String getEuUsername() {
		return euUsername;
	}

	public void setEuUsername(String euUsername) {
		this.euUsername = euUsername;
	}

	public String getEuPassword() {
		return euPassword;
	}

	public void setEuPassword(String euPassword) {
		this.euPassword = euPassword;
	}

	public String getProWebWsdlUrn() {
		return proWebWsdlUrn;
	}

	public void setProWebWsdlUrn(String proWebWsdlUrn) {
		this.proWebWsdlUrn = proWebWsdlUrn;
	}
	
	
}
