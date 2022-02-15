package ru.tenilin.cardservice.service;

public class InvalidCard extends RuntimeException {
    public InvalidCard(String msg) {
        super(msg);
    }
}
