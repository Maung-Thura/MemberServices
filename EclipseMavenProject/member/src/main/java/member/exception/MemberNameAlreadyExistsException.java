package member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Member name already exists in the system.")
public class MemberNameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2598370066024807714L;

	public MemberNameAlreadyExistsException() {
		super();
	}

}
