package com.mipt.maksimsafronov.MultithreadingHWClasses;

public class Bank {

    /**
     * МЕТОД DEADLOCK.
     */
    public void sendToAccountDeadlock(BankAccount from, BankAccount to, int amount) {
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() + " -> Захватил блокировку: " + from.getName());

            try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            synchronized (to) {
                System.out.println(Thread.currentThread().getName() + " -> Захватил блокировку: " + to.getName());

                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.println(Thread.currentThread().getName() + " -> Перевод (DEADLOCK) успешно выполнен. " + from.getName() + " -> " + to.getName() + " на сумму: " + amount);
                } else {
                    System.out.println(Thread.currentThread().getName() + " -> Перевод (DEADLOCK) не выполнен. Недостаточно средств на счете: " + from.getName());
                }
            }
        }
    }


    /**
     * МЕТОД БЕЗ DEADLOCK.
     */
    public void sendToAccount(BankAccount from, BankAccount to, int amount) {
        BankAccount firstLock = (from.compareTo(to) < 0) ? from : to;
        BankAccount secondLock = (from.compareTo(to) < 0) ? to : from;

        if (firstLock.equals(secondLock)) {
            synchronized (firstLock) {
                performTransfer(from, to, amount);
            }
            return;
        }

        synchronized (firstLock) {
            System.out.println(Thread.currentThread().getName() + " -> Захватил блокировку (first): " + firstLock.getName());
            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName() + " -> Захватил блокировку (second): " + secondLock.getName());

                performTransfer(from, to, amount);

            }
        }
    }

    /**
     * Перевод денег.
     */
    private void performTransfer(BankAccount from, BankAccount to, int amount) {
        if (amount <= 0) {
            System.out.println(Thread.currentThread().getName() + " -> Перевод не выполнен. Некорректная сумма.");
            return;
        }

        if (from.withdraw(amount)) {
            to.deposit(amount);
            System.out.println(Thread.currentThread().getName() + " -> Перевод успешно выполнен. " + from.getName() + " -> " + to.getName() + " на сумму: " + amount);
        } else {
            System.out.println(Thread.currentThread().getName() + " -> Перевод не выполнен. Недостаточно средств на счете: " + from.getName() + ", Баланс: " + from.getBalance());
        }
    }
}