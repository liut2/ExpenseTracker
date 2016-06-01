package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.*;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
 * CreateController.java
 * This file serves to control the events on create expense scene.
 */
public class CreateController {

    private int value;
    private String type;
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
        this.type = "expense";

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

        Record wrapper = new Record();
        List<Transaction> transactions = wrapper.deserializeRecord();
        for (Transaction tran : transactions) {
            System.out.println(tran.toString());
        }
    }

    /**
     * switch to Expense mode, on click the expense button
     * @param event
     */
    @FXML
    public void onClickExpenseButton(ActionEvent event) {
        System.out.println("clicked expense button");
        this.type = "expense";
        addExpenseCategories();
        this.category = "Food";
    }

    /**
     * switch to Income mode, on click the income button
     * @param event
     */
    @FXML
    public void onClickIncomeButton(ActionEvent event) {
        System.out.println("clicked income button");
        this.type = "income";
        addIncomeCategories();
        this.category = "Salary";
        System.out.println("reset category to " + this.category);
    }

    /**
     * update user's chosen date, on click the date picker
     * @param event
     */
    @FXML
    public void onClickDatePicker(ActionEvent event) {
        LocalDate chosenDate = datePicker.getValue();
        System.out.println(chosenDate.toString());
        checkIfValidDate(chosenDate);
    }

    /**
     * update user's chosen category, on click the category combo box
     * @param event
     */
    @FXML
    public void onClickCombo(ActionEvent event) {
        if (categoryList.getValue() != null) {
            this.category = categoryList.getValue().toString();
        }
        System.out.println("category is " + this.category);
    }

    /**
     * redirect user back to the home scene, on click the back button
     * @param event
     */
    @FXML
    public void onClickBack(ActionEvent event) {
        System.out.println("Back to home");
        //redirect to home page
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main main = new Main();
        stageTheEventSourceNodeBelongs.setScene(main.viewExpenseScene());
    }

    /**
     * check if all inputs are valid and then store the user record if so, on click the submit button
     * @param event
     */
    @FXML
    public void onClickSubmit(ActionEvent event){
        //check value input
        String valueInput = valueTextField.getText();
        int checkValueRes =  checkIfValidValue(valueInput);

        //check date input
        LocalDate chosenDate = datePicker.getValue();
        int checkDateRes =  checkIfValidDate(chosenDate);

        //check note input
        this.note = noteTextField.getText();

        //if all inputs are valid, then store the record
        if (checkValueRes == 1 && checkDateRes == 2) {
            //create transaction object
            Transaction tran;
            if(this.type.compareTo("income")==0){
                tran = new IncomeTransaction();
            }else{
                tran = new ExpenseTransaction();
            }
            Instant instant = Instant.from(chosenDate.atStartOfDay(ZoneId.systemDefault()));
            this.chosenDate = Date.from(instant);
            tran.setDate(this.chosenDate);
            tran.setValue(this.value);
            tran.setNote(this.note);
            tran.setCategory(this.category);

            //add to permanent storage
            Record wrapper = new Record();
            List<Transaction> transactions = wrapper.deserializeRecord();
            transactions.add(tran);
            wrapper.addTransactions(transactions);
            wrapper.serializeRecord();
            System.out.println("success");

            //redirect to home scene
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Main main = new Main();
            stageTheEventSourceNodeBelongs.setScene(main.viewExpenseScene());
        } else {
            System.out.println("failed");
        }
    }

    /**
     * return true if the value is an integer
     * @param valueInput
     * @return int
     */
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

    /**
     * a helper function to determine if the input value is integer
     * @param str
     * @return boolean
     */
    public boolean isInteger(String str) {
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (!Character.isDigit(cur)) {
                return false;
            }
        }
        return true;
    }

    /**
     * return true if user selected a valid date
     * @param chosenDate
     * @return boolean
     */
    public int checkIfValidDate(LocalDate chosenDate) {
        Date now = new Date();
        LocalDate today = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (chosenDate == null) {
            dateString.set("Please choose a date. It cannot be empty");
            dateLabel.setTextFill(Color.RED);
            return 0;
        }
        else if (today.isBefore(chosenDate)) {
            dateString.set("Please choose a date no later than today");
            dateLabel.setTextFill(Color.RED);
            return 1;
        } else {
            dateString.set("Please choose a date when transaction happened");
            dateLabel.setTextFill(Color.BLACK);
            Instant instant = Instant.from(chosenDate.atStartOfDay(ZoneId.systemDefault()));
            this.chosenDate = Date.from(instant);
            return 2;
        }
    }

    /**
     * dynamically add the combox box items in expense mode
     */
    public void addExpenseCategories(){
        categoryList.getItems().clear();
        categoryList.setPromptText("Food");
        for (String category: expenseCategories) {
            categoryList.getItems().add(category);
        }
    }

    /**
     * dynamically add the combox box items in income mode
     */
    public void addIncomeCategories(){
        categoryList.getItems().clear();
        categoryList.setPromptText("Salary");
        for (String category: incomeCategories) {
            categoryList.getItems().add(category);
        }
    }
}
