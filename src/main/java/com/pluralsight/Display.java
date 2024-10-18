package com.pluralsight;
import java.util.Scanner;
import java.io.IOException;

public class Display {
    /** Scanner for user input. */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Starting screen, provides terminal interface for logging in or creating account.
     */
    public static void start() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("A) Access\nD) Default\nX) Exit").toUpperCase();
            switch (choice) {
                case "A" -> login();
                case "D" -> loadDefault();
                case "X" -> running = false;
            }
        }
    }

    /**
     * Main loop, provides terminal interface for adding transactions and navigation.
     */
    public static void mainMenu() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("D) Add Deposit\nP) Make Payment\nL) Ledger\nX) Exit").toUpperCase();
            switch (choice) {
                case "D" -> TransactionData.addDeposit();
                case "P" -> TransactionData.addPayment();
                case "L" -> ledgerMenu();
                case "X" -> running = false;
            }
        }
        // Exiting this loop closes the program
        scanner.close();
        TransactionData.closeWriter();
        System.exit(0);
    }

    /**
     * Ledger menu loop, provides terminal interface for viewing data file.
     */
    public static void ledgerMenu() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("A) All\nD) Deposits\nP) Payments\nT) Tags\nR) Reports\nH) Home").toUpperCase();
            switch (choice) {
                case "A" -> TransactionData.viewAll();
                case "D" -> TransactionData.viewDeposits();
                case "P" -> TransactionData.viewPayments();
                case "T" -> TransactionData.viewTags();
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
            String choice = promptString("1) Month To Date\n2) Previous Month\n3) Year To Date\n4) Previous Year\n5) Search by Vendor\n6) Custom Search\n7) Search by Tags\n0) Back").toUpperCase();
            switch (choice) {
                case "1" -> TransactionData.viewMonthToDate();
                case "2" -> TransactionData.viewPreviousMonth();
                case "3" -> TransactionData.viewYearToDate();
                case "4" -> TransactionData.viewPreviousYear();
                case "5" -> TransactionData.viewByVendor(promptString("Please enter vendor name"));
                case "6" -> TransactionData.viewByCustomSearch();
                case "7" -> TransactionData.searchTags();
                case "0" -> running = false;
            }
        }
    }

    /** Load information for specified user. */
    public static void login() throws IOException {
        String username = promptString("Enter access point");
        TransactionData.loadData(username + ".csv");
        mainMenu();
    }

    /** Load information for transactions.csv, the default file. */
    public static void loadDefault() throws IOException {
        TransactionData.loadData("transactions.csv");
        mainMenu();
    }

    /**
     * Prompts the user with the {@code prompt} parameter, then returns their input.
     * @param prompt What to prompt the user
     * @return User input.
     */
    public static String promptString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
