package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
	private final MemberRepository memberRepository = new MemoryMemberRepository();

	// Fix -> Rate 로 할인정책 변경할 때 문제점 발생
	// private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

	// OCP, DIP를 지키고 NPE를 막기 위해서 discountPolicy에 구현객체를 누군가 넣어줘야 한다.
	private DiscountPolicy discountPolicy;

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);

		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
}
