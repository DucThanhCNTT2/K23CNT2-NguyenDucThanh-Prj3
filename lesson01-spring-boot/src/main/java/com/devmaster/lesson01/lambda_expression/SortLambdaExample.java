package com.devmaster.lesson01.lambda_expression;
import java.util.*;
public class SortLambdaExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Java SpringBoot", "C# NetCore", "PHP", "JavaScript");

        Collections.sort(list, (str1, str2) -> str1.compareTo(str2));

        for (String str : list) {
            System.out.println(str);
        }
    }
}
