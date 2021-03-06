package kr.team.ticketing.domain.reservation;

import kr.team.ticketing.domain.DomainTests;
import kr.team.ticketing.domain.object.Email;
import kr.team.ticketing.domain.object.generic.money.Money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest extends DomainTests {
	@Autowired
	private ReservationRepository reservationRepository;

	@BeforeEach
	void setup() {
		Reservation reservation = Reservation.builder()
			.memberId(1l)
			.name("김철수")
			.email(new Email("chulsu@naver.com"))
			.tel("010-2331-1233")
			.lineItems(asList(
				ReservationLineItem.builder()
					.productId(1l)
					.description("뮤즈 공연입니다.")
					.reserveOptions(asList(
						new ReservationOption("성인", Money.wons(17000)),
						new ReservationOption("청소년", Money.wons(15000)),
						new ReservationOption("유아", Money.wons(13000))
					))
					.build()
			))
			.build();

		reservationRepository.save(reservation);
		em.clear();
	}

	@DisplayName("예매 내역 삭제")
	@Test
	void findReserve() {
		Reservation dbReservation = reservationRepository.findById(1l).get();

		dbReservation.delete();
		dbReservation.getLineItems().clear();

		assertThat(dbReservation.getLineItems().size()).isEqualTo(0);
		assertThat(dbReservation.getDeleted()).isEqualTo(1);
	}
}