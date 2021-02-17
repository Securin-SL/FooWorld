package com.example.ecom.model.entity;

import com.example.ecom.common.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@JsonPropertyOrder({"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public class Audit {

    @Column(name = "CREATED_BY", updatable = false, nullable = false)
    @JsonProperty("CreatedBy")
    private String createdBy;

    @Column(name = "CREATED_DATE", updatable = false, nullable = false)
    @JsonProperty("CreatedDate")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "LAST_MODIFIED_BY", nullable = false)
    @JsonProperty("LastModifiedBy")
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    @JsonProperty("LastModifiedDate")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

}
