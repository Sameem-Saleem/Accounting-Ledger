package com.pluralsight;
import java.io.*;
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
    /** Array holding all transaction data. */
    private static final ArrayList<Transaction> data = new ArrayList<>();
    /**
     * Main program loop, provides terminal interface for options and interacting with data file.
     */
    public static void main(String[] args) throws IOException {
        loadData();

        writer = new BufferedWriter(new FileWriter(dataFileName, true));

        boolean running = true;
        while (running) {
            String choice = promptString("D) Add Deposit\nP) Make Payment\nL) Ledger\nX) Exit").toUpperCase();
            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> useLedger();
                case "X" -> running = false;
            }
        }
        writer.close();
        scanner.close();
    }

    /**
     * Reads every line but the first in data file csv and stores them in list.
     */
    private static void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFileName));
//      Read the first header line without saving it to get rid of it
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            data.add(new Transaction(line));
            line = reader.readLine();
        }
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
     * Prompts the user with necessary attributes of {@code Transaction} deposit, and appends it to data file.
     */
    private static void addDeposit() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = promptString("What is the description of this deposit?");
        String vendor = promptString("Who was the vendor correlated with this deposit?");
        String amount = String.valueOf(Float.parseFloat(promptString("How much was the deposit?")));

        String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        data.add(new Transaction(transactionString));
        writer.write(transactionString);
        writer.newLine();
    }

    /**
     * Prompts the user with necessary attributes of {@code Transaction} payment, and appends it to data file.
     */
    private static void addPayment() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = promptString("What is the description of this payment?");
        String vendor = promptString("Who was the vendor correlated with this payment?");
        String amount = String.valueOf(-1 * Float.parseFloat(promptString("How much was the payment?")));

        String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        data.add(new Transaction(transactionString));
        writer.write(transactionString);
        writer.newLine();
    }

    /**
     * Ledger loop, provides terminal interface for options to view and search data file.
     */
    private static void useLedger() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("A) All\nD) Deposits\nP) Payments\nR) Reports\nH) Home").toUpperCase();
            switch (choice) {
                case "A" -> viewAll();
                case "D" -> viewDeposits();
                case "P" -> viewPayments();
                case "R" -> useReport();
                case "H" -> running = false;
            }
        }
    }

    /**
     * Report loop, provides terminal interface for sorting and searching data file.
     */
    private static void useReport() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("A) All\nD) Deposits\nP) Payments\nR) Reports\nH) Home").toUpperCase();
            switch (choice) {
                case "A" -> viewAll();
                case "D" -> viewDeposits();
                case "P" -> viewPayments();
                case "R" -> System.out.println("todo");
                case "H" -> running = false;
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file.
     */
    private static void viewAll() throws IOException {
        for (Transaction n : data) {
            System.out.println(n);
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with positive amount, meaning a deposit.
     */
    private static void viewDeposits() throws IOException {
        for (Transaction n : data) {
            if (n.amount > 0) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    private static void viewPayments() throws IOException {
        for (Transaction n : data) {
            if (n.amount < 0) {
                System.out.println(n);
            }
        }
    }
}
