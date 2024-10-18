package com.pluralsight;
import java.io.*; //FileWriter, FileReader, BufferedWriter, BufferedReader, IOException
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionData {
    /** Writer to append onto data file. */
    private static BufferedWriter writer;
    /** Array holding all transaction data. */
    private static final ArrayList<Transaction> data = new ArrayList<>();
    /** The csv file which transaction information is written to. */
    private static final String DATAFILENAME = "transactions.csv";
    /**
     * Reads every line but the first in data file csv and stores them in list.
     */
    public static void loadData() throws IOException {
//        Initialize the writer for later use
        writer = new BufferedWriter(new FileWriter(DATAFILENAME, true));
        BufferedReader reader = new BufferedReader(new FileReader(DATAFILENAME));
//        Read the first header line without saving it to get rid of it
        reader.readLine();
        String line = reader.readLine();
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

        String amount = String.valueOf(Float.parseFloat(Display.promptString("How much was the deposit?")));

        if (Float.parseFloat(amount) <= 0) {
            String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
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

        String amount = String.valueOf(-1 * Float.parseFloat(Display.promptString("How much was the payment?")));

        if (Float.parseFloat(amount) <= 0) {
            String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
            data.add(new Transaction(transactionString));
            writer.write(transactionString);
            writer.newLine();
        } else {
            System.out.println("Not saved: Please enter a positive number next time");
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file.
     */
    public static void viewAll() throws IOException {
        for (Transaction n : getData()) {
            System.out.println(n);
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with positive amount, meaning a deposit.
     */
    public static void viewDeposits() throws IOException {
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
        for (Transaction n : getData()) {
            if (n.getVendor().equals(vendor)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with matching parameters. Does not search parameter string empty.
     * @param startDate The start date (yyyy-MM-dd format) to match Transactions with.
     * @param endDate The end date (yyyy-MM-dd format) to match Transactions with.
     * @param description The description to match Transactions with.
     * @param vendor The vendor to match Transactions with.
     * @param amount The amount to match Transactions with.
     */
    public static void viewByCustomSearch(String startDate, String endDate, String description, String vendor, String amount) throws IOException {
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

        for (Transaction n : searchResult) {
            System.out.println(n);
        }
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
