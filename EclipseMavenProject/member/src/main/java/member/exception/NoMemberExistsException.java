package member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No member exists in the system.")
public class NoMemberExistsException extends RuntimeException {

	private static final long serialVersionUID = -2973457857096292757L;

	public NoMemberExistsException() {
		super();
	}
}
