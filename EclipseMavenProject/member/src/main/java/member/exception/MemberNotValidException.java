package member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Required fields are not populated with valid values for a member.")
public class MemberNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1623667271309197967L;

	public MemberNotValidException() {
		super();
	}

}
