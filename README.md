# credit-card-api

This implementation covers the primary requirements listed, and I will go into the details in a bit. Let's begin with my design considerations and I'd also like to list down my assumptions :
 - Based on the requirement I can identify two entities, CARD and USER. Each USER can have many CARDS. CARDS are unique in the system and I have used credit card number as UNIQUE KEY in the CREDIT_CARD_STORE table, to avoid any duplicates. To limit the scope of implementation I have kept the USER_STORE table fairly lean and simple. Though if I have to extend this system, I would collect a unqiue identifier for the user and maintain the USER to CARD relationship. This will help with redundancy and also help with unique user identification.
 - From regulatory point of view, Credit Card Number, CVV are categorised as PII or PCI and I would not store this information in database as plain text. I would implement an encrypt and decrypt function that would be used to encrypt the information before it is stored and decrypt when it needs to be presented on the UI (would hide some digits on the UI as well and not return the complete credit card number). The key for encrypting and decrypting would be managed by Hashicorp Vault or any other equivalent technology. Again to implement the scope of work involved, I am currently storing the dummy values as plain text in the database
 - For the limiting the scope of work to be done - I have assumed that every time a request to add a new card is received, regardless if the user exists in the system, a new entry is added in the CREDIT_CARD_STORE (provided the credit card number is unique) and corresponding USER_TABLE
 - For implementing getAll, I would choose pagination and allow the user to consume the data in pages rather than comprising the response time. This currently is implemented as a simple get without pagination
 - The easy access to documentation I would configure swagger, which I have in this implementation. Swagger also facilitates testing the apis to the enduser. Swagger libraries can be implemented with this app to provide good documentation support.
 - HTTPS communication can be enabled on the spring boot app using a self signed certificate (limited to dev-test env) or certificate signed by CA. Since it is recommended to manage the certificates and the key outside the web application implementation, this is something that should be configured in the deployment environment using ansible or docker-compose
 - I have also enabled the spring-boot-actuator that provided 2 unique end point that can be used to configure application health and liveness probings

Implementation
Two REST Endpoints are be implemented
-	"Add" will create a new credit card for a given name, card number, limit, expiry date, cvv etc
	•	Card numbers are validated using Luhn 10 (Please see the LuhnValidator)
  • Credit card numbers may vary in length, up to 19 characters
	•	Credit card numbers are checked for alphabets and special character
	•	New cards start with a £0 balance
	• I am returning a 400 bad request for cards not compatible with Luhn 10
- "Get all" returns all cards in the system
- I have accounted to version of the apis, the v1 apis are hosted on /api/v1
- You can import this as a maven project and run in your IDE as java application 
- I have checked in a dockerfile so you can create an image and push it to an artifactory 
- The unit test are written using Junit4
- I have added a sql file by name schema.sql, which has the table creation commands

```Sample POST payload 
POST endpoint - http://localhost:8090/api/v1/card
Request -
{
  "creditCardNumber": "5196081888500645",
  "cardHolderName": "Poonam",
  "creditCardProcessor": "VISA",
  "creditCardExpiry": "11/22",
  "code": 123,
  "creditCardLimit": 101
}
Response -
{
    "response": "Card added successfully"
}

Sample GET  
GET endpoint - http://localhost:8090/api/v1/cards
Response -
[
    {
        "creditCardNumber": "5196081888500645",
        "cardHolderName": "Poonam",
        "creditCardProcessor": "VISA",
        "creditCardBalance": 0,
        "creditCardLimit": 101
    },
    {
        "creditCardNumber": "6250947000000014",
        "cardHolderName": "Poonam",
        "creditCardProcessor": "MASTERCARD",
        "creditCardBalance": 0,
        "creditCardLimit": 10001
    }
]

Thanks 
