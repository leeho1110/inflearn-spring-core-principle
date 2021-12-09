package hello.core.discount;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.core.member.Grade;
import hello.core.member.Member;
import jdk.jfr.DataAmount;

class RateDiscountPolicyTest {

	RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

	@Test
	@DisplayName("VIP는 10% 할인이 적용")
	void vip_o(){
		//given
		Member member = new Member(1L, "memberVIP", Grade.VIP);

		// when
		int discount = discountPolicy.discount(member, 10000);

		assertThat(discount).isEqualTo(1000);

	}

}