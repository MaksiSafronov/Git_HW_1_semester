package com.mipt.maksimsafronov;

public class MainClass {
    private int intField;
    private String stringField;
    static double doubleField;
    public final long longConstField = 31415;

    public static void main(String[] args) {
        for (int i = 1; i <= 15; i++) {
            System.out.println("Iter: " + i);
        }
    }
}

interface ForStudents {
    void study();
}

abstract class ForWorkers {
    public abstract void work(int num);

    public boolean goHome(String first, String second) {
        return first.equals(second);
    }
}
