package com.devmaster.lesson01.lambda_expression;

import java.util.Arrays;
import java.util.List;

public class LambdaExpression4 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Java SpringBoot", "C# NetCore", "PHP", "JavaScript");

        // Duyệt bằng lambda
        list.forEach(item -> System.out.println(item));

        System.out.println("==================");

        // Duyệt ngắn gọn hơn
        list.forEach(System.out::println);
    }
}
