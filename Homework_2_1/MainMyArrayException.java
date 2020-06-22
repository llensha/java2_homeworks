package Lesson_2.Homework_2_1;

public class MainMyArrayException {

    public static void main(String[] args) {
        int lengthI = 4;
        int lengthJ = 4;

        String[][] array1 = {
                {"1", "2", "3", "4"},
                {"21", "22", "23", "24"},
                {"31", "ABC", "33", "34"}
        };
        getSumStringArray(array1, lengthI, lengthJ);

        String[][] array2 = {
                {"1", "2", "3", "4"},
                {"21", "22", "23"},
                {"31", "ABC", "33", "34"},
                {"41", "42", "43", "44"}
        };
        getSumStringArray(array2, lengthI, lengthJ);

        String[][] array3 = {
                {"1", "2", "3", "4"},
                {"21", "22", "23", "24"},
                {"31", "ABC", "33", "34"},
                {"41", "42", "43", "44"}
        };
        getSumStringArray(array3, lengthI, lengthJ);

        String[][] array4 = {
                {"-7", "0", "3", "4"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"},
                {"41", "42", "43", "54"}
        };
        getSumStringArray(array4, lengthI, lengthJ);

    }

    static void getSumStringArray(String[][] array, int lengthI, int lengthJ) {
        int sum = 0;
        try {
            MyArraySizeException.checkMyArraySize(array, lengthI, lengthJ);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    sum += MyArrayDataException.checkMyArrayData(array[i][j], i, j);
                }
            }
            System.out.println("Сумма элементов массива = " + sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
