package com.example;

public class Connection {
    private final Person person;
    private final int score;

    public Connection(Person person, Integer score) {
        this.person = person;
        this.score = score;
    }

    public Person getPerson() {
        return person;
    }

    public int getScore() {
        return score;
    }
}