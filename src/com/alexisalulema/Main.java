package com.alexisalulema;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Car[] cars = {
                new Car("GM", Country.US),
                new Car("Cadillac", Country.US),
                new Car("BMW", Country.GERMANY),
                new Car("Mercedes Benz", Country.GERMANY),
                new Car("Toyota", Country.JAPAN),
                new Car("Mazda", Country.JAPAN),
                new Car("Honda", Country.JAPAN),
                new Car("Mahindra", Country.INDIA),
                new Car("Land Rover", Country.UK)
        };

        List<Car> list = Arrays.asList(cars);

        List<String> germanCars = list.stream()
                .filter(x -> x.getOrigin().equals(Country.GERMANY))
                .map(Car::getName)
                .collect(Collectors.toList());
        System.out.println(germanCars);

        Set<String> countries = list.stream()
                .map(Car::getOrigin)
                .map(Country::getName)
                .collect(Collectors.toSet());
        System.out.println(countries);

        String rawSentence = list.stream()
                .map(Car::getName)
                .collect(Collectors.joining(","));
        System.out.println(rawSentence);

        Map<Country, List<Car>> groupByCountry = list.stream()
                .collect(Collectors.groupingBy(Car::getOrigin));
        System.out.println(groupByCountry.get(Country.JAPAN).stream()
                .map(Car::getName)
                .collect(Collectors.toList()));
        System.out.println(groupByCountry.get(Country.US).stream()
                .map(Car::getName)
                .collect(Collectors.toList()));

        Map<String, Car> tokenToWord = list.stream()
                .collect(Collectors.toMap(Car::getName, Function.identity()));
        System.out.println(tokenToWord.get("Mazda"));

        int maxNameLength = list.stream()
                .mapToInt(x -> x.getName().length())
                .max().getAsInt();
        System.out.println(maxNameLength);

        int[] firstLengths = list.parallelStream()
                .filter(w -> w.getName().length() % 2 == 0)
                .map(Car::getName)
                .mapToInt(String::length)
                .sequential()
                .sorted()
                .limit(2)
                .toArray();
        System.out.println(Arrays.toString(firstLengths));

        InputStream fStream = Main.class.getResourceAsStream("text.txt");
        InputStreamReader fReader = new InputStreamReader(fStream, StandardCharsets.UTF_8);

        try (Stream<String> lines = new BufferedReader(fReader).lines()) {
            double average = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .map(String::toLowerCase)
                    .mapToInt(String::length)
                    .average().getAsDouble();
            System.out.println("average token length: " + average);
        }
    }
}

class Car {
    private final String name;
    private final Country origin;

    Car(String name, Country origin) {
        this.name = name;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public Country getOrigin() {
        return origin;
    }
}

final class Country {
    private final int value;
    private final String name;

    public static Country GERMANY = new Country(1, "GERMANY");
    public static Country US = new Country(2, "US");
    public static Country UK = new Country(3, "UK");
    public static Country INDIA = new Country(4, "INDIA");
    public static Country JAPAN = new Country(5, "JAPAN");

    private Country(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
