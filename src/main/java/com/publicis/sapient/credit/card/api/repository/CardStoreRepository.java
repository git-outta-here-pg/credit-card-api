package com.publicis.sapient.credit.card.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.publicis.sapient.credit.card.api.model.CreditCardEntity;

/**
 * @author poonamgupta
 *
 */
@Repository
public interface CardStoreRepository extends JpaRepository<CreditCardEntity, Long> {

}
