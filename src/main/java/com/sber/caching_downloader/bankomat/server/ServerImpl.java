package com.sber.caching_downloader.bankomat.server;

import com.sber.caching_downloader.bankomat.server.exceptions.AccountIsLockedException;
import com.sber.caching_downloader.bankomat.server.exceptions.IncorrectSumException;
import com.sber.caching_downloader.bankomat.server.exceptions.NotEnoughMoneyException;
import com.sber.caching_downloader.bankomat.server.exceptions.PinIsNotValidatedException;
import org.springframework.stereotype.Service;

@Service
public class ServerImpl implements Server{
    Card card;
    boolean isLocked = false;
    int numberOfTries = 0;
    long lockTime;
    boolean pinIsValidated = false;

    public ServerImpl(Card card) {
        this.card = card;
    }

    @Override
    public void withdraw(int sum) throws IncorrectSumException, NotEnoughMoneyException, PinIsNotValidatedException {
        if(!pinIsValidated) throw new PinIsNotValidatedException();
        if ((sum % 100) > 0) {
            throw new IncorrectSumException();
        }
        card.withdraw(sum);
    }

    @Override
    public void deposit(int sum) throws IncorrectSumException, PinIsNotValidatedException {
        if(!pinIsValidated) throw new PinIsNotValidatedException();
        if ((sum % 100) > 0) {
            throw new IncorrectSumException();
        }
        card.deposit(sum);
    }

    @Override
    public int getBalance() throws PinIsNotValidatedException {
        if(!pinIsValidated) throw new PinIsNotValidatedException();
        return card.getBalance();
    }

    @Override
    public boolean checkPin(int pin) throws AccountIsLockedException {
        if(isLocked) {
            int passedTime = Math.toIntExact(((System.currentTimeMillis() - lockTime) / 1000));
            if (passedTime < 10) {
                throw  new AccountIsLockedException(10 - passedTime);
            }
        }

        if(card.checkPin(pin)) {
            pinIsValidated = true;
            numberOfTries = 0;
            lockTime = 0;
            isLocked = false;
            return true;
        } else {
            numberOfTries++;
            if(numberOfTries == 3) {
                lockTime = System.currentTimeMillis();
                numberOfTries = 0;
                isLocked = true;
            }
            return false;
        }
    }

    @Override
    public boolean isPinIsValidated() {
        return pinIsValidated;
    }
}
