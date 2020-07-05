package Lesson_5.Homework_5;

import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static final int SIZE = 10000000;
    static final int MAX_SIZE = SIZE / 2;

    public static void main(String[] args) {

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);

        checkTimeMethod1(arr);

        System.out.println("\nМетод 2");
        checkTimeMethod2(arr);

        System.out.println("\nМетод 3");
        checkTimeMethod3(arr);

    }

    private static void checkTimeMethod1(float[] arr) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время выполнения метода 1: " + (System.currentTimeMillis() - timeStart));
    }

    private static void checkTimeMethod2(float[] arr) {
        long timeStart = System.currentTimeMillis();
        float[] arr1 = new float[MAX_SIZE];
        float[] arr2 = new float[MAX_SIZE];
        System.arraycopy(arr, 0, arr1, 0, MAX_SIZE);
        System.arraycopy(arr, MAX_SIZE, arr2, 0, MAX_SIZE);
        System.out.println("Время на разбивку массивов: " + (System.currentTimeMillis() - timeStart));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long timeStart = System.currentTimeMillis();
                for (int i = 0; i < MAX_SIZE; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.out.println("Время на просчет массива 1: " + (System.currentTimeMillis() - timeStart));
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                long timeStart = System.currentTimeMillis();
                for (int i = 0; i < MAX_SIZE; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.out.println("Время на просчет массива 2: " + (System.currentTimeMillis() - timeStart));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long timeStart2 = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, MAX_SIZE);
        System.arraycopy(arr2, 0, arr, MAX_SIZE, MAX_SIZE);
        System.out.println("Время на склейку массивов: " + (System.currentTimeMillis() - timeStart2));

        System.out.println("Время выполнения метода 2: " + (System.currentTimeMillis() - timeStart));
    }

    private static void checkTimeMethod3(float[] arr) {
        long timeStart = System.currentTimeMillis();
        float[][] arrCount = new float[(SIZE % MAX_SIZE == 0) ? SIZE / MAX_SIZE : SIZE / MAX_SIZE + 1][];

        splitArray(arr, arrCount, timeStart);

        HashMap<Integer, Thread> threads = new HashMap<>();
        for (int i = 0; i < arrCount.length; i++) {
            int finalI = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    long timeStart = System.currentTimeMillis();
                    for (int j = 0; j < arrCount[finalI].length; j++) {
                        arrCount[finalI][j] = (float) (arrCount[finalI][j] * Math.sin(0.2f + j / 5)
                                                        * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                    }
                    System.out.println("Время на просчет массива [" + finalI + "]: " + + (System.currentTimeMillis() - timeStart));
                }
            });
            threads.put(i, t);
            t.start();
        }

        try {
            for (int i = 0; i < arrCount.length; i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        glueArray(arr, arrCount);

        System.out.println("Время выполнения метода 3: " + (System.currentTimeMillis() - timeStart));
    }

    private static float[][] splitArray(float[] arr, float[][] arrCount, long timeStart) {
        for (int i = 0; i < arrCount.length; i++) {
            int copySize = MAX_SIZE;
            if (i == arrCount.length - 1) {
                if (SIZE % MAX_SIZE != 0) {
                    copySize = SIZE % MAX_SIZE;
                }
            }
            arrCount[i] = new float[copySize];
            System.arraycopy(arr, MAX_SIZE * i, arrCount[i], 0, copySize);
        }
        System.out.println("Время на разбивку массивов: " + (System.currentTimeMillis() - timeStart));
        return arrCount;
    }

    private static void glueArray(float[] arr, float[][] arrCount) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < arrCount.length; i++) {
            int copySize = MAX_SIZE;
            if (i == arrCount.length - 1) {
                if (SIZE % MAX_SIZE != 0) {
                    copySize = SIZE % MAX_SIZE;
                }
            }
            System.arraycopy(arrCount[i], 0, arr, MAX_SIZE * i, copySize);
        }
        System.out.println("Время на склейку массивов: " + (System.currentTimeMillis() - timeStart));
    }

}
