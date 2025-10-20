package com.devmaster.lesson01.lambda_expression;

@FunctionalInterface
interface Calculator1 {
    int add(int a, int b);
}

@FunctionalInterface
interface Calculator2 {
    void add(int a, int b);
}

public class LambdaExpression3 {
    public static void main(String[] args) {
        Calculator1 calc1 = (a, b) -> a + b;
        System.out.println(calc1.add(11, 12));

        Calculator2 calc2 = (a, b) -> System.out.println(a + b);
        calc2.add(21, 22);
    }
}