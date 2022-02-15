package ru.tenilin.cardservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import ru.tenilin.cardservice.model.Card;
import ru.tenilin.cardservice.model.Operation;
import ru.tenilin.cardservice.repository.CardRepository;
import ru.tenilin.cardservice.repository.OperationRepository;
import ru.tenilin.cardservice.service.CardService;
import ru.tenilin.cardservice.service.OperationService;

import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class CardServiceApplicationTests {

    @Test
    public void testTransferCard(){
        CardRepository cardRepository = new CardRepository();
        CardService cardService = new CardService(cardRepository);
        Operation operation = new Operation("123", "1111111111111111", "2222222222222222", 10L, "000", new Date(), "rub");
        cardService.cardTransfer(operation);
        assertEquals("Balance CardFrom", 990L, cardRepository.getCardByNumber("1111111111111111").getBalance());
        assertEquals("Balance CardTo", 20L, cardRepository.getCardByNumber("2222222222222222").getBalance());
    }

    void contextLoads() {
    }

}
