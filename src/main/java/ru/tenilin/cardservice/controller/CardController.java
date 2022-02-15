package ru.tenilin.cardservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tenilin.cardservice.model.Amount;
import ru.tenilin.cardservice.model.ConfirmForm;
import ru.tenilin.cardservice.model.Operation;
import ru.tenilin.cardservice.model.TransferForm;
import ru.tenilin.cardservice.service.CardService;
import ru.tenilin.cardservice.service.InvalidCard;
import ru.tenilin.cardservice.service.OperationService;
import ru.tenilin.cardservice.service.TransferError;

import java.util.Collections;
import java.util.Map;

@RestController
public class CardController {
    private final CardService cardService;
    private final OperationService operationService;

    @Autowired
    public CardController(CardService cardService, OperationService operationService) {
        this.cardService = cardService;
        this.operationService = operationService;
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferForm transferForm) {
        String cardFromNumber = transferForm.getCardFromNumber();
        String cardToNumber = transferForm.getCardToNumber();
        String cardFromValidTill = transferForm.getCardFromValidTill();
        String cardFromCVV = transferForm.getCardFromCVV();
        Amount amount = transferForm.getAmount();
        cardService.checkCard(cardFromNumber, cardToNumber, cardFromValidTill, cardFromCVV, amount.getValue());
        Operation operation = operationService.addOperation(cardFromNumber, cardToNumber, amount.getValue(), amount.getCurrency());
        return new ResponseEntity<>(Collections.singletonMap("operationId", operation.getOperationId()), HttpStatus.OK);
    }

    @PostMapping(value = "/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmForm confirmForm) {
        Operation operation = operationService.getOperation(confirmForm.getOperationId(), confirmForm.getCode());
        cardService.cardTransfer(operation);
        operationService.logOperation(operation);
        operationService.removeOperation(operation);
        return new ResponseEntity<>(Collections.singletonMap("operationId", operation.getOperationId()), HttpStatus.OK);
    }

    @ExceptionHandler(InvalidCard.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidCard(InvalidCard e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ExceptionHandler(TransferError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleTransferError(TransferError e) {
        return Collections.singletonMap("message", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> allException(Exception e) {
        return Collections.singletonMap("message", e.getMessage());
    }


}
