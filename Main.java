package Lesson_3.Homework_3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Задание 1
        String[] words = {"абрикос", "груша", "черешня", "яблоко", "абрикос", "виноград", "яблоко", "крыжовник", "абрикос", "черешня",
                "арбуз", "клубника", "крыжовник", "банан", "малина", "клубника", "черешня", "голубика", "жимолость", "виноград"};
        countDistinctWords(words);
        System.out.println();

        // Задание 2
        Phonebook phonebook = new Phonebook();
        phonebook.add("Иванов", "(123) 456-78-90");
        phonebook.add("Петров", "(234) 567-89-01");
        phonebook.add("Сидоров", "(345) 678-90-12");
        phonebook.add("Мягкова", "(456) 789-01-23");
        phonebook.add("Иванов", "(567) 890-12-34");
        phonebook.add("Федотов", "(678) 901-23-45");
        phonebook.add("Ковалева", "(789) 012-34-56");
        phonebook.add("Иванов", "(890) 123-45-67");
        phonebook.add("Федотов", "(901) 234-56-78");
        phonebook.add("Иванов", "(123) 456-78-90");

        phonebook.get("Иванов");
        phonebook.get("Федотов");
        phonebook.get("Сидоров");
        phonebook.get("Васечкин");

    }

    static void countDistinctWords(String[] words) {
        HashMap<String, Integer> distinctWords = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String key = words[i];
            Integer value = distinctWords.get(key);
            distinctWords.put(key, value == null ? 1 : value + 1);
        }
        System.out.println(distinctWords);
    }

}
