package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

	// 역할
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}

	// 구현
	private MemoryMemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}

	// 역할
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}

	// 구현
	public DiscountPolicy discountPolicy() {
		return new FixDiscountPolicy();
	}
}
