package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class Transaction {

    // private fields, public getters and (setters) -- public final is old practice -- getters are standard now
    // this is OK-ish because the data-types are all immutable, couldn't do on mutable like list or map

    /** The date which the transaction was entered. */
    private final LocalDate date;
    /** The time which the transaction was entered. */
    private final LocalTime time;
    /** The description of the transaction. */
    private final String description;
    /** The vendor associates with this transaction. */
    private final String vendor;
    /** The amount of the transaction. Negative indicates a payment, positive indicates a deposit. */
    private final float amount;
    /** The string representation of a transaction. In the format:
     * <p>
     * {@code yyyy-MM-dd|HH:mm:ss|DESCRIPTION|VENDOR|AMOUNT}
     */
    private final String information;
    /** The string array with tags of a transaction. In the format:
     *
     */
    private final String[] tags;

    /**
     * @param information
     * This constructor takes in a string formatted like the following:
     * <p>
     * {@code YYYY-MM-DD|HH:MM:SS|DESCRIPTION|VENDOR|AMOUNT}
     * <p>
     * and assigns the following values to the instance variables, from
     * left to right - date, time, description, vendor, and amount.
     */
    public Transaction(String information) {
        this.information = information;
        String[] values = information.split("\\|");
        this.date = LocalDate.parse(values[0]);
        this.time = LocalTime.parse(values[1]);
        this.description = values[2];
        this.vendor = values[3];
        this.amount = Float.parseFloat(values[4]);
        this.tags = values[5].split(",");
    }

    /**
     * Gets the date which the transaction was tracked, based off the {@code String} input to the constructor
     * @return The date which the transaction was tracked.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the time which the transaction was tracked, based off the {@code String} input to the constructor
     * @return The time which the transaction was tracked.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Gets the description of the transaction was tracked, based off the {@code String} input to the constructor
     * @return The description of the transaction.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the vendor of the transaction, based off the {@code String} input to the constructor
     * @return The vendor of the transaction.
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Gets the amount of money associated with the transaction, based off the {@code String} input to the constructor
     * @return The amount of money associated with the transaction.
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Gets the {@code String} with all transaction attributes, the same as the {@code String} input to the constructor.
     * @return A {@code String} with all attributes of transaction.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Gets the {@code String[]} with all tags related to the transaction.
     * @return A {@code String[]} with all tags of transaction.
     */
    public String[] getTags() {
        return this.tags;
    }

    /** Returns the {@code String} representation of the {@code Transaction} object.
     * @return {@code YYYY-MM-DD|HH:MM:SS|DESCRIPTION|VENDOR|AMOUNT}
     */
    @Override
    public String toString() {
        return this.description +
                " ".repeat(30 - this.description.length()) +
                this.vendor +
                " ".repeat(30 - this.vendor.length()) + "$" +
                this.amount +
                " ".repeat(30 - Float.toString(this.amount).length()) +
                Arrays.toString(this.tags) +
                " ".repeat(30 - Arrays.toString(this.tags).length()) +
                this.date +
                " ".repeat(30 - this.date.toString().length()) +
                this.time +
                " ".repeat(30 - this.time.toString().length());

//        return "Description: " + this.description + "\nVendor: " + this.vendor + "\nAmount: $" + this.amount + "\nTags: " + Arrays.toString(this.tags) + "\nCreated: " + this.date + ", " + this.time + "\n";
    }
}
