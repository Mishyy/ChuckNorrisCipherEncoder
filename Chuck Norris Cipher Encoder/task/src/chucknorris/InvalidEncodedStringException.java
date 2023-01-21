package chucknorris;

public final class InvalidEncodedStringException extends IllegalArgumentException {

	public InvalidEncodedStringException() {
		super("Encoded string is not valid.");
	}

}