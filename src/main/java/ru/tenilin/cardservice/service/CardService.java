package ru.tenilin.cardservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tenilin.cardservice.model.Card;
import ru.tenilin.cardservice.model.Operation;
import ru.tenilin.cardservice.repository.CardRepository;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public void cardTransfer(Operation operation) {
        try {
            Card cardFrom = cardRepository.getCardByNumber(operation.getCardFrom());
            Card cardTo = cardRepository.getCardByNumber(operation.getCardTo());
            cardFrom.addMoney(-operation.getValue());
            cardTo.addMoney(operation.getValue());
        } catch (ArithmeticException e) {
            throw new TransferError("Недостаточно денег на карте");
        }
    }

    public void checkCard(String cardFromNumber, String cardToNumber, String cardFromValidTill, String cardFromCVV, Long value) {
        Card cardFrom = cardRepository.getCardByNumber(cardFromNumber);
        Card cardTo = cardRepository.getCardByNumber(cardToNumber);
        if (cardFrom == null) {throw new InvalidCard("Номер карты отправителя указан неверно");}
        if (cardTo == null) {throw new InvalidCard("Номер карты получателя указан неверно");}
        if (!cardFrom.getCardValidTill().equals(cardFromValidTill)) {
            throw new InvalidCard("Срок действия карты указан неверно");
        }
        if (!cardFrom.getCardCVV().equals(cardFromCVV)) {throw new InvalidCard("CVV код указан неверно");}
        if (cardFrom.getBalance() < value) {throw new InvalidCard("Недостаточно денег на карте");}
    }
}
