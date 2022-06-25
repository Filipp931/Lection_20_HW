package com.sber.caching_downloader.bankomat.server.exceptions;

public class IncorrectSumException extends Throwable{
    public IncorrectSumException() {
        super("Sum must be a multiple of 100");
    }
}
