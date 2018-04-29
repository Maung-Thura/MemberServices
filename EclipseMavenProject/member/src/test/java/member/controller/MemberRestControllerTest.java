package member.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import member.dao.MemberDao;
import member.exception.MemberEmailAleadyExistsException;
import member.exception.MemberNameAlreadyExistsException;
import member.exception.MemberNotFoundException;
import member.exception.MemberNotValidException;
import member.model.Member;
import member.model.ResponseMessage;
import member.util.MemberValidator;

public class MemberRestControllerTest {

	private MemberRestController restController;

	@Before
	public void initialize()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		restController = new MemberRestController();
		Field memberDao = MemberRestController.class.getDeclaredField("memberDao");
		memberDao.setAccessible(true);
		memberDao.set(restController, new MemberDao());
		Field memberValidator = MemberRestController.class.getDeclaredField("memberValidator");
		memberValidator.setAccessible(true);
		memberValidator.set(restController, new MemberValidator());
	}

	@Test
	public void testAddNewMember() {
		Member newMember = new Member(1, "James", "Bond", "jbond@mi5.gove.uk", getUnixTime());
		ResponseEntity<ResponseMessage> response = restController.addNewMember(newMember);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test(expected = MemberNotValidException.class)
	public void testMemberNotValidException() {
		Member newMember = new Member(1, "", "Bond", "jbond@mi5.gove.uk", getUnixTime());
		restController.addNewMember(newMember);
	}

	@Test(expected = MemberNotValidException.class)
	public void testMemberEmailNotValidException() {
		Member newMember = new Member(1, "James", "Bond", "jbond[at]mi5", getUnixTime());
		restController.addNewMember(newMember);
	}

	@Test(expected = MemberNameAlreadyExistsException.class)
	public void testMemberNameAlreadyExistsException() {
		Member newMember = new Member(1, "James", "Bond", "jbond@mi5.gove.uk", getUnixTime());
		restController.addNewMember(newMember);
		restController.addNewMember(newMember);
	}

	@Test(expected = MemberEmailAleadyExistsException.class)
	public void testMemberEmailAleadyExistsExceptionn() {
		Member newMember = new Member(1, "James", "Bond", "jbond@mi5.gove.uk", getUnixTime());
		restController.addNewMember(newMember);
		newMember = new Member(1, "Eve", "Moneypenny", "jbond@mi5.gove.uk", getUnixTime());
		restController.addNewMember(newMember);
	}

	@Test
	public void testSettingUnixTime() {
		Member newMember = new Member(0, "James", "Bond", "jbond@mi5.gove.uk", 0l);
		restController.addNewMember(newMember);
		ResponseEntity<Collection<Member>> response = restController.getAllMembers();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		long unixTimestamp = 0l;
		for (Member m : response.getBody()) {
			if (m.getFirstName().equalsIgnoreCase(newMember.getFirstName())
					&& m.getLastName().equalsIgnoreCase(newMember.getLastName())) {
				unixTimestamp = m.getCreatedAt();
				break;
			}
		}
		assertTrue(unixTimestamp > 0l);
	}

	@Test(expected = MemberNotFoundException.class)
	public void testMemberNotFoundException() {
		restController.getMemberById(3000);
	}

	@Test
	public void testUpdatingMemberDetailsById() {
		ResponseEntity<Collection<Member>> response = restController.getAllMembers();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());

		int totalRecordsBeforeUpdate = response.getBody().size();
		Member update = response.getBody().iterator().next();
		int memberId = update.getMemberId();
		String newFirstName = String.valueOf(System.currentTimeMillis());
		update.setFirstName(newFirstName);
		restController.changeMemberDetailsById(memberId, update);

		response = restController.getAllMembers();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(totalRecordsBeforeUpdate, response.getBody().size());

		ResponseEntity<Member> responseSingleMember = restController.getMemberById(memberId);
		assertNotNull(responseSingleMember);
		assertEquals(HttpStatus.OK, responseSingleMember.getStatusCode());
		assertNotNull(responseSingleMember.getBody());
		assertEquals(newFirstName, responseSingleMember.getBody().getFirstName());
	}

	@Test(expected = MemberNotFoundException.class)
	public void testRemovingMemberById() {
		ResponseEntity<Collection<Member>> response = restController.getAllMembers();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());

		int totalRecordsBeforeUpdate = response.getBody().size();
		Member delete = response.getBody().iterator().next();
		int memberId = delete.getMemberId();
		restController.removeMemberById(memberId);

		response = restController.getAllMembers();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(totalRecordsBeforeUpdate - 1, response.getBody().size());

		restController.getMemberById(memberId);
	}

	private long getUnixTime() {
		return System.currentTimeMillis() / 1000l;
	}
}
