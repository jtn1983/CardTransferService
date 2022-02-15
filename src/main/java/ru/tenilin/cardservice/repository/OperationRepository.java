package ru.tenilin.cardservice.repository;

import org.springframework.stereotype.Repository;
import ru.tenilin.cardservice.model.Operation;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OperationRepository {
    private static final ConcurrentHashMap<String, Operation> operationMap = new ConcurrentHashMap<>();

    public void addOperation(Operation operation) {
        operationMap.put(operation.getOperationId(), operation);
    }

    public Operation getOperation(String operationId) {
        return operationMap.get(operationId);
    }

    public void removeOperation(Operation operation) {
        operationMap.remove(operation.getOperationId());
    }
}
