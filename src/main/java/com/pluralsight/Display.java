package com.pluralsight;
import java.util.Scanner;
import java.io.IOException;

public class Display {
    /** Scanner for user input. */
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Main loop, provides terminal interface for adding transactions and navigation.
     */
    public static void mainMenu() throws IOException {
        TransactionData.loadData();
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
        scanner.close();
        TransactionData.closeWriter();
    }

    /**
     * Ledger menu loop, provides terminal interface for viewing data file.
     */
    public static void ledgerMenu() throws IOException {
        boolean running = true;
        while (running) {
            String choice = promptString("A) All\nD) Deposits\nP) Payments\nR) Reports\nH) Home").toUpperCase();
            switch (choice) {
                case "A" -> TransactionData.viewAll();
                case "D" -> TransactionData.viewDeposits();
                case "P" -> TransactionData.viewPayments();
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
            String choice = promptString("1) Month To Date\n2) Previous Month\n3) Year To Date\n4) Previous Year\n5) Search by Vendor\n6) Custom Search\n0) Back").toUpperCase();
            switch (choice) {
                case "1" -> TransactionData.viewMonthToDate();
                case "2" -> TransactionData.viewPreviousMonth();
                case "3" -> TransactionData.viewYearToDate();
                case "4" -> TransactionData.viewPreviousYear();
                case "5" -> TransactionData.viewByVendor(promptString("Please enter vendor name"));
                case "6" -> customSearch();
                case "0" -> running = false;
            }
        }
    }

    /**
     * Allows user to search with Start Date, End Date, Description, Vendor, and Amount. Case sensitive.
     */
    private static void customSearch() throws IOException {
        System.out.println("Welcome to Custom Search. Leave any field empty to not search.");
        String startDate = promptString("Start Date (yyyy-MM-dd format)");
        String endDate = promptString("End Date (yyyy-MM-dd) format");
        String description = promptString("Description");
        String vendor = promptString("Vendor");
        String amount = promptString("Amount");
        TransactionData.viewByCustomSearch(startDate, endDate, description, vendor, amount);
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
