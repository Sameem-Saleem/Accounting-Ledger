package com.pluralsight;
import java.io.*; //FileWriter, FileReader, BufferedWriter, BufferedReader, IOException
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TransactionData {
    /** Writer to append onto data file. */
    private static BufferedWriter writer;
    /** Array holding all transaction data. */
    private static final ArrayList<Transaction> data = new ArrayList<>();
    /**
     * Reads every line but the first in data file csv and stores them in list.
     */
    public static void loadData(String file) throws IOException {
        // Initialize the writer for later use.
        writer = new BufferedWriter(new FileWriter(file, true));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Read the first header line without saving it to get rid of it.
        reader.readLine();
        String line = reader.readLine();
        // Add first line if not exists.
        if (line == null) {
            writer.write("DATE|TIME|DESCRIPTION|VENDOR|AMOUNT|TAGS");
            writer.newLine();
        }
        while (line != null) {
            data.add(new Transaction(line));
            line = reader.readLine();
        }
        reader.close();
    }

    /**
     * Prompts the user with necessary attributes of {@code Transaction} deposit, and appends it to data file.
     */
    public static void addDeposit() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = Display.promptString("What is the description of this deposit?");
        String vendor = Display.promptString("Who was the vendor correlated with this deposit?");
        String tags = Display.promptString("What tags are related? (Separate with comma, no spaces)");

        String amount = Display.promptString("How much was the deposit?");

        if (Float.parseFloat(amount) >= 0) {
            String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount + "|" + tags;
            data.add(new Transaction(transactionString));
            writer.write(transactionString);
            writer.newLine();
        } else {
            System.out.println("Not saved: Please enter a positive number next time.");
        }
    }

    /**
     * Prompts the user with necessary attributes of {@code Transaction} payment, and appends it to data file.
     */
    public static void addPayment() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = Display.promptString("What is the description of this payment?");
        String vendor = Display.promptString("Who was the vendor correlated with this payment?");
        String tags = Display.promptString("What tags are related. Separate with comma, no spaces");

        String amount = Display.promptString("How much was the payment?");

        if (Float.parseFloat(amount) >= 0) {
            String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + -1 * Float.parseFloat(amount) + "|" + tags;
            data.add(new Transaction(transactionString));
            writer.write(transactionString);
            writer.newLine();
        } else {
            System.out.println("Not saved: Please enter a positive number next time");
        }
    }

    /**
     * Prompts the user to search by one or more tags, searching all {@code Transaction}s in {transactions.csv}.
     */
    public static void searchTags() throws IOException {
        viewTags();
        String term = Display.promptString("Select a tag to search");
        for (Transaction n : getData()) {
            if (Arrays.asList(n.getTags()).contains(term)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all tags that are in the {@code Transaction} objects in {@code transactions.csv}. Doesn't repeat.
     */
    public static void viewTags() throws IOException {
        ArrayList<String> tags = new ArrayList<>();
        System.out.println("All tags are:");
        for (Transaction n : getData()) {
            for (String tag : n.getTags()) {
                if (!tags.contains(tag)) {
                    tags.add(tag);
                    System.out.println(tag);
                }
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file.
     */
    public static void viewAll() throws IOException {
        showHeader();
        for (Transaction n : getData()) {
            System.out.println(n);
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with positive amount, meaning a deposit.
     */
    public static void viewDeposits() throws IOException {
        showHeader();
        for (Transaction n : getData()) {
            if (n.getAmount() > 0) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    public static void viewPayments() throws IOException {
        showHeader();
        for (Transaction n : getData()) {
            if (n.getAmount() < 0) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file made after start of current month.
     */
    public static void viewMonthToDate() throws IOException {
        showHeader();
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrMonth = LocalDate.parse(currentDate.getYear() + "-" + (currentDate.getMonthValue()) + "-" + "1", DateTimeFormatter.ofPattern("yyyy-M-d")).minusDays(1);
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfCurrMonth)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file made before start of current month and after start of last month.
     */
    public static void viewPreviousMonth() throws IOException {
        showHeader();
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrMonth = LocalDate.parse(currentDate.getYear() + "-" + (currentDate.getMonthValue()) + "-" + "1", DateTimeFormatter.ofPattern("yyyy-M-d"));
        LocalDate startOfLastMonth = LocalDate.parse(currentDate.getYear() + "-" + (currentDate.getMonthValue() - 1) + "-" + "1", DateTimeFormatter.ofPattern("yyyy-M-d")).minusDays(1);
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfLastMonth) && n.getDate().isBefore(startOfCurrMonth)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file made after the start of the current year.
     */
    public static void viewYearToDate() throws IOException {
        showHeader();
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrYear = LocalDate.parse((currentDate.getYear()) + "-01-01").minusDays(1);
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfCurrYear)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file made before the start of this year and after the start of last year.
     */
    public static void viewPreviousYear() throws IOException {
        showHeader();
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrYear = LocalDate.parse((currentDate.getYear()) + "-01-01");
        LocalDate startOfLastYear = LocalDate.parse((currentDate.getYear() - 1) + "-01-01").minusDays(1);
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfLastYear) && n.getDate().isBefore(startOfCurrYear)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with matching vendor.
     * @param vendor The vendor to match Transactions with.
     */
    public static void viewByVendor(String vendor) throws IOException {
        showHeader();
        for (Transaction n : getData()) {
            if (n.getVendor().equals(vendor)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with matching inputs. Does not search empty inputs.
     * <p>
     * startDate The start date (yyyy-MM-dd format) to match Transactions with.
     * <p>
     * endDate The end date (yyyy-MM-dd format) to match Transactions with.
     * <p>
     * description The description to match Transactions with.
     * <p>
     * vendor The vendor to match Transactions with.
     * <p>
     * amount The amount to match Transactions with.
     * <p>
     * Note: This method is case-sensitive.
     */
    public static void viewByCustomSearch() throws IOException {
        System.out.println("Welcome to Custom Search. Leave any field empty to not search. Make sure to follow the format, or all data will not be saved.");
        String startDate = Display.promptString("Start Date (yyyy-MM-dd format)");
        String endDate = Display.promptString("End Date (yyyy-MM-dd) format");
        String description = Display.promptString("Description");
        String vendor = Display.promptString("Vendor");
        String amount = Display.promptString("Amount");

        ArrayList<Transaction> searchResult = getData();

        if (!startDate.equals("")) {
            searchResult.removeIf(n -> n.getDate().isBefore(LocalDate.parse(startDate)));
        }
        if (!endDate.equals("")) {
            searchResult.removeIf(n -> n.getDate().isAfter(LocalDate.parse(endDate)));
        }
        if (!description.equals("")) {
            searchResult.removeIf(n -> !n.getDescription().equals(description));
        }
        if (!vendor.equals("")) {
            searchResult.removeIf(n -> !n.getVendor().equals(vendor));
        }
        if (!amount.equals("")) {
            searchResult.removeIf(n -> n.getAmount() != Float.parseFloat(amount));
        }
        showHeader();
        for (Transaction n : searchResult) {
            System.out.println(n);
        }
    }
    /**
     * Displays header with all the categories.
     */
    public static void showHeader() {
        System.out.println("[Description" +
                "-".repeat(17) +
                "][Vendor" +
                "-".repeat(22) +
                "][Amount"+
                "-".repeat(22) +
                "][Tags" +
                "-".repeat(24) +
                "][Date" +
                "-".repeat(24) +
                "][Time" +
                "-".repeat(24) +
                "]");
    }

    /**
     * Provides a way to access the data in the TransactionData class.
     * @return Copy of data with all tracked Transaction instances in reverse order.
     */
    public static ArrayList<Transaction> getData() {
        ArrayList<Transaction> clone = new ArrayList<>(data);
        Collections.reverse(clone);
        return clone;
    }

    /**
     * Ensures that the writer is closed.
     */
    public static void closeWriter() throws IOException {
        writer.close();
    }
}
