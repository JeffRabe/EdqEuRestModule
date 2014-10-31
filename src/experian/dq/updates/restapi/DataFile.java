package experian.dq.updates.restapi;

/**
 * A class representing a data file.
 */
public final class DataFile {
    
    /**
     * The name of the data file.
     */
    private final String fileName;
    
    /**
     * The MD5 hash of the data file.
     */
    private final String md5Hash;
    
    /**
     * The size of the data file in bytes.
     */
    private final Long size;
    
    /**
     * Initializes a new instance of the DataFile class.
     * @param fileName The name of the file.
     * @param md5Hash The MD5 hash of the file.
     * @param size The size of the file in bytes.
     */
    public DataFile(String fileName, String md5Hash, Long size) {
        
        this.fileName = fileName;
        this.md5Hash = md5Hash;
        this.size = size;
    }
    
    @Override
    public String toString(){
    	return String.format("%s\t\t%d\t%s", this.fileName, 
    									this.size, this.md5Hash);
    }
    
    /**
     * Gets the file name.
     * @return The name of the file.
     */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Gets the MD5 hash of the file.
     * @return The MD5 hash of the file.
     */
    public String getMD5Hash() {
        return this.md5Hash;
    }
    
    /**
     * Gets the size of the file in bytes.
     * @return The size of the file in bytes
     */
    public Long getSize() {
        return this.size;
    }
}