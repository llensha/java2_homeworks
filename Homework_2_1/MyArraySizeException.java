package Lesson_2.Homework_2_1;

public class MyArraySizeException extends Exception {

    public MyArraySizeException(String message) {
        super(message);
    }

    public static void checkMyArraySize(String[][] array, int lengthI, int lengthJ) throws MyArraySizeException {
        boolean isRightSize = true;
        if (array.length != lengthI) isRightSize = false;
        if (isRightSize) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].length != lengthJ) {
                    isRightSize = false;
                    break;
                }
            }
        }
        if (!isRightSize) throw new MyArraySizeException(String.format("Размер массива не соответствует [%d][%d]", lengthI, lengthJ));
    }
}
