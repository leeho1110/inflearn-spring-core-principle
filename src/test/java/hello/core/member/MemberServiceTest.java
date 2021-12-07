package hello.core.member;

import java.awt.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

	MemberService memberService = new MemberServiceImpl();


	@Test
	void join(){
		// given
		Member member = new Member(1L, "memberA", Grade.VIP);

		//when
		memberService.join(member);
		Member finMember = memberService.findMember(2L);

		//then
		Assertions.assertThat(member).isEqualTo(finMember);
	}
}
