package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.*;

/**
 * Created by taoliu on 5/23/16.
 * ViewExpenseController.java
 * This file serves to control teh events on view expense scene.
 */
public class ViewExpenseController {
    @FXML
    private ComboBox<String> changeView;

    public void loadRecord(){
        Transaction e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("/transaction.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Transaction) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }
}
