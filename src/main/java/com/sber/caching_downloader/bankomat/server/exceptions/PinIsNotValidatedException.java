package com.sber.caching_downloader.bankomat.server.exceptions;

public class PinIsNotValidatedException extends Throwable{
    public PinIsNotValidatedException() {
        super("Input your PIN !");
    }
}
