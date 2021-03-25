package com.publicis.sapient.credit.card.api.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author poonamgupta
 *
 */
@Data
@Entity
@Table(name = "USER_STORE")
public class UserEntity {
	@Id
	@SequenceGenerator(name = "userSequenceGenerator", sequenceName = "SEQ_USER_STORE", allocationSize = 1)
	@GeneratedValue(generator = "userSequenceGenerator")
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "USER_STATUS")
	private Status userStatus;
	
	@Column(name = "CREATED_TIMESTAMP")
	private Instant createdTimestamp;
	
	@PrePersist
	public void setTimestamps() {
		if (createdTimestamp == null) {
			createdTimestamp = Instant.now();
		}
	}
}