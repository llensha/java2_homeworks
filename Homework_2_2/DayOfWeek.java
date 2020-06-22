package Lesson_2.Homework_2_2;

public enum DayOfWeek {
    MONDAY(8), TUESDAY(8), WEDNESDAY(8), THURSDAY(8), FRIDAY(8), SATURDAY(0), SUNDAY(0);

    private int workingHours;

    DayOfWeek(int workingHours) {
        this.workingHours = workingHours;
    }

    public static void getRemainWorkingHours(DayOfWeek day) {
        if (day.workingHours == 0) {
            System.out.println("Это выходной день");
        } else {
            int remainWorkingHours = 0;
            for (int i = day.ordinal(); i < DayOfWeek.values().length; i++) {
                remainWorkingHours += DayOfWeek.values()[i].workingHours;
            }
            System.out.println("Оставшееся количество рабочих часов до конца недели = " + remainWorkingHours);
        }
    }

}

