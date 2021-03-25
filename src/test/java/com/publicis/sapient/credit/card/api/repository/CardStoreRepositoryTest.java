package com.publicis.sapient.credit.card.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.model.CreditCardEntity;
import com.publicis.sapient.credit.card.api.model.Status;
import com.publicis.sapient.credit.card.api.model.UserEntity;

/**
 * @author poonamgupta
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CardStoreRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CardStoreRepository cardStoreRepository;

	@Test
	public void givenCardEntity_store() {

		CreditCardEntity creditCardEntity = creditCardEntityHelper();

		creditCardEntity = entityManager.persistAndFlush(creditCardEntity);

		assertEquals(creditCardEntity, cardStoreRepository.findById(creditCardEntity.getCardId()).get());
	}

	@Test
	public void givenCardStored_findAll() {

		CreditCardEntity creditCardEntity = creditCardEntityHelper();

		creditCardEntity = entityManager.persistAndFlush(creditCardEntity);

		assertNotNull(cardStoreRepository.findAll());

	}

	private static CreditCardEntity creditCardEntityHelper() {
		UserEntity userEntity = new UserEntity();
		userEntity.setName("John Smith");
		userEntity.setUserStatus(Status.ACTIVE);

		CreditCardEntity creditCardEntity = new CreditCardEntity();
		creditCardEntity.setCardBalance(new BigDecimal(1500.00));
		creditCardEntity.setCardLimit(new BigDecimal(2000.00));
		creditCardEntity.setCardExpiry("12/22");
		creditCardEntity.setCardProcessor(CardProcessor.VISA);
		creditCardEntity.setCardNumber("5196081888500645");
		creditCardEntity.setCardStatus(Status.ACTIVE);
		creditCardEntity.setUser(userEntity);
		return creditCardEntity;
	}
}