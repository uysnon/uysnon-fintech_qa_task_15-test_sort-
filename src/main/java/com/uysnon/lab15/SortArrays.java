package com.uysnon.lab15;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class SortArrays {
    private static final int LENGTH_ARRAY = 20000; //length of the sortable array

    private static final int MIN_ELEMENT = -10000;
    private static final int  MAX_ELEMENT = 10000;
    private static final int NUM_SELECTION_VARIATIONS = 3;
    private static final String MESSAGE_PICK = "Select sort method: \n " +
            "0. All the following sorts\n " +
            "1. Gnome sort and its optimized version \n " +
            "2. Bubble sort and cocktail sort\n " +
            "3. Quick sort"
            ;
    private static final String MESSAGE_ERROR = "Something went wrong";
    private static final String LINE = "__________________________________________________________________________";

    public static void main(String[] args) {
        int array[] = new int [LENGTH_ARRAY];
        System.out.println("Basic array:");
        inPutArray(array);
        outPutArray(array);
        System.out.println("\n");
        System.out.println(MESSAGE_PICK);
        Scanner scanner = new Scanner(System.in);
        int pickElement = -1;
        do {
           pickElement = scanner.nextInt();
        }while ((pickElement<0)||(pickElement>NUM_SELECTION_VARIATIONS));
        scanner.close();
        System.out.println(LINE+"\nBasic array:\n"+Arrays.toString(array)+"\n");
        switch (pickElement){
            case 0: printAllSorts(array);
            break;
            case 1: printGnomeSorts(array);
            break;
            case 2: printBubbleCocktailSorts(array);
            break;
            case 3: printQuickSort(array);
            break;
            default: System.out.println(MESSAGE_ERROR);
            break;
        }

    }

    private static void printAllSorts(int [] pArray){
        printGnomeSorts(pArray);
        printBubbleCocktailSorts(pArray);
        printQuickSort(pArray);
        int [] array = Arrays.copyOf(pArray, pArray.length);
        long time0 = System.nanoTime();
        Arrays.sort(array);
        long time1 = System.nanoTime();
        System.out.println(LINE+"\nJAVA-SORT\n");
        showDuration(time1-time0);
    }


    private static void printBubbleCocktailSorts(int [] pArray){
        long durationBubble, startTimeBubble, endTimeBubble;
        long durationCocktail, startTimeCocktail, endTimeCocktail;
        startTimeBubble = System.nanoTime();
        int [] sortBubbleArray = sortBubble(pArray);
        endTimeBubble = System.nanoTime();
        durationBubble = endTimeBubble - startTimeBubble;

        startTimeCocktail = System.nanoTime();
        int [] sortCocktailArray = sortCocktail(pArray);
        endTimeCocktail = System.nanoTime();
        durationCocktail = endTimeCocktail - startTimeCocktail;

        System.out.println(LINE+"\nBubble sort:");
        showDuration(durationBubble);
        if (LENGTH_ARRAY < 50000) outPutArray(sortBubbleArray);
        System.out.println("\n");

        System.out.println(LINE+"\nCocktail sort:");
        showDuration(durationCocktail);
        if (LENGTH_ARRAY < 50000) outPutArray(sortCocktailArray);
        System.out.println("\n");
    }

    private static void printGnomeSorts(int [] pArray){
        long durationGnome, startTimeGnome, endTimeGnome;
        long durationGnomeOptimization, startTimeGnomeOptimization, endTimeGnomeOptimization;
        startTimeGnome = System.nanoTime();
        int [] sortGnomeArray = sortGnome(pArray);
        endTimeGnome = System.nanoTime();
        durationGnome = endTimeGnome - startTimeGnome;

        startTimeGnomeOptimization = System.nanoTime();
        int [] sortGnomeOptimizationArray = sortGnomeOptimization(pArray);
        endTimeGnomeOptimization = System.nanoTime();
        durationGnomeOptimization = endTimeGnomeOptimization - startTimeGnomeOptimization;

        System.out.println(LINE+"\nGnome sort:");
        showDuration(durationGnome);
        if (LENGTH_ARRAY < 50000)outPutArray(sortGnomeArray);
        System.out.println("\n");

        System.out.println(LINE+"\nOptimized gnome sort:");
        showDuration(durationGnomeOptimization);
        if (LENGTH_ARRAY < 50000)outPutArray(sortGnomeOptimizationArray);
        System.out.println("\n");
    }

    private static void printQuickSort(int [] pArray){
        long durationQuick, startTimeQuick, endTimeQuick;
        startTimeQuick = System.nanoTime();
        int [] sortQuickArray = Arrays.copyOf(pArray,pArray.length);
        quickSort(sortQuickArray, 0,sortQuickArray.length-1);
        endTimeQuick = System.nanoTime();
        durationQuick = endTimeQuick - startTimeQuick;


        System.out.println(LINE+"\nQuick sort:");
        showDuration(durationQuick);
        if (LENGTH_ARRAY < 50000)outPutArray(sortQuickArray);
        System.out.println("\n");

    }



    //***************************************methods for bubble sort
    public static int [] sortBubble(int[] pArray){
        int [] array = Arrays.copyOf(pArray, pArray.length);
        for (int i = 0 ; i < array.length-1; i++){
            for (int j = 0; j<array.length-i-1; j++){
                if (array[j] > array[j+1]){
                    changeElements(array,j, j+1);
                }
            }
        }
        return array;
    }
    //**************************************

    //**************************************methods for cocktail sort
    public static int[] sortCocktail(int[] pArray){
        int [] array = Arrays.copyOf(pArray, pArray.length);
        int n = 0;
        int front, back;
        front = 0;
        back = 0;
        for (int i = 0 ; i < array.length-1; i++) {
            if (n % 2 == 0) {
                for (int j = front; j < array.length - back - 1; j++) {
                    if (array[j] > array[j + 1]) changeElements(array, j, j + 1);
                }
            } else {
                for (int j = array.length - back - 1; j > front; j--) {
                    if (array[j] < array[j - 1]) changeElements(array, j, j - 1);
                }
            }
            if (n % 2 == 0) back++;
            else front++;
            n = ++n % 2;
        }
        return array;
    }

    //*************************************


    //*******************************************methods for gnome sort:
    //main method gnomeSort
    public static int[] sortGnome(int [] pArray){
        int [] sortArray  = Arrays.copyOf(pArray, pArray.length);
        int currentIndex = 0;
        do {
            currentIndex = stepGnome(sortArray, currentIndex);
        }while (currentIndex != -1);
        return sortArray;
    }
    //supporting method of gnomeSort (one step of gnomeSort, that returns int value of index for the following step
    // and change elements in array (if necessary)
    private static int stepGnome(int [] pArray, int pCurrentIndex){
        if (pCurrentIndex == pArray.length-1) return -1;
        if (pArray[pCurrentIndex] > pArray[pCurrentIndex+1]){
            changeElements(pArray, pCurrentIndex, pCurrentIndex+1);
            if (pCurrentIndex == 0) return pCurrentIndex+1;
            return pCurrentIndex-1;
        }
        if (pArray[pCurrentIndex] <= pArray[pCurrentIndex+1]){
            return pCurrentIndex+1;
        }
        return -1;
    }
    //********************************************

    //***********************************methods for optimizationGnomeSort
    //main method optimizationGnomeSort
    private static int[] sortGnomeOptimization(int [] pArray){
        int [] sortArray = Arrays.copyOf(pArray, pArray.length);
        int currentIndex = 0;
        int indexPastStep = currentIndex;
        int indexMemory  = currentIndex;
        boolean isStepFirstToLeftSide = true;
        do {
            currentIndex = stepGnome(sortArray, currentIndex);
            if (currentIndex != -1){
                if ((currentIndex < indexPastStep)&&(isStepFirstToLeftSide))
                {
                    isStepFirstToLeftSide = false;
                    indexMemory = indexPastStep;
                }
                if ((currentIndex > indexPastStep) && (!isStepFirstToLeftSide)){
                    isStepFirstToLeftSide = true;
                    currentIndex = indexMemory;
                }
                indexPastStep = currentIndex;
            }
        }while (currentIndex != -1);
        return sortArray;
    }


    //*************************************************quick sort method
    private static void quickSort(int [] pArray, int index1, int index2){
        int [] array = pArray;
        double pivot = ((pArray[index1]+pArray[index2]+pArray[(index1+index2)/2])/3.0);
        int pointer1 = index1;
        int pointer2 = index2;
        while (pointer1 <= pointer2) {
            for (int i = pointer1; i <= index2; i++) {
                    pointer1 = i;
                    if (array[i] > pivot) break;
                }
            for (int i = pointer2; i >= index1; i--) {
                    pointer2 = i;
                    if (array[i] < pivot) break;
                }

            if (pointer1 < pointer2) changeElements(array,pointer1,pointer2);
        }
        if (index1 != pointer2) quickSort(array,index1,pointer2);
        if (pointer1 != index2) quickSort(array,pointer1,index2);
    }
    //**********************************

    private static void changeElements(int [] pArray, int pIndex1, int pIndex2){
        int x = pArray[pIndex1];
        pArray[pIndex1] = pArray[pIndex2];
        pArray[pIndex2] = x;
    }

    private static void showDuration (long pDuration){
        double duration = pDuration/ Math.pow(10, 6);
        NumberFormat formatter = new DecimalFormat("#0.0000");
        System.out.println(formatter.format(duration) + " мc");
    }

    //Method of outPut all Arrays in the program
    private static void outPutArray(int [] pArray){
        System.out.println(Arrays.toString(pArray));
    }
    //Method of input all arrays in the program
    private static void inPutArray (int [] pArray){
        for (int i = 0; i < pArray.length; i++){
            pArray[i] = ThreadLocalRandom.current().nextInt(MIN_ELEMENT, MAX_ELEMENT);
        }
    }


}
