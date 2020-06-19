package Lesson_1.Homework_1;


public class Dog extends Animal {
    public Dog(String name) {
        super("Пес", name, 500, 5, 20);
    }
    public Dog(String name, int maxJumpHeight) {
        super("Пес", name, 500, maxJumpHeight,20);
    }
}