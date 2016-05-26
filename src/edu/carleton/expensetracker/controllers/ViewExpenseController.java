package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by taoliu on 5/23/16.
 * ViewExpenseController.java
 * This file serves to control teh events on view expense scene.
 */
public class ViewExpenseController {
    @FXML
    private ComboBox<String> changeView;
    @FXML
    public void onClickCreateNewRecord(ActionEvent event) {
        //redirect to create page
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main main = new Main();
        stageTheEventSourceNodeBelongs.setScene(main.createExpenseScene());
    }
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
