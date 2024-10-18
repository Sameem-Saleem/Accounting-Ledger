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

        // TODO: return here if input == 0 OR negative number <= 0
        String amount = String.valueOf(Float.parseFloat(Display.promptString("How much was the deposit?")));

        String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        data.add(new Transaction(transactionString));
        writer.write(transactionString);
        writer.newLine();
    }

    /**
     * Prompts the user with necessary attributes of {@code Transaction} payment, and appends it to data file.
     */
    public static void addPayment() throws IOException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        String description = Display.promptString("What is the description of this payment?");
        String vendor = Display.promptString("Who was the vendor correlated with this payment?");

        // TODO: return here if input == 0 OR negative number <= 0
        String amount = String.valueOf(-1 * Float.parseFloat(Display.promptString("How much was the payment?")));

        String transactionString = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        data.add(new Transaction(transactionString));
        writer.write(transactionString);
        writer.newLine();
    }

    /**
     * Outputs all {@code Transaction}s in data file.
     */
    public static void viewAll() throws IOException {
        for (Transaction n : getData()) {
            System.out.println(n);
        }
    }

// should be opposite order, newest first!

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
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    public static void viewMonthToDate() throws IOException {
        for (Transaction n : getData()) {
            if (n.getAmount() < 0) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    public static void viewPreviousMonth() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrMonth = LocalDate.parse(currentDate.getYear() + "-" + (currentDate.getMonthValue()) + "-" + "1", DateTimeFormatter.ofPattern("yyyy-M-d"));
        LocalDate startOfLastMonth = LocalDate.parse(currentDate.getYear() + "-" + (currentDate.getMonthValue() - 1) + "-" + "1", DateTimeFormatter.ofPattern("yyyy-M-d"));
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfLastMonth) && n.getDate().isBefore(startOfCurrMonth)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    public static void viewYearToDate() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrYear = LocalDate.parse((currentDate.getYear()) + "-01-01");
        for (Transaction n : getData()) {
            if (n.getDate().isAfter(startOfCurrYear)) {
                System.out.println(n);
            }
        }
    }

    /**
     * Outputs all {@code Transaction}s in data file with negative amount, meaning a payment.
     */
    public static void viewPreviousYear() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrYear = LocalDate.parse((currentDate.getYear()) + "-01-01");
        LocalDate startOfLastYear = LocalDate.parse((currentDate.getYear() - 1) + "-01-01");
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
