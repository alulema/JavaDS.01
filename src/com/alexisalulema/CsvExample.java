package com.alexisalulema;

import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvExample {
    public static void run() {
        List<Person> result = new ArrayList<>();

        InputStream fStream = Main.class.getResourceAsStream("person.csv");
        InputStreamReader fReader = new InputStreamReader(fStream, StandardCharsets.UTF_8);

        try (BufferedReader reader = new BufferedReader(fReader)) {}
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