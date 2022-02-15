package ru.tenilin.cardservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Card {
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private Long balance;

    synchronized public void addMoney(Long balance) {
        if (balance < 0 && this.balance < Math.abs(balance)) {
            throw new ArithmeticException();
        }
        this.balance += balance;
    }
}
