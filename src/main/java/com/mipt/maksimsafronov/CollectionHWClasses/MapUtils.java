package com.mipt.maksimsafronov.CollectionHWClasses;

import com.mipt.maksimsafronov.CollectionHWClasses.Student;
import java.util.*;

/**
 * Утилиты для работы с Map коллекциями
 */
public class MapUtils {

    /**
     * Находит студентов с оценкой в заданном диапазоне
     * @param map карта студентов
     * @param minGrade минимальная оценка (включительно)
     * @param maxGrade максимальная оценка (включительно)
     * @return список студентов с оценкой в диапазоне [minGrade, maxGrade]
     */
    public static List<Student> findStudentsByGradeRange(Map<Integer, Student> map,
                                                         double minGrade,
                                                         double maxGrade) {
        List<Student> result = new ArrayList<>();

        for (Student student : map.values()) {
            if (student.getGrade() >= minGrade && student.getGrade() <= maxGrade) {
                result.add(student);
            }
        }

        return result;
    }

    /**
     * Возвращает N студентов с наибольшими id
     * @param map TreeMap студентов, отсортированный по убыванию id
     * @param n количество студентов для возврата
     * @return список N студентов с наибольшими id
     */
    public static List<Student> getTopNStudents(TreeMap<Integer, Student> map, int n) {
        List<Student> result = new ArrayList<>();
        int count = 0;

        // TreeMap уже отсортирован, идем от начала (наибольшие id)
        for (Student student : map.values()) {
            if (count >= n) break;
            result.add(student);
            count++;
        }

        return result;
    }
}
