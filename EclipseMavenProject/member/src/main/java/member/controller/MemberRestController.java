package member.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import member.dao.MemberDao;
import member.exception.MemberEmailAleadyExistsException;
import member.exception.MemberNameAlreadyExistsException;
import member.exception.MemberNotFoundException;
import member.exception.MemberNotValidException;
import member.exception.NoMemberExistsException;
import member.model.Member;
import member.model.ResponseMessage;
import member.util.MemberValidator;

@RestController
@RequestMapping("/members")
public class MemberRestController {

	@Autowired(required = true)
	private MemberDao memberDao;

	@Autowired(required = true)
	private MemberValidator memberValidator;

	@PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
	public ResponseEntity<ResponseMessage> addNewMember(@RequestBody Member newMember) {
		if (!memberValidator.isValid(newMember)) {
			throw new MemberNotValidException();
		} else if (memberDao.find(newMember.getFirstName(), newMember.getLastName()) != null) {
			throw new MemberNameAlreadyExistsException();
		} else if (memberDao.find(newMember.getEmail()) != null) {
			throw new MemberEmailAleadyExistsException();
		}

		long unixTimestamp = System.currentTimeMillis() / 1000l;
		newMember.setCreatedAt(unixTimestamp);

		newMember = memberDao.insert(newMember);
		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("A new member is created with member ID: " + newMember.getMemberId()),
				HttpStatus.CREATED);
	}

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Member>> getAllMembers() {
		Collection<Member> allMembers = memberDao.selectAll();
		if (allMembers == null || allMembers.isEmpty()) {
			throw new NoMemberExistsException();
		}
		return new ResponseEntity<Collection<Member>>(allMembers, HttpStatus.OK);
	}

	@GetMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> getMemberById(@PathVariable("memberId") int memberId) {
		Member searched = memberDao.find(memberId);
		if (searched == null) {
			throw new MemberNotFoundException();
		}
		return new ResponseEntity<Member>(searched, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> removeMemberById(@PathVariable("memberId") int memberId) {
		boolean deleted = memberDao.delete(memberId);
		if (!deleted) {
			throw new MemberNotFoundException();
		}
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Member with ID: " + memberId + " is deleted."),
				HttpStatus.OK);
	}

	@PatchMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
	public ResponseEntity<ResponseMessage> changeMemberDetailsById(@PathVariable("memberId") int memberId,
			@RequestBody Member existingMember) {
		if (!memberValidator.isValid(existingMember)) {
			throw new MemberNotValidException();
		}

		Member memberWithSameName = memberDao.find(existingMember.getFirstName(), existingMember.getLastName());

		if (memberWithSameName != null && memberWithSameName.getMemberId() != memberId) {
			throw new MemberNameAlreadyExistsException();
		}

		Member memberWithSameEmail = memberDao.find(existingMember.getEmail());
		if (memberWithSameEmail != null && memberWithSameEmail.getMemberId() != memberId) {
			throw new MemberEmailAleadyExistsException();
		}

		boolean updated = memberDao.update(memberId, existingMember);
		if (!updated) {
			throw new MemberNotFoundException();
		}

		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Member with ID: " + memberId + " is updated."),
				HttpStatus.OK);
	}

}
