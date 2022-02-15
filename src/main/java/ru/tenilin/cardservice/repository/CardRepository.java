package ru.tenilin.cardservice.repository;

import org.springframework.stereotype.Repository;
import ru.tenilin.cardservice.model.Card;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {
    private static final ConcurrentHashMap<String, Card> cardMap = new ConcurrentHashMap<>();

    public CardRepository() {
        cardMap.put("1111111111111111", new Card("1111111111111111", "12/23", "456", 1000L));
        cardMap.put("2222222222222222", new Card("2222222222222222", "06/24", "999", 10L));
    }

    public Card getCardByNumber(String number) {
        return cardMap.get(number);
    }

}
