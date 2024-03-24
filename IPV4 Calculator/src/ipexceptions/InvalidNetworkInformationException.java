package ipexceptions;

public class InvalidNetworkInformationException extends IllegalArgumentException{
	
private static  String DEFAULT_ERROR_MESSAGE = "Invalid network information ";
	
    public InvalidNetworkInformationException(String message) {
		 super(message);
	} 
   
    
    public InvalidNetworkInformationException() {
    	super(DEFAULT_ERROR_MESSAGE);
    }  

}
