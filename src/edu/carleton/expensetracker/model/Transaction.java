package edu.carleton.expensetracker.model;

import java.io.*;
import java.util.Date;

/**
 * This class is the core object user creates in order to view their records
 */
public class Transaction implements java.io.Serializable{

    private TransactionType type;
    private int value;
    private String category;
    private Date date;
    private String note;

    public Transaction(TransactionType type, Date date,String category, String note,int value) {
        this.type = type;
        this.value = value;
        this.category = category;
        this.date = date;
        this.note = note;
    }

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



