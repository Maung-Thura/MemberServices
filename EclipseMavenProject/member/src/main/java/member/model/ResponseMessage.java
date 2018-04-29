package member.model;

public class ResponseMessage {

	private final String response;

	public ResponseMessage(String response) {
		this.response = response;
	}

	public String getMessage() {
		return this.response;
	}
}
