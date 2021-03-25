package com.publicis.sapient.credit.card.api.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

/**
 * @author poonamgupta
 *
 */
@Data
@Entity
@Table(name = "CREDIT_CARD_STORE")
public class CreditCardEntity {
	@Id
	@SequenceGenerator(name = "cardSequenceGenerator", sequenceName = "SEQ_CREDIT_CARD_STORE", allocationSize = 1)
	@GeneratedValue(generator = "cardSequenceGenerator")
	@Column(name = "CARD_ID")
    private Long cardId;
	  
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
    private UserEntity user;
    
    @Column(name = "CARD_NUMBER")
    private String cardNumber;
    
    @Column(name = "CARD_PROCESSOR")
    @Enumerated(EnumType.STRING)
    private CardProcessor cardProcessor; //VISA, MasterCard or Amex
    
    @Column(name = "CARD_EXPIRY")
    private String cardExpiry;
    
    @Column(name = "CODE")
    private Integer code;
    
    @Column(name = "CARD_LIMIT")
    private BigDecimal cardLimit;
    
    @Column(name = "CARD_BALANCE")
    private BigDecimal cardBalance;
    
    @Column(name = "CARD_STATUS")
    @Enumerated(EnumType.STRING)
    private Status cardStatus;
    
    @Column(name = "CREATED_TIMESTAMP")
	private Instant createdTimestamp;
    
    @PrePersist
	public void setTimestamps() {
		if (createdTimestamp == null) {
			createdTimestamp = Instant.now();
		}
	}
}