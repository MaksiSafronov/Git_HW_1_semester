package com.mipt.maksimsafronov.CollectionHWClasses;

import java.util.NoSuchElementException;

/**
 * Кастомная реализация ArrayList
 * @param <A> тип элементов в списке
 */
public class CustomArrayList<A> implements CustomList<A> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;

    private Object[] elements;
    private int size;

    /**
     * Создает пустой список с начальной емкостью 10
     */
    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Создает пустой список с указанной начальной емкостью
     * @param initialCapacity начальная емкость
     * @throws IllegalArgumentException если initialCapacity меньше 0
     */
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public boolean add(A element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public A get(int index) {
        checkIndex(index);
        return (A) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public A remove(int index) {
        checkIndex(index);

        A removedElement = (A) elements[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }

        elements[--size] = null;

        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public CustomIterator<A> iterator() {
        return new CustomArrayListIterator();
    }

    /**
     * Проверяет и увеличивает емкость при необходимости
     * @param minCapacity минимальная требуемая емкость
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = (int) (elements.length * GROWTH_FACTOR);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Проверяет валидность индекса
     * @param index индекс для проверки
     * @throws IndexOutOfBoundsException если индекс невалиден
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Внутренний класс итератора
     */
    private class CustomArrayListIterator implements CustomIterator<A> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public A next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return (A) elements[currentIndex++];
        }
    }
}