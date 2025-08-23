package utils;

import java.util.Random;
public class RandomGenerate {
    public static void main(String[] args) {
        int[] numbers = {1, 5, 10};
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.length);
        System.out.println("随机选择的数字是: " + numbers[randomIndex]);
    }
}