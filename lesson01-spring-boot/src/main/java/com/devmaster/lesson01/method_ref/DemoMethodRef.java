package com.devmaster.lesson01.method_ref;

import java.util.Arrays;

@FunctionalInterface
interface ExecuteFunction {
    int execute(int a, int b);
}

class MathUtils {
    public MathUtils() {}

    public MathUtils(String str) {
        System.out.println("MathUtils: " + str);
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public static int minus(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }
}

public class DemoMethodRef {
    public static void main(String[] args) {
        int a = 10, b = 20;

        // Tham chiếu tới static method
        int sum = doAction(a, b, MathUtils::sum);
        System.out.println(a + " + " + b + " = " + sum);

        int minus = doAction(a, b, MathUtils::minus);
        System.out.println(a + " - " + b + " = " + minus);

        // Tham chiếu tới instance method
        MathUtils mathUtils = new MathUtils();
        int multiply = doAction(a, b, mathUtils::multiply);
        System.out.println(a + " * " + b + " = " + multiply);

        // Tham chiếu tới method của class cụ thể
        String[] stringArray = { "Java", "C++", "PHP", "C#", "Javascript" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        for (String str : stringArray) {
            System.out.println(str);
        }
    }

    public static int doAction(int a, int b, ExecuteFunction func) {
        return func.execute(a, b);
    }
}
