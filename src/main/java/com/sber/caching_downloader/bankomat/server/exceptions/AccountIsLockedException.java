package com.sber.caching_downloader.bankomat.server.exceptions;

public class AccountIsLockedException extends Throwable{
    public AccountIsLockedException(int estimateTime) {
        super("Account is locked, wait "+estimateTime+" seconds!");
    }
}
