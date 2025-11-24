package com.mipt.maksimsafronov.MultithreadingHWClasses;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Банковский аккаунт.
 */
public class BankAccount implements Comparable<BankAccount> {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long id;
    private int balance;
    private final String name;

    public BankAccount(String name, int initialBalance) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = name;
        this.balance = initialBalance;
    }

    public long getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(BankAccount other) {
        return Long.compare(this.id, other.id);
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account{" + "name='" + name + '\'' + ", id=" + id + ", balance=" + balance + '}';
    }
}