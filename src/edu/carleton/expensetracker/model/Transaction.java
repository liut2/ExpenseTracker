package edu.carleton.expensetracker.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is the core object user creates in order to view their records
 */
public class Transaction implements java.io.Serializable{

    private int value;
    private String category;
    private Date date;
    private String note;
    private String type;
    private String displayDate;

    public Transaction(Date date,String category, String note,int value) {
        this.value = value;
        this.category = category;
        this.date = date;
        this.note = note;
        this.displayDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public Transaction(String type){
        this.type = type;
    }
    public  String getType(){
        return type;
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
    public String getDisplayDate(){
        return displayDate;
    }
    public void setDate(Date date) {
        this.date = date;
        this.displayDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return " value is " + value + " category is " + category + " date is " + date + " and note is "+ note;
    }

}



