package edu.carleton.expensetracker.model;

import java.io.*;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 */

public class Record implements java.io.Serializable{
    public List<Transaction> transactions;

    public Record(List<Transaction> transaction){
        this.transactions = transaction;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public void serializeRecord(){
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("/record.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in record.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void deserializeRecord(){
        Record e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("/record.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Record) in.readObject();
            in.close();
            fileIn.close();
            this.transactions = e.transactions;
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Record class not found");
            c.printStackTrace();
            return;
        }
    }

}
