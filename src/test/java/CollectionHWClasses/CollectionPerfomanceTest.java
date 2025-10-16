package CollectionHWClasses;

import org.junit.jupiter.api.Test;
import java.util.*;

class CollectionPerformanceTester {

    private static final int ELEMENT_COUNT = 10000;

    @Test
    void testPerformance() {
        System.out.println("Сравнение производительности ArrayList и LinkedList");
        System.out.println("Количество элементов: " + ELEMENT_COUNT);
        System.out.println("==================================================");

        // Тестирование ArrayList
        List<Integer> arrayList = new ArrayList<>();
        long arrayListTime = testListPerformance(arrayList, "ArrayList");

        // Тестирование LinkedList
        List<Integer> linkedList = new LinkedList<>();
        long linkedListTime = testListPerformance(linkedList, "LinkedList");

        System.out.println("==================================================");
        System.out.printf("Общее время ArrayList: %d ms%n", arrayListTime);
        System.out.printf("Общее время LinkedList: %d ms%n", linkedListTime);
    }

    private long testListPerformance(List<Integer> list, String listType) {
        System.out.println("\n--- " + listType + " ---");

        long totalTime = 0;

        // 1. Добавление в конец
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        long addEndTime = endTime - startTime;
        totalTime += addEndTime;
        System.out.printf("Добавление в конец: %d ms%n", addEndTime);

        // 2. Добавление в начало
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) { // Меньше элементов чтобы не ждать слишком долго
            list.add(0, i);
        }
        endTime = System.currentTimeMillis();
        long addStartTime = endTime - startTime;
        totalTime += addStartTime;
        System.out.printf("Добавление в начало: %d ms%n", addStartTime);

        // 3. Вставка в середину
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            list.add(list.size() / 2, i);
        }
        endTime = System.currentTimeMillis();
        long insertMiddleTime = endTime - startTime;
        totalTime += insertMiddleTime;
        System.out.printf("Вставка в середину: %d ms%n", insertMiddleTime);

        // 4. Доступ по индексу
        startTime = System.currentTimeMillis();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.get(i % list.size());
        }
        endTime = System.currentTimeMillis();
        long accessByIndexTime = endTime - startTime;
        totalTime += accessByIndexTime;
        System.out.printf("Доступ по индексу: %d ms%n", accessByIndexTime);

        // 5. Удаление из начала
        startTime = System.currentTimeMillis();
        while (!list.isEmpty()) {
            list.remove(0);
            if (list.size() % 1000 == 0) {
                // Удаляем по 1000 элементов для демонстрации
                break;
            }
        }
        endTime = System.currentTimeMillis();
        long removeStartTime = endTime - startTime;
        totalTime += removeStartTime;
        System.out.printf("Удаление из начала: %d ms%n", removeStartTime);

        // 6. Удаление из конца
        // Заполняем список снова для теста
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }

        startTime = System.currentTimeMillis();
        while (!list.isEmpty()) {
            list.remove(list.size() - 1);
            if (list.size() < ELEMENT_COUNT - 1000) {
                break;
            }
        }
        endTime = System.currentTimeMillis();
        long removeEndTime = endTime - startTime;
        totalTime += removeEndTime;
        System.out.printf("Удаление из конца: %d ms%n", removeEndTime);

        return totalTime;
    }
}