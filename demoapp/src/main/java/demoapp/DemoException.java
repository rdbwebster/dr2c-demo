package demoapp;

public class DemoException extends Exception {
 
    /**
	 * DemoException is an application Exception used to wrap and standardize Exceptions thrown by different 
	 * REST client implementations.
	 */
	
	private static final long serialVersionUID = 7560631685410911503L;
	private String message = null;
 
    public DemoException() {
        super();
    }
 
    public DemoException(String message) {
        super(message);
        this.message = message;
    }
 
    public DemoException(Throwable cause) {
        super(cause);
    }
    
    public DemoException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
