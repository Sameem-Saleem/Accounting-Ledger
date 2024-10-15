package com.pluralsight;
import java.io.*;
import java.nio.Buffer;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    /** The csv file which transaction information is written to. */
    private static final String dataFileName = "transactions.csv";
    /** Scanner for user input. */
    private static final Scanner scanner = new Scanner(System.in);
    // todo
    private static BufferedWriter writer;
    /**
     * Main program loop, provides terminal interface for options and interacting with data file.
     */
    public static void main(String[] args) throws IOException {
        writer = new BufferedWriter(new FileWriter(dataFileName));

        boolean running = true;
        while (running) {
            String choice = promptString("D) Add Deposit\nP) Make Payment\nL) Ledger\nX) Exit").toUpperCase();
            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> useLedger();
                case "X" -> running = false;
            }
            clearScreen();
        }
        writer.close();
        scanner.close();
    }

    /**
     * Reads every line but the first in data file csv and stores them in list.
     * @return Array with Transaction objects pulled from data file.
     */
    private static ArrayList<Transaction> pullData() throws IOException {
        ArrayList<Transaction> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(dataFileName));
//      Read the first header line to get rid of it
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            data.add(new Transaction(line));
        }
        return data;
    }

    /**
     * Prompts the user with the {@code prompt} parameter, then returns their input.
     * @param prompt What to prompt the user
     * @return User input.
     */
    private static String promptString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Clears the terminal
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    private static void addDeposit() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = promptString("What is the description of this deposit?");
        String vendor = promptString("Who was the vendor correlated with this deposit?");
        String amount = String.valueOf(Float.parseFloat(promptString("How much was the deposit?")));

        writer.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        writer.newLine();
    }

    private static void addPayment() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = promptString("What is the description of this payment?");
        String vendor = promptString("Who was the vendor correlated with this payment?");
        String amount = String.valueOf(-1 * Float.parseFloat(promptString("How much was the payment?")));

        writer.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        writer.newLine();
    }

    private static void useLedger() {
        boolean running = true;
        while (running) {
            String choice = promptString("A) All\nD) Deposits\nP) Payments\nR) Reports\nH) Home").toUpperCase();
            switch (choice) {
                case "A" -> System.out.println("todo");
                case "D" -> System.out.println("todo");
                case "P" -> System.out.println("todo");
                case "R" -> System.out.println("todo");
                case "H" -> running = false;
            }
            clearScreen();
        }
    }
}
