package auth;

public class FacebookErrorException extends Exception {
	
	private int code;
	private String message;
	
	public FacebookErrorException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public FacebookErrorException() { }
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}