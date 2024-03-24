package ipexceptions;

public class InvalidSubnetMaskException extends IllegalArgumentException {
	
	private static  String DEFAULT_ERROR_MESSAGE = "Invalid subnet mask entered";
	
    public InvalidSubnetMaskException(String message) {
        super(message);
    }
    public InvalidSubnetMaskException() {
    	super(DEFAULT_ERROR_MESSAGE);
    }
    
}
