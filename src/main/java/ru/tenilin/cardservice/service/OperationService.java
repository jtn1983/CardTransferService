package ru.tenilin.cardservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tenilin.cardservice.model.Operation;
import ru.tenilin.cardservice.repository.OperationRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    public Operation addOperation(String cardFromNumber, String cardToNumber, Long value, String currency) {
        String operationId = UUID.randomUUID().toString();
        String code = "0000";
        Operation operation = new Operation(operationId, cardFromNumber, cardToNumber, value, code, new Date(), currency);
        operationRepository.addOperation(operation);
        return operation;
    }

    public Operation getOperation(String operationId, String code) {
        Operation operation = operationRepository.getOperation(operationId);
        if (operation == null || !operation.getCode().equals(code)) {
            throw new TransferError("Ошибка операции!");
        }
        return operation;
    }

    public void logOperation(Operation operation) {
        try (FileWriter writer = new FileWriter("operation_log.txt", true)) {
            String text = (
                    operation.getDate() + " " +
                            "OperationId: " + operation.getOperationId() + " " +
                            "CardFrom: " + operation.getCardFrom() + " " +
                            "CardTo: " + operation.getCardTo() + " " +
                            "Paid: " + operation.getValue() / 100 +
                            operation.getCurrency()
            );
            writer.write(text);
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            throw new TransferError("Ошибка операции!");
        }
    }

    public void removeOperation(Operation operation) {
        operationRepository.removeOperation(operation);
    }

}
