package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.model.Transaction;
import java.io.*;
/**
 * Created by taoliu on 5/23/16.
 * CreateExpenseController.java
 * This file serves to control the events on create expense scene.
 */
public class CreateExpenseController {
    public void addRecord(Transaction tran){
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("/transaction.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tran);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in transaction.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

}
