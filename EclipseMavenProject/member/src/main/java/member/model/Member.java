package member.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import member.controller.Iso8601DateSerializer;

public class Member {

	private int memberId;
	private String firstName;
	private String lastName;
	private String email;

	@JsonSerialize(using = Iso8601DateSerializer.class)
	private long createdAt;

	public Member() {

	}

	public Member(int memberId, String firstName, String lastName, String email, long createdAt) {
		this.memberId = memberId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.createdAt = createdAt;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
}
