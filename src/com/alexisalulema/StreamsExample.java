package com.alexisalulema;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class StreamsExample {

    static void run() {
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

        // Basic example
        List<String> germanCars = list.stream()
                .filter(x -> x.getOrigin().equals(Country.GERMANY))
                .map(Car::getName)
                .collect(Collectors.toList());
        System.out.println(germanCars);

        // Nested mapping example
        Set<String> countries = list.stream()
                .map(Car::getOrigin)
                .map(Country::getName)
                .collect(Collectors.toSet());
        System.out.println(countries);

        // String joining example
        String rawSentence = list.stream()
                .map(Car::getName)
                .collect(Collectors.joining(","));
        System.out.println(rawSentence);

        // Grouping example
        Map<Country, List<Car>> groupByCountry = list.stream()
                .collect(Collectors.groupingBy(Car::getOrigin));
        System.out.println(groupByCountry.get(Country.JAPAN).stream()
                .map(Car::getName)
                .collect(Collectors.toList()));
        System.out.println(groupByCountry.get(Country.US).stream()
                .map(Car::getName)
                .collect(Collectors.toList()));

        // Collection to Map example
        Map<String, Car> tokenToWord = list.stream()
                .collect(Collectors.toMap(Car::getName, Function.identity()));
        System.out.println(tokenToWord.get("Mazda"));

        // Basic primitive stream example
        OptionalInt maxNameLength = list.stream()
                .mapToInt(x -> x.getName().length())
                .max();
        if (maxNameLength.isPresent())
            System.out.println(maxNameLength.getAsInt());

        // Parallel processing example
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("Threads: " + commonPool.getParallelism());
        int[] firstLengths = list.parallelStream()
                .filter(w -> w.getOrigin().equals(Country.JAPAN))
                .map(Car::getName)
                .mapToInt(String::length)
                .sequential()
                .sorted()
                .limit(2)
                .toArray();
        System.out.println(Arrays.toString(firstLengths));

        // Files I/O example
        InputStream fStream = Main.class.getResourceAsStream("text.txt");
        InputStreamReader fReader = new InputStreamReader(fStream, StandardCharsets.UTF_8);

        try (Stream<String> lines = new BufferedReader(fReader).lines()) {
            OptionalDouble average = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .map(String::toLowerCase)
                    .mapToInt(String::length)
                    .average();

            if (average.isPresent())
                System.out.println("average token length: " + average.getAsDouble());
        }

        // Custom collector example
        Collector<Car, StringJoiner, String> carsCollector =
                Collector.of(
                        () -> new StringJoiner(", "),                     // supplier
                        (joiner, car) -> joiner.add(car.getName().toUpperCase()),  // accumulator
                        StringJoiner::merge,                                       // combiner
                        StringJoiner::toString);                                   // finisher

        String names = list.stream()
                .collect(carsCollector);

        System.out.println(names);

    }
}

class Car {
    private final String name;
    private final Country origin;

    Car(String name, Country origin) {
        this.name = name;
        this.origin = origin;
    }

    String getName() {
        return name;
    }

    Country getOrigin() {
        return origin;
    }
}

final class Country {
    private final int value;
    private final String name;

    static Country GERMANY = new Country(1, "GERMANY");
    static Country US = new Country(2, "US");
    static Country UK = new Country(3, "UK");
    static Country INDIA = new Country(4, "INDIA");
    static Country JAPAN = new Country(5, "JAPAN");

    private Country(int value, String name) {
        this.value = value;
        this.name = name;
    }

    int getValue() {
        return value;
    }

    String getName() {
        return name;
    }
}
