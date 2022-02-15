package ru.tenilin.cardservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Operation {
    private String operationId;
    private String cardFrom;
    private String cardTo;
    private Long value;
    private String code;
    private Date date;
    private String currency;
}
