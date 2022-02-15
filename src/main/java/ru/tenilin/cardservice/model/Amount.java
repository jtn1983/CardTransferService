package ru.tenilin.cardservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Amount {
    private Long value;
    private String currency;
}
