package org.example;
import org.example.model.BinaryTree;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        testBinaryTree(new BinaryTree());
    }

    // генератор массива
    public static int[] generateKeyArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 80); // [0;80]
        }
        return arr;
    }

    public static void testBinaryTree(BinaryTree binaryTree) {
        int n = 12;
        //int[] arr = {10, 35, 15, 17, 20, 27, 24, 8, 30}; // дерево из методички
        int[] arr = {18, 71, 39, 47, 25, 77, 56, 67, 6, 35, 73, 0};
        //int[] arr = generateKeyArray(n);
        System.out.println("Отсортированный массив");
        System.out.println(Arrays.toString(arr));

        binaryTree.createTree(arr);
        System.out.println("Построенное двоичное дерево:");
        binaryTree.print();

        System.out.println("Симметричный обход");
        binaryTree.inOrder(binaryTree.getRoot());

        int num = 11;
        System.out.println("\nПоиск по близости сверху");
        System.out.println("Результат поиска числа " + num + ": " + binaryTree.searchBigger(num));
    }
}