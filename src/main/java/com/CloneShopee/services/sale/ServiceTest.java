package com.CloneShopee.services.sale;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceTest {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 25),
                new Person("David", 30),
                new Person("Eve", 35),
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 25),
                new Person("David", 30),
                new Person("Eve", 35));

        // Nhóm danh sách theo tuổi
        Map<Integer, List<Person>> groupedByAge = people.stream()
                .collect(Collectors.groupingBy(person -> person.age));

        groupedByAge.forEach((age, persons) -> {
            System.out.println("Tuổi " + age + ": " + persons.size());
        });
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}