package Lesson_3.Homework_3;

import java.util.HashMap;
import java.util.HashSet;

public class Phonebook {

    private HashMap<String, HashSet<String>> phonebook = new HashMap<>();

    public void add(String surname, String phoneNumber) {
        if (phonebook.containsKey(surname)) {
            phonebook.get(surname).add(phoneNumber);
        } else {
            HashSet<String> numbers = new HashSet<>();
            numbers.add(phoneNumber);
            phonebook.put(surname, numbers);
        }
    }

    public void get(String surname) {
        if (phonebook.containsKey(surname)) {
            System.out.println(surname + ": " + phonebook.get(surname));
        } else {
            System.out.println("В телефонном справочнике такая фамилия не найдена");
        }
    }
}
