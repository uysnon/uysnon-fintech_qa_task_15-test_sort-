package com.uysnon.lab15.sort_tests;


import com.uysnon.lab15.SortArrays;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit-тесты для сортировок int массивов. (Класс SortArrays).
 *
 * @author Gorkin A.V.
 * @version 0.1 25.03.2019
 */
public class TestSort {
    /**
     * Массив из множества целочисленных элементов
     */
    private static final int[] NORMAL_ARRAY = {2, 4, 3, -45, 2390, 3, 54, 31};
    /**
     * Пустой массив
     */
    private static final int[] EMPTY_ARRAY = {};
    /**
     * Массив из повторящющихся элелементов
     */
    private static final int[] REPEAT_ARRAY = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    /**
     * Массив из чисел, превышающих тип int
     */
    private static final int[] TOO_LARGE_ARRAY = {(int) Math.pow(10, 30), (int) Math.pow(543, 10)};
    /**
     * Массив из отрицательных чисел
     */
    private static final int[] NEGATIVE_ARRAY = {-4, -435436443, -34, 54236, 123, 634, -1, 0, 1};


    /**
     * Тест для сортировки пузырьком на "нормальном" массиве
     */
    @Test
    public void testBubbleNormal() {
        MatcherAssert.assertThat("Ошибка!\nСортировка: Bubble\n Обычный массив",
                testArray(SortArrays.sortBubble(NORMAL_ARRAY)));
    }

    /**
     * Тест для сортировки пузырьком на "пустом" массиве
     */
    @Test
    public void testBubbleEmpty() {
        try {
            SortArrays.sortBubble(EMPTY_ARRAY);
        } catch (RuntimeException e) {
            MatcherAssert.assertThat("Ошибка\nСортировка: Bubble\n Пустой массив", false);
        }
    }

    /**
     * Тест для сортировки пузырьком на массиве из повторящющихся значений
     */
    @Test
    public void testBubbleRepeat() {
        MatcherAssert.assertThat("Ошибка\nСортировка: Bubble\n  Массив из повторяющихся значений",
                testArray(SortArrays.sortBubble(REPEAT_ARRAY)));
    }

    /**
     * Тест для сортировки пузырьком на массиве из значений, превышающих тип int
     */
    @Test
    public void testBubbleTooLarge() {
        try {
            SortArrays.sortBubble(TOO_LARGE_ARRAY);
        } catch (RuntimeException e) {
            return;
        }
        MatcherAssert.assertThat("Ошибка\nСортировка: Bubble\n  Массив из значений выходящих за границы int",
                false);
    }

    /**
     * Тест для сортировки пузырьком на  массиве с отрицательными числами
     */
    @Test
    public void testBubbleNegative() {
        MatcherAssert.assertThat("Ошибка!\nСортировка: Bubble\nМассив  с отрицательными числами",
                testArray(SortArrays.sortBubble(NEGATIVE_ARRAY)));
    }

    /**
     * Проверка правильной сортировки массива
     *
     * @param arr входной массив
     * @return true - правильно отсортрован
     * false - неправильно
     */
    private boolean testArray(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }


}
