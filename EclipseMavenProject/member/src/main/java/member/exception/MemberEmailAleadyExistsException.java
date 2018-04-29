package member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Email exists for other member.")
public class MemberEmailAleadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 6241269462214144799L;

	public MemberEmailAleadyExistsException() {
		super();
	}

}
