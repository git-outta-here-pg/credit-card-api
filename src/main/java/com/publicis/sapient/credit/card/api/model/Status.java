package com.publicis.sapient.credit.card.api.model;

/**
 * @author poonamgupta
 *
 */
public enum Status {
	
	ACTIVE("active"), DISABLED("disabled"), BLOCKED("blocked");
	
	private String status;

	Status(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }

}
