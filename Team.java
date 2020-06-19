package Lesson_1.Homework_1;

public class Team {
    String teamName;
    Competitor[] competitors;

    public Team(String teamName, Competitor... competitors) {
        this.teamName = teamName;
        this.competitors = competitors;
    }

    public void showResults() {
        System.out.println();
        System.out.println("Команда " + teamName + ":");
        for (Competitor c : competitors) {
            c.infoResults();
        }
    }

    public void showWinners() {
        System.out.println();
        System.out.println("Успешно преодолели полосу препятствий:");
        for (Competitor c : competitors) {
            if (c.isOnDistance()) {
                c.info();
            }
        }
    }

}
