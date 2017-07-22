package com.alexisalulema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvExample {
    public static void run() throws IOException {
        List<Person> result = new ArrayList<>();

        InputStream fStream = Main.class.getResourceAsStream("persons.csv");
        InputStreamReader fReader = new InputStreamReader(fStream, StandardCharsets.UTF_8);

        try (BufferedReader reader = new BufferedReader(fReader)) {
            CSVFormat csv = CSVFormat.RFC4180.withHeader();

            try (CSVParser parser = csv.parse(reader)) {
                // Creating an iterator is useful when CSV is very large
                Iterator<CSVRecord> it = parser.iterator();

                it.forEachRemaining(rec -> {
                    String name = rec.get("name");
                    String email = rec.get("email");
                    String country = rec.get("country");
                    int salary = Integer.parseInt(rec.get("salary").substring(1));
                    int experience = Integer.parseInt(rec.get("experience"));

                    Person p = new Person(name, email, country, salary, experience);
                    result.add(p);
                });
            }
        }

        System.out.println("Results Number: " + result.size());
    }
}

class Person {
    private final String name;
    private final String email;
    private final String country;
    private final int salary;
    private final int experience;

    public Person(String name, String email, String country, int salary, int experience) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.salary = salary;
        this.experience =experience;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public int getSalary() {
        return salary;
    }

    public int getExperience() {
        return experience;
    }
}