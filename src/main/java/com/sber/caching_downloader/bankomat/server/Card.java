package com.sber.caching_downloader.bankomat.server;

import com.sber.caching_downloader.bankomat.server.exceptions.NotEnoughMoneyException;
import org.springframework.stereotype.Component;

@Component
public class Card {
    private int balance = 12000;
    private int pin = 1234;

    public void withdraw(int sum) throws NotEnoughMoneyException {
        if(sum > balance) {
            throw new NotEnoughMoneyException();
        } else {
            balance -= sum;
        }
    }

    public void deposit(int sum) {
        balance += sum;
    }

    public int getBalance() {
        return balance;
    }

    public boolean checkPin(int pin){
       return this.pin==pin;
    }
}
