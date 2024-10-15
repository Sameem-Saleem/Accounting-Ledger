package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    /** The date which the transaction was entered. */
    public final LocalDate date;
    /** The time which the transaction was entered. */
    public final LocalTime time;
    /** The description of the transaction. */
    public final String description;
    /** The vendor associates with this transaction. */
    public final String vendor;
    /** The amount of the transaction. Negative indicates a payment, positive indicates a deposit. */
    public final float amount;
    /** The string representation of a transaction. In the format:
     * <p>
     * {@code yyyy-MM-dd|HH:mm:ss|DESCRIPTION|VENDOR|AMOUNT}
     */
    private final String information;

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
    }

    /** Returns the string representation of the {@code Transaction} object.
     * @return {@code YYYY-MM-DD|HH:MM:SS|DESCRIPTION|VENDOR|AMOUNT}
     */
    @Override
    public String toString() {
//        return date + "|" + time + "|" + description + "|" +
        return this.information;
    }
}
