package com.publicis.sapient.credit.card.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonSerialize(as = PostResponse.class)
@JsonDeserialize(as = PostResponse.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	String response;
}
