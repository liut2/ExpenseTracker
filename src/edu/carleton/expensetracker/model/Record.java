package edu.carleton.expensetracker.model;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 */

public class Record implements java.io.Serializable{
    public List<Transaction> transactions;

    public Record(){
        this.transactions = new ArrayList<Transaction>();
    }

    public void addTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public void serializeRecord(){
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("./resources/data/record.ser");
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

    public List<Transaction> deserializeRecord(){
        Record e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("./resources/data/record.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Record) in.readObject();
            System.out.println(e.transactions.size());
            in.close();
            fileIn.close();
            return e == null? new ArrayList<Transaction>(): e.transactions;

        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Record class not found");
            c.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Record wrapper = new Record();
        wrapper.addTransactions(Test.test());
        wrapper.serializeRecord();

        /*
        List<Transaction> transactions = wrapper.deserializeRecord();
        for (Transaction tran : transactions) {
            System.out.println(tran.toString());
        }
        */
    }

}