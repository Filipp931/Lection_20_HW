package com.sber.caching_downloader.bankomat.terminal;

import com.sber.caching_downloader.bankomat.server.exceptions.AccountIsLockedException;
import com.sber.caching_downloader.bankomat.server.exceptions.IncorrectSumException;
import com.sber.caching_downloader.bankomat.server.exceptions.NotEnoughMoneyException;
import com.sber.caching_downloader.bankomat.server.exceptions.PinIsNotValidatedException;

public interface Terminal {
    void withdraw(int sum) throws IncorrectSumException, NotEnoughMoneyException, PinIsNotValidatedException;
    void deposit(int sum) throws IncorrectSumException, PinIsNotValidatedException;
    int getBalance() throws PinIsNotValidatedException;
    boolean checkPassword(int pin) throws AccountIsLockedException;
    boolean isPinIsValidated();
}
