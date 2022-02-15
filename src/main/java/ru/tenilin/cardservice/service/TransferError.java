package ru.tenilin.cardservice.service;

public class TransferError extends RuntimeException {
    public TransferError(String msg) {
        super(msg);
    }
}
