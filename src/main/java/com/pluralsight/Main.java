package com.pluralsight;
import java.io.*;
import java.util.ArrayList;

public class Main {
    private static final String dataFileName = "transactions.csv";
    public static void main(String[] args) {
        for (Transaction n : pullData()) {
            System.out.println(n);
        }
    }

    private static ArrayList<Transaction> pullData() {
        ArrayList<Transaction> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(new Transaction(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
