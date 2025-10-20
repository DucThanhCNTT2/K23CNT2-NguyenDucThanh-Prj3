package com.devmaster.lesson01.lambda_expression;

@FunctionalInterface
interface SayHello2 {
    void sayHello(String name);
}

public class LambdaExpression2 {
    public static void main(String[] args) {
        SayHello2 say1 = (name) -> {
            System.out.println("Hello " + name);
        };
        say1.sayHello("Devmaster");

        SayHello2 say2 = name -> System.out.println("Hello " + name);
        say2.sayHello("Devmaster");
    }
}
