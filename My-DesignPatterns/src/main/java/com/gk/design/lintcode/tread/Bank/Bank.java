package com.gk.design.lintcode.tread.Bank;

import com.gk.design.lintcode.tread.Variable.VariableModification;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private int account;
    // write your code


    public Bank(int account) {
        this.account = account;
        // write your code

    }

    public void saveMoney(int amount) throws Exception {
        // write your code
        synchronized (Bank.class) {
            Main.saveOperation( this.account,amount);
            this.account+= amount;
        }

    }

    public void withdrawMoney(int amount) throws Exception {
        // write your code
        if (this.account<amount){
            Thread.currentThread().sleep(10);
        }
        synchronized (Bank.class) {
            Main.withdrawOperation( this.account,amount);
            this.account-= amount;
        }
    }

    public int checkAccount(){
        // write your code
        return this.account;
    }
}