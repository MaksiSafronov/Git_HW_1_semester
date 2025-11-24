package MultithreadingHWClasses;

import com.mipt.maksimsafronov.MultithreadingHWClasses.Bank;
import com.mipt.maksimsafronov.MultithreadingHWClasses.BankAccount;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BankTest {

    public static void main(String[] args) throws InterruptedException {
        testDeadlockScenario();
        System.out.println("\n------------------------------------------------\n");
        testSafeTransferScenario();
        System.out.println("\n------------------------------------------------\n");
        testNegativeCases();
    }

    /**
     * Тест многопоточки без дедлока.
     */
    public static void testSafeTransferScenario() throws InterruptedException {
        System.out.println("### 1. Тест безопасного перевода (No Deadlock)");

        BankAccount accountA = new BankAccount("A", 1000);
        BankAccount accountB = new BankAccount("B", 1000);
        Bank bank = new Bank();
        int transferAmount = 100;
        int numThreads = 10;

        String order = (accountA.getId() < accountB.getId()) ? "A -> B" : "B -> A";
        System.out.println("ID A: " + accountA.getId() + ", ID B: " + accountB.getId());
        System.out.println("Ожидаемый порядок блокировки: " + order);


        ExecutorService executor = Executors.newFixedThreadPool(numThreads * 2);

        IntStream.range(0, numThreads).forEach(i ->
                executor.submit(() -> bank.sendToAccount(accountA, accountB, transferAmount))
        );

        IntStream.range(0, numThreads).forEach(i ->
                executor.submit(() -> bank.sendToAccount(accountB, accountA, transferAmount))
        );

        executor.shutdown();

        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Потоки не завершились вовремя в безопасном тесте.");
        }

        int expectedBalance = 1000;
        System.out.println("\n*** Итоговые результаты (Безопасный): ***");
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println("Общий баланс: " + (accountA.getBalance() + accountB.getBalance()));

        if (accountA.getBalance() + accountB.getBalance() == expectedBalance * 2) {
            System.out.println("✅ Проверка баланса пройдена: Общий баланс корректен.");
        } else {
            System.err.println("❌ Проверка баланса не пройдена: Ошибка многопоточности.");
        }
    }


    /**
     * Тест многопоточки c дедлоком.
     */
    public static void testDeadlockScenario() throws InterruptedException {
        System.out.println("### 2. Тест Deadlock");

        BankAccount accountX = new BankAccount("X", 500);
        BankAccount accountY = new BankAccount("Y", 500);
        Bank bank = new Bank();

        System.out.println(accountX);
        System.out.println(accountY);

        Thread t1 = new Thread(() -> {
            bank.sendToAccountDeadlock(accountX, accountY, 10);
        }, "Thread-DEADLOCK-1 (X->Y)");

        Thread t2 = new Thread(() -> {
            bank.sendToAccountDeadlock(accountY, accountX, 20);
        }, "Thread-DEADLOCK-2 (Y->X)");

        t1.start();
        t2.start();


        long timeout = 1000; // 1 секунда
        long startTime = System.currentTimeMillis();

        t1.join(timeout);
        t2.join(timeout);

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("\n*** Результаты (Deadlock): ***");
        System.out.println("Время выполнения: " + elapsedTime + " мс");

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("🔥 Deadlock обнаружен! Один или оба потока (" + (t1.isAlive() ? "T1 " : "") + (t2.isAlive() ? "T2" : "") + ") не завершились в течение " + timeout + " мс.");
            System.out.println("Текущий баланс (может быть некорректным из-за незавершенных операций):");
            System.out.println(accountX);
            System.out.println(accountY);

            t1.interrupt();
            t2.interrupt();
        } else {
            System.out.println("✅ Дедлок не произошел. (Вероятно, из-за специфики планировщика или недостаточного стресса).");
            System.out.println("Итоговый баланс (если все выполнилось):");
            System.out.println(accountX);
            System.out.println(accountY);
        }
    }

    /**
     * Тест корректности операций.
     */
    public static void testNegativeCases() throws InterruptedException {
        System.out.println("### 3. Тест негативных кейсов (Недостаточно средств)");

        BankAccount accountPoor = new BankAccount("Poor", 50);
        BankAccount accountRich = new BankAccount("Rich", 1000);
        Bank bank = new Bank();
        int transferAmount = 100;
        int numThreads = 5;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        IntStream.range(0, numThreads).forEach(i ->
                executor.submit(() -> bank.sendToAccount(accountPoor, accountRich, transferAmount))
        );

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("\n*** Итоговые результаты (Негативные): ***");
        System.out.println(accountPoor);
        System.out.println(accountRich);

        if (accountPoor.getBalance() == 50 && accountRich.getBalance() == 1000) {
            System.out.println("✅ Проверка негативного кейса пройдена: Баланс не изменился.");
        } else {
            System.err.println("❌ Проверка негативного кейса не пройдена: Баланс изменился некорректно.");
        }
    }
}
