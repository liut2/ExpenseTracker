package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.Transaction;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import edu.carleton.expensetracker.model.TransactionType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by taoliu on 5/23/16.
 * CreateExpenseController.java
 * This file serves to control the events on create expense scene.
 */
public class CreateExpenseController {
    private int value;
    private TransactionType type;
    private String note;
    private StringProperty dateString;
    private StringProperty valueString;
    private StringProperty noteString;
    private Date chosenDate;
    private String category;
    private String[] expenseCategories;
    private String[] incomeCategories;

    @FXML
    private VBox vbox;
    @FXML
    private ComboBox categoryList;
    @FXML
    private TextField noteTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label valueLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ToggleButton expense;
    @FXML
    private ToggleButton income;
    @FXML
    private ToggleGroup transactionType;
    @FXML
    public void initialize(){

        //init vbox position
        vbox.setPadding(new Insets(50, 50, 50, 50));
        //init toggle button
        expense.setSelected(true);
        income.setSelected(false);
        this.type = TransactionType.EXPENSE;

        //init date label
        dateString = new SimpleStringProperty();
        dateString.set("Please choose a date when transaction happened");
        dateLabel.textProperty().bind(dateString);

        //init value label
        valueString = new SimpleStringProperty();
        valueString.set("Please enter the amount of transaction in integer");
        valueLabel.textProperty().bind(valueString);

        //init note label
        noteString = new SimpleStringProperty();
        noteString.set("Please specify the source or usage of money (optional)");
        noteLabel.textProperty().bind(noteString);
        this.note = "";

        //init category list
        this.expenseCategories = new String[]{"Food", "Clothes", "Entertainment", "Gas", "Gifts", "Holidays", "Kids", "Shopping", "Sports", "Transportation"};
        this.incomeCategories = new String[]{"Salary", "Bonus", "Stock", "Lottery"};
        addExpenseCategories();
        this.category = "Food";
    }
    @FXML
    public void onClickExpenseButton(ActionEvent event) {
        System.out.println("clicked expense button");
        this.type = TransactionType.EXPENSE;
        addExpenseCategories();
        this.category = "Food";
    }
    @FXML
    public void onClickIncomeButton(ActionEvent event) {
        System.out.println("clicked income button");
        this.type = TransactionType.INCOME;
        addIncomeCategories();
        this.category = "Salary";
        System.out.println("reset category to " + this.category);
    }
    @FXML
    public void onClickDatePicker(ActionEvent event) {
        LocalDate chosenDate = datePicker.getValue();
        System.out.println(chosenDate.toString());
        checkIfValidDate(chosenDate);
    }
    @FXML
    public void onClickCombo(ActionEvent event) {
        if (categoryList.getValue() != null) {
            this.category = categoryList.getValue().toString();
        }
        System.out.println("category is " + this.category);
    }
    @FXML
    public void onClickBack(ActionEvent event) {
        System.out.println("Back to home");
        //redirect to home page
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main main = new Main();
        stageTheEventSourceNodeBelongs.setScene(main.viewExpenseScene());
    }
    @FXML
    public void onClickSubmit(ActionEvent event){
        //check if every input is valid

        //check value input
        String valueInput = valueTextField.getText();
        System.out.println("the value input is " + valueInput);
        int checkValueRes =  checkIfValidValue(valueInput);

        //check date input
        LocalDate chosenDate = datePicker.getValue();
        int checkDateRes =  checkIfValidDate(chosenDate);

        //check note input
        this.note = noteTextField.getText();

        //check category

        System.out.printf("check value and check date is %d %d %n", checkValueRes, checkDateRes);
        if (checkValueRes == 1 && checkDateRes == 2) {
            //create tran object
            Transaction tran = new Transaction(this.type);
            //set date
            Instant instant = Instant.from(chosenDate.atStartOfDay(ZoneId.systemDefault()));
            this.chosenDate = Date.from(instant);
            tran.setDate(this.chosenDate);
            //set value
            tran.setValue(this.value);
            //set note
            tran.setNote(this.note);
            //set category
            tran.setCategory(this.category);
            System.out.println(tran.toString());
            System.out.println("success");
            //redirect to home page
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Main main = new Main();
            stageTheEventSourceNodeBelongs.setScene(main.viewExpenseScene());
        } else {
            System.out.println("failed");
        }
    }
    public int checkIfValidValue(String valueInput){
        if (valueInput == null || valueInput.equals("") || !isInteger(valueInput)) {
            valueString.set("Please enter a valid integer");
            valueLabel.setTextFill(Color.RED);
            return 0;
        } else {
            valueString.set("Please enter the amount of transaction in integer");
            valueLabel.setTextFill(Color.BLACK);
            this.value = Integer.parseInt(valueInput);
            return 1;
        }
    }
    public boolean isInteger(String str) {
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (!Character.isDigit(cur)) {
                return false;
            }
        }
        return true;
    }
    public int checkIfValidDate(LocalDate chosenDate) {
        Date now = new Date();
        LocalDate today = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //System.out.println(today.toString());
        if (chosenDate == null) {
            dateString.set("Please choose a date. It cannot be empty");
            dateLabel.setTextFill(Color.RED);
            return 0;
        }
        else if (today.isBefore(chosenDate)) {
            //System.out.println("wrong date!");
            dateString.set("Please choose a date no later than today");
            dateLabel.setTextFill(Color.RED);
            return 1;
        } else {
            //System.out.println("correct date!");
            dateString.set("Please choose a date when transaction happened");
            dateLabel.setTextFill(Color.BLACK);
            Instant instant = Instant.from(chosenDate.atStartOfDay(ZoneId.systemDefault()));
            this.chosenDate = Date.from(instant);
            //System.out.println("the chosen date is " + this.chosenDate);
            return 2;
        }
    }

    public void addExpenseCategories(){
        categoryList.getItems().clear();
        categoryList.setPromptText("Food");
        for (String category: expenseCategories) {
            categoryList.getItems().add(category);
        }
    }

    public void addIncomeCategories(){
        categoryList.getItems().clear();
        categoryList.setPromptText("Salary");
        for (String category: incomeCategories) {
            categoryList.getItems().add(category);
        }
    }

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
