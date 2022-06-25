package com.sber.caching_downloader.bankomat.server.exceptions;

public class NotEnoughMoneyException extends Throwable {
    public NotEnoughMoneyException() {
        super("Not enough money!");
    }
}
