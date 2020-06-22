package Lesson_2.Homework_2_1;

public class MyArrayDataException extends Exception {

    public MyArrayDataException(String message) {
        super(message);
    }

    public static int checkMyArrayData(String data, int indexI, int indexJ) throws MyArrayDataException {
        boolean isNumeric = true;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) < '1' || data.charAt(i) > '9') {
                isNumeric = false;
                break;
            }
        }
        if (!isNumeric) throw new MyArrayDataException(String.format("Значение в ячейке массива [%d][%d] не является числом", indexI, indexJ));
        return Integer.parseInt(data);
    }

}
