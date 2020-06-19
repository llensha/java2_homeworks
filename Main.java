package Lesson_1.Homework_1;


public class Main {
    public static void main(String[] args) {

        Course course = new Course(new Cross(80), new Water(5), new Wall(2), new Cross(120));
        Team team = new Team ("DreamTeam",
                new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"), new Dog("Дружок", 1));
        course.doIt(team);
        team.showResults();
        team.showWinners();

    }
}