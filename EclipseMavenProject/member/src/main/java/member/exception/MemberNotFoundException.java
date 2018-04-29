package member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Member not found.")
public class MemberNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3876349593724254556L;

	public MemberNotFoundException() {
		super();
	}
}
