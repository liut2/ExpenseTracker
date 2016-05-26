package edu.carleton.expensetracker.model;

import java.io.*;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 * This class is used to store a list of transactions.
 */

public class Record implements java.io.Serializable{
    public List<Transaction> transactions;

    public Record(List<Transaction> transaction){
        this.transactions = transaction;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }


}
