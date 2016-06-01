package edu.carleton.expensetracker.model;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores a list of transactions
 */

public class Record implements java.io.Serializable{
    public List<Transaction> transactions;

    public Record(){
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     *  Update the transaction list as a whole to the newest one.
     *  @param transactions
     */
    public void addTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }

    /**
     *  Serialize the record object
     */
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

    /**
     *  Deserialize the record object
     */
    public List<Transaction> deserializeRecord(){
        Record e = null;
        if(! new File("./resources/data/record.ser").isFile()){
            return new ArrayList<Transaction>();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream("./resources/data/record.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                e = (Record) in.readObject();
                System.out.println(e.transactions.size());
                in.close();
                fileIn.close();
                return e == null ? new ArrayList<Transaction>() : e.transactions;

            } catch (IOException i) {
                i.printStackTrace();
                return new ArrayList<Transaction>();
            } catch (ClassNotFoundException c) {
                System.out.println("Record class not found");
                c.printStackTrace();
                return new ArrayList<Transaction>();
            }
        }
    }

    public static void main(String[] args) {
        Record wrapper = new Record();
        wrapper.addTransactions(Test.test());
        wrapper.serializeRecord();
    }

}