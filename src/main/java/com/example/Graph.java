package com.example;

import java.util.*;

public class Graph {
    private final Map<Integer, Person> personMap;
    private final Map<Integer, Set<Integer>> adjacencyList;

    private static final int UNIVERSITY_WEIGHT = 20;
    private static final int MAJOR_WEIGHT = 50;
    private static final int JOB_PLACE_WEIGHT = 30;

    public Graph() {
        personMap = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addPerson(Person person) {
        personMap.put(person.getID(), person);
        adjacencyList.putIfAbsent(person.getID(), new HashSet<>());

        for (int connection : person.getConnections()) {
            adjacencyList.get(person.getID()).add(connection);
            adjacencyList.putIfAbsent(connection, new HashSet<>());
            adjacencyList.get(connection).add(person.getID());
        }
    }

    public boolean personExists(Integer personId) {
        return personMap.containsKey(personId);
    }

    public List<Connection> suggestConnections(int personId) {
        Person person = personMap.get(personId);
        if (person == null) {
            return Collections.emptyList();
        }

        Map<Integer, Integer> scores = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        DFS(personId, visited, scores, person);

        List<Connection> suggestions = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            if (entry.getValue() != 0) {
                suggestions.add(new Connection(personMap.get(entry.getKey()), entry.getValue()));
            }
        }

        suggestions.sort((sc1, sc2) -> Integer.compare(sc2.getScore(), sc1.getScore()));

        if (suggestions.isEmpty()) {
            System.out.println("No suitable suggestions found!");
        }

        return suggestions;
    }


    private void DFS(int personID, Set<Integer> visited, Map<Integer, Integer> scores, Person targetPerson) {
        //base case
        if (visited.contains(personID)) {
            return;
        }

        visited.add(personID);

        for (int neighborID : adjacencyList.get(personID)) {
            if (!visited.contains(neighborID) && (neighborID != targetPerson.getID())) {
                int score = calculateScore(targetPerson, personMap.get(neighborID));
                scores.put(neighborID, score);
                DFS(neighborID, visited, scores, targetPerson);
            }
        }
    }

    private int calculateScore(Person p1, Person p2) {
        int score = 0;
        if (p1.getUniversity().equals(p2.getUniversity())) {
            score += UNIVERSITY_WEIGHT;
        }
        if (p1.getMajor().equals(p2.getMajor())) {
            score += MAJOR_WEIGHT;
        }
        if (p1.getJobPlace().equals(p2.getJobPlace())) {
            score += JOB_PLACE_WEIGHT;
        }
        return score;
    }

    public void printSuggestions(List<Connection> suggestions) {
        for (Connection suggestedConnection : suggestions) {
            Person person = suggestedConnection.getPerson();
            System.out.println("Suggested Person: " + person.getName() + ", Score: " + suggestedConnection.getScore() + " %");
            System.out.println("ID: " + person.getID());
            System.out.println("Year of Birth: " + person.getYearOfBirth());
            System.out.println("University: " + person.getUniversity());
            System.out.println("Major: " + person.getMajor());
            System.out.println("Job Place: " + person.getJobPlace());
            System.out.println("Connections: " + person.getConnections());
            System.out.println();
        }
    }
}