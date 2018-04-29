package member.dao;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import member.model.Member;

@Component("memberDao")
public class MemberDao {

	private int identity;
	private Map<Integer, Member> memberTable;

	public MemberDao() {
		identity = 1;
		memberTable = new Hashtable<Integer, Member>();
		populateInitialData();
	}

	public Member insert(Member newMember) {
		newMember.setMemberId(identity);
		memberTable.put(identity, newMember);
		identity++;
		return newMember;
	}

	public Collection<Member> selectAll() {
		return memberTable.values();
	}

	public Member find(int memberId) {
		return memberTable.get(memberId);
	}

	public Member find(String firstName, String lastName) {
		Set<Integer> keys = memberTable.keySet();
		for (int key : keys) {
			Member member = memberTable.get(key);
			if (member.getFirstName().equalsIgnoreCase(firstName) && member.getLastName().equalsIgnoreCase(lastName)) {
				return member;
			}
		}
		return null;
	}

	public Member find(String email) {
		Set<Integer> keys = memberTable.keySet();
		for (int key : keys) {
			Member member = memberTable.get(key);
			if (member.getEmail().equalsIgnoreCase(email)) {
				return member;
			}
		}
		return null;
	}

	public boolean delete(int memberId) {
		return memberTable.remove(memberId) != null;
	}

	public boolean update(int memberId, Member member) {
		Member existing = memberTable.get(memberId);
		if (existing != null) {
			existing.setFirstName(member.getFirstName());
			existing.setLastName(member.getLastName());
			existing.setEmail(member.getEmail());
			return true;
		}
		return false;
	}

	private void populateInitialData() {
		memberTable.put(identity, new Member(identity, "Ban", "Ki-moon", "bkmoon@un.org", 1463980143l)); // 2016-05-23T06:09+01:00
		identity++;
		memberTable.put(identity, new Member(identity, "Simon", "Raynor", "sraynor@msn.net", 1505229046l)); // 2017-09-12T16:10+01:00
		identity++;
		memberTable.put(identity, new Member(identity, "Kelly", "Rohrbach", "krohrbach@hotmail.com", 1509393010l)); // 2017-10-30T19:50Z
		identity++;
		memberTable.put(identity, new Member(identity, "Sarah", "Krill", "skrill@mailzone.com", 1515144303l)); // 2018-01-05T09:25Z
		identity++;
		memberTable.put(identity, new Member(identity, "John", "Smith", "jsmith@gmail.com", 1524942180l)); // 2018-04-28T20:03+01:00
		identity++;
	}
}
