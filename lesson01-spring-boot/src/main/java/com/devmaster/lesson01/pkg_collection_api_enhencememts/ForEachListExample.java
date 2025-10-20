package com.devmaster.lesson01.pkg_collection_api_enhencememts;

import java.util.Arrays;
import java.util.List;

public class ForEachListExample {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java Spring", "C#", "NetCore API", "PHP Laravel", "Javascript");

        System.out.println("Sử dụng biểu thức Lambda:");
        languages.forEach(lang -> System.out.println(lang));

        System.out.println("Sử dụng Method Reference:");
        languages.forEach(System.out::println);
    }
}
