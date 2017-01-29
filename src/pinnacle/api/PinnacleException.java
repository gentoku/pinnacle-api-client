package pinnacle.api;

import java.io.IOException;

/**
 * A Exception thrown by this package.
 * @author gentoku@gmail.com
 *
 */
@SuppressWarnings("serial")
public class PinnacleException extends IOException {
	
	PinnacleException (String message) {
		super(message);
	}
	
	/*
	 * Runtime Exceptions
	 * 
	 * Why not integrated as a single exception? Or why runtime exception?
	 * To use with Stream API. Checked exceptions can't be thrown in lambda function
	 * while runtime exceptions can be done. Specification of Java.
	 */
	/**
	 * This exception is thrown when mapping a response to an object 
	 * if JSON or XML document doesn't have a key Pinnacle Sports defined 
	 * as 'required' or 'not optional'.
	 */
	public static class NoNecessaryKeyException extends RuntimeException {
		
		private NoNecessaryKeyException (String message) {
			super(message);
		}
		
		static NoNecessaryKeyException of (String key) {
			return new NoNecessaryKeyException("Necessary key not found in JSON/XML: " + key);
		}
	}

	/**
	 * This exception is thrown when a value of Enum in a response is not
	 * enumerated in the list Pinnacle Sports defined.
	 */
	public static class NoEnumException extends RuntimeException {
		
		private NoEnumException (String message) {
			super(message);
		}
		
		static NoEnumException of (String enumName, String value) {
			return new NoEnumException("No [" + enumName + "] exists for the value of:" + value);
		}
	}
}
