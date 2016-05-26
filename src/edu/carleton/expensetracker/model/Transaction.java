package edu.carleton.expensetracker.model;

import java.io.*;
import java.util.Date;

/**
 * Created by taoliu on 5/23/16.
 * Transaction.java
 * This class is the unit block object.
 */
public class Transaction implements java.io.Serializable{

    private TransactionType type;
    private int value;
    private String category;
    private Date date;
    private String note;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return "Type is " + type + " value is " + value + " category is " + category + " date is " + date + " and note is "+ note;
    }
    public Transaction(TransactionType type){
        this.type = type;
    }

}




