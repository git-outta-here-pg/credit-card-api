package com.publicis.sapient.credit.card.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.publicis.sapient.credit.card.api.component.impl.ServerImpl;
import com.publicis.sapient.credit.card.api.configuration.ServerMapperConfiguration;
import com.publicis.sapient.credit.card.api.dto.CardRequestBody;
import com.publicis.sapient.credit.card.api.dto.CardResponseBody;
import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.model.CreditCardEntity;
import com.publicis.sapient.credit.card.api.model.Status;
import com.publicis.sapient.credit.card.api.model.UserEntity;
import com.publicis.sapient.credit.card.api.service.CardService;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ServerTest {

	@Mock
	private CardService cardServiceMock;

	@Mock
	private ModelMapper modelMapperMock;

	private Server server;
	private ModelMapper modelMapper;

	private CardRequestBody CARDREQUESTBODY;

	private CreditCardEntity expectedCreditCardEntity;
	private UserEntity userEntity;

	@Before
	public void setup() {
		ServerMapperConfiguration serverMapperConfiguration = new ServerMapperConfiguration();
		modelMapper = serverMapperConfiguration.createModelMapperBean();
		server = new ServerImpl(cardServiceMock, modelMapperMock);

		CARDREQUESTBODY = new CardRequestBody("5196081888500645", "John Smith", CardProcessor.MASTERCARD, "12/22", 344,
				new BigDecimal(2000.00));
		expectedCreditCardEntity = modelMapper.map(CARDREQUESTBODY, CreditCardEntity.class);
		userEntity = new UserEntity();
		userEntity.setName("John Smith");
		expectedCreditCardEntity.setUser(userEntity);
	}

	@Test
	public void givenValidCardEntity_thenSave() {

		when(cardServiceMock.saveCardData(expectedCreditCardEntity)).thenReturn(true);
		when(modelMapperMock.map(CARDREQUESTBODY, CreditCardEntity.class)).thenReturn(expectedCreditCardEntity);

		boolean result = server.saveCardDetails(CARDREQUESTBODY);
		assertEquals(true, result);
	}

	@Test
	public void givenCreditCards_returnAll() {
		List<CreditCardEntity> cardEntityList = new ArrayList<>();
		CreditCardEntity cardEntityOne = creditCardEntityHelper("6250947000000015", "Mike Smith", "12/22",
				CardProcessor.VISA, new BigDecimal(2000.00), new BigDecimal(1500.00));
		CreditCardEntity cardEntityTwo = creditCardEntityHelper("6250947000000019", "Gold Smith", "10/23",
				CardProcessor.VISA, new BigDecimal(2000.00), new BigDecimal(1500.00));
		CreditCardEntity cardEntityThree = creditCardEntityHelper("6250947000000020", "Ron Smith", "06/22",
				CardProcessor.VISA, new BigDecimal(2000.00), new BigDecimal(1500.00));

		cardEntityList.add(cardEntityOne);
		cardEntityList.add(cardEntityTwo);
		cardEntityList.add(cardEntityThree);

		when(cardServiceMock.getAllCards()).thenReturn(Optional.ofNullable(cardEntityList));

		List<CardResponseBody> cardList = server.getAllCards();
		assertEquals(3, cardList.size());
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
