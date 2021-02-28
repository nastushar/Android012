package com.nastya;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main ( String[] args ) {
        metod1 ();
        metod2 ();

    }

    public static void metod1 () {
        System.out.println ("Метод 1.");
        float[] arr = new float[ SIZE ];
        long a1 = System.currentTimeMillis ();
        for ( int i = 0; i < arr.length; i++ ) {
            arr[ i ] = 1;

        }
        long a2 = System.currentTimeMillis ();
        System.out.println ("Время заполнения массива: " + (a2-a1));
        for ( int i = 0; i < arr.length; i++ ) {
            arr[ i ] = (float) (arr[ i ] * Math.sin (0.2f + i / 5) * Math.cos (0.2f + i / 5) * Math.cos (0.4f + i / 2));
        }
        long a3 = System.currentTimeMillis ();
        System.out.println ("Время перезаписи массива: " + (a3-a2));
        System.out.println ("Разница: " + (a3-2*a2+a1));
    }

    public static void metod2 () {
        System.out.println ("Метод 2.");
        float[] arr = new float[ SIZE ];
        long a1 = System.currentTimeMillis ();
        Arrays.fill (arr , 1);
        long a2 = System.currentTimeMillis ();
        System.out.println ("Время создания массива: " + (a2-a1));
        float[] arr1 = new float[ HALF ];
        float[] arr2 = new float[ HALF ];
        System.arraycopy (arr , 0 , arr1 , 0 , HALF);
        System.arraycopy (arr , HALF , arr2 , 0 , HALF);

        Thread thread1 = new Thread (() -> {
            for ( int i = 0; i < arr1.length; i++ ) {
                arr1[ i ] = (float) (arr1[ i ] * Math.sin (0.2f + i / 5) * Math.cos (0.2f + i / 5) * Math.cos (0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread (() -> {
            for ( int i = 0; i < arr2.length; i++ ) {
                arr2[ i ] = (float) (arr2[ i ] * Math.sin (0.2f + i / 5) * Math.cos (0.2f + i / 5) * Math.cos (0.4f + i / 2));
            }
        });
        thread1.start ();
        thread2.start ();

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, (HALF+1), (HALF-1));
        long a3 = System.currentTimeMillis ();
        System.out.println ("Время перезаписи массива (разделение+проход+склейка) : " + (a3-a2));
        System.out.println ("Разница: " + (a3-2*a2+a1));

    }

}