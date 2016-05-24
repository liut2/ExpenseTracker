package edu.carleton.expensetracker.model;

import java.util.Date;

/**
 * Created by taoliu on 5/23/16.
 * Transaction.java
 * This enum serves as our model for expense tracker.
 */
public enum Transaction {
    INCOME("income"),
    EXPENSE("expense");

    private String type;
    private int value;
    private String category;
    private Date date;
    private String note;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
    Transaction(String type){
        this.type = type;
    }

    /*
    public static void main(String[] args) {
        Transaction tran =  Transaction.EXPENSE;
        tran.setCategory("food");
        tran.setNote("I love food");
        tran.setValue(14);
        tran.setDate(new Date());
        System.out.println(tran.toString());
    }
    */
}




