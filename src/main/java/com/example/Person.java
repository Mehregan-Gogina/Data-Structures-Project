package com.example;

import java.util.List;

public class Person {
    private final int id;
    private final String name;
    private final int yearOfBirth;
    private final String university;
    private final String major;
    private final String jobPlace;
    private final List<Integer> connections;

    public Person(int id, String name, int yearOfBirth, String university, String major, String jobPlace, List<Integer> connections) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.university = university;
        this.major = major;
        this.jobPlace = jobPlace;
        this.connections = connections;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getUniversity() {
        return university;
    }

    public String getMajor() {
        return major;
    }

    public String getJobPlace() {
        return jobPlace;
    }

    public List<Integer> getConnections() {
        return connections;
    }
}