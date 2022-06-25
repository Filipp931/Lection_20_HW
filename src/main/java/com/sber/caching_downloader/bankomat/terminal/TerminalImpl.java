package com.sber.caching_downloader.bankomat.terminal;

import com.sber.caching_downloader.bankomat.server.Server;
import com.sber.caching_downloader.bankomat.server.exceptions.AccountIsLockedException;
import com.sber.caching_downloader.bankomat.server.exceptions.IncorrectSumException;
import com.sber.caching_downloader.bankomat.server.exceptions.NotEnoughMoneyException;
import com.sber.caching_downloader.bankomat.server.exceptions.PinIsNotValidatedException;
import org.springframework.stereotype.Component;

@Component
public class TerminalImpl implements Terminal{
    Server server;

    public TerminalImpl(Server server) {
        this.server = server;
    }

    @Override
    public void withdraw(int sum) throws IncorrectSumException, NotEnoughMoneyException, PinIsNotValidatedException {
        server.withdraw(sum);
    }

    @Override
    public void deposit(int sum) throws IncorrectSumException, PinIsNotValidatedException {
        server.deposit(sum);
    }

    @Override
    public int getBalance() throws PinIsNotValidatedException {
        return server.getBalance();
    }

    @Override
    public boolean checkPassword(int pin) throws AccountIsLockedException {
        return server.checkPin(pin);
    }

    @Override
    public boolean isPinIsValidated() {
        return server.isPinIsValidated();
    }
}
