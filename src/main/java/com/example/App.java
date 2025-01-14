package com.example;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) {
        String resource = "/inputFile.json";
        InputStream input = App.class.getResourceAsStream(resource);

        // error handling
        if (input == null) {
            System.out.println("File not found!");
            return;
        }

        List<Person> people = parseJsonFile(input);

        // error handling
        if (people.isEmpty()) {
            System.out.println("No people in the JSON file.");
            return;
        }

        Graph graph = buildGraph(people);

        try (Scanner scanner = new Scanner(System.in)) {
            int personID;
            while (true) {
                System.out.print("Enter ID of the person (-1 for exit): ");
                personID = scanner.nextInt();
                //base case
                if (personID == -1) {
                    break;
                }

                if (graph.personExists(personID)) {
                    List<Connection> suggestions = graph.suggestConnections(personID);
                    graph.printSuggestions(suggestions);
                } else {
                    System.out.println("Person with ID " + personID + " does not exist.");
                }
                System.out.println("------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Person> parseJsonFile(InputStream input) {
        List<Person> people = new ArrayList<>();

        try {
            StringBuilder jsonText = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonText.append(line);
                }
            }

            JSONArray jsonArray = new JSONArray(jsonText.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("ID");
                String name = jsonObject.getString("Name");
                int yearOfBirth = jsonObject.getInt("Year of Birth");
                String university = jsonObject.getString("University");
                String major = jsonObject.getString("Major");
                String jobPlace = jsonObject.getString("Job Place");
                JSONArray connectionsArray = jsonObject.getJSONArray("Connections");

                List<Integer> connections = new ArrayList<>();
                for (int j = 0; j < connectionsArray.length(); j++) {
                    connections.add(connectionsArray.getInt(j));
                }

                Person person = new Person(id, name, yearOfBirth, university, major, jobPlace, connections);
                people.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }

    private static Graph buildGraph(List<Person> people) {
        Graph graph = new Graph();
        for (Person person : people) {
            graph.addPerson(person);
        }
        return graph;
    }
}