package com.publicis.sapient.credit.card.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.model.CreditCardEntity;
import com.publicis.sapient.credit.card.api.model.Status;
import com.publicis.sapient.credit.card.api.model.UserEntity;
import com.publicis.sapient.credit.card.api.repository.CardStoreRepository;
import com.publicis.sapient.credit.card.api.service.impl.CardServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CardServiceTest {
	@Mock
	private CardStoreRepository cardStoreRepositoryMock;

	private CardService cardService;

	@Before
	public void setup() {
		cardService = new CardServiceImpl(cardStoreRepositoryMock);
	}

	@Test
	public void givenValidCardEntity_thenSave() {
		CreditCardEntity creditCardEntity = creditCardEntityHelper("6250947000000014", "John Smith", "11/22",
				CardProcessor.VISA, new BigDecimal(2000.00), new BigDecimal(1500.00));
		cardService.saveCardData(creditCardEntity);
		verify(cardStoreRepositoryMock, times(1)).save(eq(creditCardEntity));
	}

	@Test
	public void givenCreditCards_returnAll() {
		List<CreditCardEntity> cardList = new ArrayList<>();
		CreditCardEntity cardOne = creditCardEntityHelper("6250947000000015", "Mike Smith", "12/22", CardProcessor.VISA,
				new BigDecimal(2000.00), new BigDecimal(1500.00));
		CreditCardEntity cardTwo = creditCardEntityHelper("6250947000000019", "Gold Smith", "10/23", CardProcessor.VISA,
				new BigDecimal(2000.00), new BigDecimal(1500.00));
		CreditCardEntity cardThree = creditCardEntityHelper("6250947000000020", "Ron Smith", "06/22",
				CardProcessor.VISA, new BigDecimal(2000.00), new BigDecimal(1500.00));

		cardList.add(cardOne);
		cardList.add(cardTwo);
		cardList.add(cardThree);

		when(cardStoreRepositoryMock.findAll()).thenReturn(cardList);

		// test
		Optional<List<CreditCardEntity>> list = cardService.getAllCards();
		List<CreditCardEntity> returnList = list.get();
		assertEquals(true, list.isPresent());
		assertEquals(3, returnList.size());
		verify(cardStoreRepositoryMock, times(1)).findAll();

	}

	private static CreditCardEntity creditCardEntityHelper(String cardNumber, String name, String cardExpiry,
			CardProcessor cardProcessor, BigDecimal cardLimit, BigDecimal cardBalance) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		userEntity.setUserStatus(Status.ACTIVE);

		CreditCardEntity creditCardEntity = new CreditCardEntity();
		creditCardEntity.setCardBalance(cardBalance);
		creditCardEntity.setCardLimit(cardLimit);
		creditCardEntity.setCardExpiry(cardExpiry);
		creditCardEntity.setCardProcessor(cardProcessor);
		creditCardEntity.setCardNumber(cardNumber);
		creditCardEntity.setCardStatus(Status.ACTIVE);
		creditCardEntity.setUser(userEntity);
		return creditCardEntity;
	}
}