package idv.david.member.model;

import java.util.List;

public interface MemberDAO {
	boolean isMember(String userId, String password);
	boolean isUserIdExist(String userId);
	boolean add(Member member);
	boolean update(Member member);
	boolean delete(String userid);
	Member findById(String userid);
	List<Member> getAll();
}
