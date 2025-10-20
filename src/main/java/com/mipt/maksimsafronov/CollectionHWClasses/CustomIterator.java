package com.mipt.maksimsafronov.CollectionHWClasses;

/**
 * Интерфейс итератора для кастомного списка
 * @param <A> тип элементов
 */
public interface CustomIterator<A> {

    /**
     * Проверяет, есть ли следующий элемент
     * @return true если есть следующий элемент, иначе false
     */
    boolean hasNext();

    /**
     * Возвращает следующий элемент
     * @return следующий элемент
     * @throws java.util.NoSuchElementException если элементов больше нет
     */
    A next();

}
