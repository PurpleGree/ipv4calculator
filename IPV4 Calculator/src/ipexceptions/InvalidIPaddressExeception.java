package ipexceptions;

public class InvalidIPaddressExeception extends IllegalArgumentException{
	private static  String DEFAULT_ERROR_MESSAGE = "Invalid IP address entered";
	
    public InvalidIPaddressExeception(String message) {
    	super(message);
	} 
        
    public InvalidIPaddressExeception() {
    	super(DEFAULT_ERROR_MESSAGE);
	} 
}
