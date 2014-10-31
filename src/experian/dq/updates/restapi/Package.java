package experian.dq.updates.restapi;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an instance of a package.
 */
public final class Package {

    /**
     * The package code.
     */
    private final String packageCode;
    
    /**
     * The data files associated with the package.
     */
    private final List<DataFile> dataFiles;
    
    /**
     * Initializes a new instance of the Package class.
     */
    public Package(String packageCode) {
        
        this.packageCode = packageCode;		
        this.dataFiles = new ArrayList<DataFile>();
    }
    
    @Override
    public String toString(){
    	return String.format("%s", packageCode);
    }
    
    /**
     * Gets the data files in the package.
     * @return A list containing the data files in the package.
     */
    public List<DataFile> getDataFiles() {
        return this.dataFiles;
    }
    
    /**
     * Gets the package code.
     * @return The package code.
     */
    public String getPackageCode() {		
        return this.packageCode;
    }
}