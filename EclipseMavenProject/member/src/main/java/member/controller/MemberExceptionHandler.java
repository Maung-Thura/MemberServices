package member.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import member.exception.MemberEmailAleadyExistsException;
import member.exception.MemberNameAlreadyExistsException;
import member.exception.MemberNotFoundException;
import member.exception.MemberNotValidException;
import member.exception.NoMemberExistsException;
import member.model.ResponseMessage;

@ControllerAdvice
public class MemberExceptionHandler {

	@ResponseBody
	@ExceptionHandler(MemberNotValidException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			MemberNotValidException e) {
		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Required fields are not populated with valid values for a member."),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ResponseBody
	@ExceptionHandler(MemberNameAlreadyExistsException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			MemberNameAlreadyExistsException e) {
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Member name already exists in the system."),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ResponseBody
	@ExceptionHandler(MemberEmailAleadyExistsException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			MemberEmailAleadyExistsException e) {
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Email exists for other member."),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ResponseBody
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			MemberNotFoundException e) {
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Member not found."), HttpStatus.NOT_FOUND);
	}

	@ResponseBody
	@ExceptionHandler(NoMemberExistsException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			NoMemberExistsException e) {
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("No member exists in the system."),
				HttpStatus.NOT_FOUND);
	}

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseMessage> handleServiceException(HttpServletResponse response,
			HttpMessageNotReadableException e) {
		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Server cannot process the request. Please check the URI and the parameters."),
				HttpStatus.BAD_REQUEST);
	}
}
