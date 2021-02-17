package com.example.ecom.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
//commments
@JsonPropertyOrder({"statusCode","status","message"})
public class ErrorResponse {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("StatusCode")
    private Integer statusCode;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.getReasonPhrase();
        this.statusCode = status.value();
    }

}
