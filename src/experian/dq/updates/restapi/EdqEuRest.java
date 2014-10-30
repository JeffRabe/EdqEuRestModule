package experian.dq.updates.restapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java sample code for the QAS Electronic Updates Metadata Web API.
 * @author Experian QAS
 */
public class EdqEuRest {

    /**
     * The user name to use to authenticate with the web service.
     */
    private static final String DEFAULT_USERNAME = "bcale";

    /**
     * The password to use to authenticate with the web service.
     */
    private static final String DEFAULT_PASSWORD = "provide123";

    /**
     * The root folder to download data to.
     */
    private static final String DEFAULT_DOWNLOAD_PATH = ".\\QASData";
    
    /**
     * The Verbose option (prints to standard out if true)
     */
    private static final boolean DEFAULT_VERBOSE = false;

    public static final String PACKAGE_ENDPOINT = "https://ws.updates.qas.com/metadata/V1/packages";
    public static final String DOWNLOAD_ENDPOINT = "https://ws.updates.qas.com/metadata/V1/filedownload";
    
    public static final Logger logback = LoggerFactory.getLogger(EdqEuRest.class); 
    
    private String username;
    private String password;
    private String downloadPath;
    private boolean isVerbose;
    
    public EdqEuRest( String username, String password, String downloadPath,
    									boolean isVerbose ){
    	this.username = username;
    	this.password = password;
    	this.downloadPath = downloadPath;
    	this.isVerbose = isVerbose;
    }
    
    public EdqEuRest( String username, String password, String downloadPath ){
    	this( username, password, downloadPath, DEFAULT_VERBOSE );
    }
    
    public EdqEuRest(){
    	this( DEFAULT_USERNAME, DEFAULT_PASSWORD, 
    								DEFAULT_DOWNLOAD_PATH, DEFAULT_VERBOSE );
    }
    
    /**
     * Calculates the MD5 hash of the specified file.
     * @param fileName The name of the file to calculate the MD5 hash of.
     * @return The MD5 hash of the specified file.
     * @throws Exception
     */
    public String calculateMD5Hash(String fileName) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream stream = new FileInputStream(fileName);

        try {

            byte[] dataBytes = new byte[1024];

            int bytesRead = 0;

            while ((bytesRead = stream.read(dataBytes)) > -1) {
                md.update(dataBytes, 0, bytesRead);
            }

            byte[] hashBytes = md.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < hashBytes.length; i++) {

                String hex = Integer.toHexString(0xff & hashBytes[i]);

                if(hex.length() == 1) {
                    sb.append('0');
                }

                sb.append(hex);
            }

            return sb.toString();
        }
        finally {
            stream.close();
        }
    }

    /**
     * Creates the credentials JSON to use to authenticate with the web service.
     * @return A JSON object containing the user's credentials.
     */
    @SuppressWarnings("unchecked")
    private JSONObject createCredentials() {

        JSONObject result = new JSONObject();

        result.put("UserName", username);
        result.put("Password", password);

        return result;
    }

    /**
     * Creates a String containing the JSON request to download the specified data file.
     * @param dataFile The data file to get the download request for.
     * @return A String containing the JSON to download the specified data file.
     */
    @SuppressWarnings("unchecked")
    private String createFileDownloadRequest(DataFile dataFile) {

        JSONObject credentials = createCredentials();

        JSONObject fileDownloadRequest = new JSONObject();
        fileDownloadRequest.put("FileName", dataFile.getFileName());
        fileDownloadRequest.put("FileMd5Hash", dataFile.getMD5Hash());

        JSONObject result = new JSONObject();

        result.put("usernamePassword", credentials);
        result.put("fileDownloadRequest", fileDownloadRequest);

        return result.toJSONString();
    }

    /**
     * Creates a HttpPost request object for the specified URI and body.
     * 
     * @param uri The URI of the POST request.
     * @param body The body of the POST request.
     * @return The created HttpPost instance.
     * @throws Exception
     */
    private HttpPost createHttpPostRequest(String uri, String body) throws Exception {

        HttpPost result = new HttpPost(uri);

        // Add the required headers
        result.addHeader("Accept", "application/json");
        result.addHeader("Content-Type", "application/json; charset=utf-8");
        result.addHeader("User-Agent", String.format("MetadataWebApi-Java/%1$s", System.getProperty("java.version")));

        StringEntity entity = new StringEntity(body);
        result.setEntity(entity);

        return result;
    }

    /**
     * Creates a String containing the JSON request to get the available package groups.
     * @return A String containing the JSON to request the available package groups.
     */
    @SuppressWarnings("unchecked")
    private String createPackagesRequest() {

        JSONObject credentials = createCredentials();

        JSONObject request = new JSONObject();
        request.put("usernamePassword", credentials);

        return request.toJSONString();
    }

    /**
     * Returns the available package groups.
     * @return The available package groups.
     * @throws Exception
     */
    public List<PackageGroup> getAvailablePackages() throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = null;
        List<PackageGroup> result = new ArrayList<PackageGroup>();
        PackageGroup packageGroup = null;
      
        try {
        	
            // Create the request JSON
            String body = createPackagesRequest();

            // Create the HTTP POST to request the available packages
            request = createHttpPostRequest( PACKAGE_ENDPOINT, body);

            	JSONArray packageGroups = getAvailablePackageGroups( httpClient, request );
                Iterator<JSONObject> packageGroupIterator = (Iterator<JSONObject>)packageGroups.iterator();

                logMessage("Available packages:");

                // Iterate through the available package groups
                while (packageGroupIterator.hasNext()) {

                    JSONObject packageGroupJson = (JSONObject)packageGroupIterator.next();

                    JSONArray packages = getGroupPackages( packageGroupJson, result );
                    Iterator<?> packageIterator = packages.iterator();

                    // Iterate through the available packages
                    while (packageIterator.hasNext()) {

                        JSONObject packageJson = (JSONObject)packageIterator.next();
                        parsePackage( packageJson, packageGroup );
 
                    }
                }
                
        } finally {
        	request.releaseConnection();
            httpClient.getConnectionManager().shutdown();
        }

        return result;
    }

    
    public void logMessage( String message )
    {
    	logback.info(message);
    	if( isVerbose ){
    		System.out.println(message);
    	}
    }
    
    public void logMessage( Exception e ){
    	logback.error( e.getMessage() );
    	
    }
    
    /**
     * Gets a URI to use to download the specified data file.
     * @param dataFile The data file to get a download URI for.
     * @return The download URI to use to download the specified data file, if available; otherwise null.
     * @throws Exception
     */
    public String getDownloadUri(DataFile dataFile) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = null;
        
        try {

            // Create the request JSON
            String body = createFileDownloadRequest(dataFile);

            // Create the HTTP POST to request a download URI for the specified file
            request = createHttpPostRequest(
                DOWNLOAD_ENDPOINT,
                body);

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                    throw new Exception(
                        String.format(
                            "Request for file download URI failed with HTTP Status %d.",
                            response.getStatusLine().getStatusCode()));
            }

            // Read the response JSON
            HttpEntity entity = response.getEntity();

            Reader reader = new BufferedReader(
            new InputStreamReader(entity.getContent()));

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject)parser.parse(reader);

            // Return the URI, if any
            return (String)json.get("DownloadUri");

        }
        finally {
        	request.releaseConnection();
            httpClient.getConnectionManager().shutdown();
        }
    }

    /*
     * Helper method to get available package groups (in JSON format)
     * from the initial request to the EU service.
     */
    private JSONArray getAvailablePackageGroups( HttpClient httpClient, HttpPost request )
    throws Exception
    {
    	
    	 // Create the request JSON
        String body = createPackagesRequest();

        // Create the HTTP POST to request the available packages
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception(
                    String.format(
                        "Request for available packages failed with HTTP Status %d.",
                        response.getStatusLine().getStatusCode()));

        }
            // Read the response JSON
        HttpEntity entity = response.getEntity();

        Reader reader = new BufferedReader(
        new InputStreamReader(entity.getContent()));

        JSONParser parser = new JSONParser();
        JSONObject packagesJson = (JSONObject)parser.parse(reader);

        JSONArray packageGroups = (JSONArray)packagesJson.get("PackageGroups");
            
            
        return packageGroups;
    }
    
    /*
     * Helper method to get the individual packages from a JSON format
     * pacakge group as parsed from the EU service.
     */
    private JSONArray getGroupPackages( JSONObject packageGroupJson, List<PackageGroup> result ){
    	 String packageGroupCode = (String)packageGroupJson.get("PackageGroupCode");
         String vintage = (String)packageGroupJson.get("Vintage");

         PackageGroup packageGroup = new PackageGroup(packageGroupCode, vintage);
         result.add(packageGroup);

         logMessage(
             String.format(
                 "Package Group Code: %1$s; Vintage: %2$s",
                 packageGroup.getPackageGroupCode(),
                 packageGroup.getVintage()));

         JSONArray packages = (JSONArray)packageGroupJson.get("Packages");
         
         return packages;
    }
    
    /*
     * Helper method to parse an individual JSON package from the EU
     * service.  This involves reading the package code, and individual
     * files and placing them into an individual package group, and
     * list of total packges.
     */
    private void parsePackage( JSONObject packageJson, PackageGroup packageGroup ){
        String packageCode = (String)packageJson.get("PackageCode");

        Package thePackage = new Package(packageCode);
        packageGroup.getPackages().add(thePackage);

        logMessage(
            String.format(
                "Package Code: %1$s",
                thePackage.getPackageCode()));

        JSONArray files = (JSONArray)packageJson.get("Files");
        Iterator<?> fileIterator = files.iterator();

        // Iterate through the data files
        while (fileIterator.hasNext()) {

            JSONObject fileJson = (JSONObject)fileIterator.next();

            String fileName = (String)fileJson.get("FileName");
            String md5Hash = (String)fileJson.get("Md5Hash");
            Long size = (Long)fileJson.get("Size");

            DataFile dataFile = new DataFile(fileName, md5Hash, size);
            thePackage.getDataFiles().add(dataFile);

            logMessage(
                String.format(
                    "Name: %1$s; MD5 Hash: %2$s; Size: %3$d",
                    dataFile.getFileName(),
                    dataFile.getMD5Hash(),
                    dataFile.getSize())
            );
        }
    }
    
    
    /*
    public static void main(String[] args) throws Exception {

        List<PackageGroup> availablePackages = getAvailablePackages();

        for (int i = 0; i < availablePackages.size(); i++) {

            PackageGroup packageGroup = availablePackages.get(i);

            for (int j = 0; j < packageGroup.getPackages().size(); j++) {

                Package thePackage = packageGroup.getPackages().get(j);

                for (int k = 0; k < thePackage.getDataFiles().size(); k++) {

                    DataFile dataFile = thePackage.getDataFiles().get(k);

                    String downloadUri = getDownloadUri(dataFile);

                    if (downloadUri != null) {

                        File rootDataDirectory = new File(rootDownloadPath);
                        File packageGroupDirectory = new File(rootDataDirectory, packageGroup.getPackageGroupCode());
                        File vintageDirectory = new File(packageGroupDirectory, packageGroup.getVintage());

                        if (!vintageDirectory.exists()) {
                            vintageDirectory.mkdirs();
                        }

                        File fileName = new File(vintageDirectory, dataFile.getFileName());

                        URL url = new URL(downloadUri);
                        
                        System.out.println(String.format("Downloading %1$s to '%2$s'...", dataFile.getFileName(), fileName.getPath()));

                        // Download the file
                        ReadableByteChannel channel = Channels.newChannel(url.openStream());
                        FileOutputStream stream = new FileOutputStream(fileName.getPath());

                        try {
                            stream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
                        }
                        finally {
                            stream.close();
                        }

                        // Validate the downloaded file is not corrupt
                        String computedHash = calculateMD5Hash(fileName.getPath());

                        if (!computedHash.equals(dataFile.getMD5Hash())) {
                            System.out.println(String.format("'%1$s' is corrupted.", fileName.getPath()));
                            fileName.delete();
                        }
                    }
                }
            }
        }
        
        System.out.println("All file(s) downloaded.");
    }
    
    */
    
   
}