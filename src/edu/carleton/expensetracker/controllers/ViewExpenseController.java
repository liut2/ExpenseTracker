package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;
import edu.carleton.expensetracker.model.listView.AnnualListView;
import edu.carleton.expensetracker.model.listView.DailyListView;
import edu.carleton.expensetracker.model.listView.MonthlyListView;
import edu.carleton.expensetracker.model.listView.WeeklyListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoliu on 5/23/16.
 * ViewExpenseController.java
 * This file serves to control teh events on view expense scene.
 */
public class ViewExpenseController {
    @FXML
    private ComboBox<String> changeView;
    @FXML
    private TableView table;
    @FXML
    private ToggleButton day;
    @FXML
    private ToggleButton week;
    @FXML
    private ToggleButton month;
    @FXML
    private ToggleButton year;

    private TableColumn type;
    private TableColumn note;
    private TableColumn value;
    private TableColumn category;
    private TableColumn date;
    private final ObservableList<Transaction> data = FXCollections.observableArrayList();
    private String timeRange = "day";
    private String displayView = "listView";
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public void initialize(){
        day.setSelected(true);
        test();
        listViewInit();
        listView();
    }

    public void listViewInit(){
        System.out.println("list view init");
        type = new TableColumn("type");
        type.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("type"));
        note = new TableColumn("note");
        note.setMinWidth(200);
        note.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("note"));
        value = new TableColumn("value");
        value.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("value"));
        date = new TableColumn("date");
        date.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("date"));
        category = new TableColumn("category");
        category.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("category"));

    }

    public void listView(){
        displayView = "listView";
        List<Transaction> tempData;
        if(timeRange.compareTo("day") == 0){
            DailyListView temp = new DailyListView(transactions);
            tempData = temp.getDailyTransactions();
        }else if(timeRange.compareTo("week") == 0){
            WeeklyListView temp = new WeeklyListView(transactions);
            tempData = temp.getWeeklyTransactions();
        }else if(timeRange.compareTo("month") == 0){
            MonthlyListView temp = new MonthlyListView(transactions);
            tempData = temp.getMonthlyTransactions();
        }else{
            AnnualListView temp = new AnnualListView(transactions);
            tempData = temp.getAnnualTransactions();
        }
        addToData(tempData);
        table.setItems(data);
        table.getColumns().addAll(type,value,category,date,note);
    }

    public void pieChartViewInit(){
        System.out.println("pie view init");
    }

    public void lineChartViewInit(){
        System.out.println("line view init");
    }

    @FXML
    public void onClickDayButton(ActionEvent event) {
        System.out.println("day button clicked");
        if(displayView.compareTo("listView") == 0){
            DailyListView temp = new DailyListView(transactions);
            List<Transaction> tempData = temp.getDailyTransactions();
            addToData(tempData);
        }
    }

    @FXML
    public void onClickWeekButton(ActionEvent event) {
        System.out.println("week button clicked");
        if(displayView.compareTo("listView") == 0){
            WeeklyListView temp = new WeeklyListView(transactions);
            List<Transaction> tempData = temp.getWeeklyTransactions();
            addToData(tempData);
        }
    }

    @FXML
    public void onClickMonthButton(ActionEvent event) {
        System.out.println("month button clicked");
        if(displayView.compareTo("listView") == 0){
            MonthlyListView temp = new MonthlyListView(transactions);
            List<Transaction> tempData = temp.getMonthlyTransactions();
            addToData(tempData);
        }
    }

    @FXML
    public void onClickYearButton(ActionEvent event) {
        System.out.println("year button clicked");
        if(displayView.compareTo("listView") == 0){
            AnnualListView temp = new AnnualListView(transactions);
            List<Transaction> tempData = temp.getAnnualTransactions();
            addToData(tempData);
        }
    }

    @FXML
    public void onClickCombo(ActionEvent event){
        String value = changeView.getValue();
        System.out.println(value);
        if(value.compareTo("List View") == 0){
            listViewInit();
        }else if(value.compareTo("Pie Chart")==0){
            pieChartViewInit();
        }else{
            lineChartViewInit();
        }
    }

    public void onClickCreateNewRecord(ActionEvent event) {
        //redirect to create page
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main main = new Main();
        stageTheEventSourceNodeBelongs.setScene(main.createExpenseScene());
    }

    public void test(){
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;
        try {
            date1 = newDateFormat.parse("05/12/2016");
            date2 = newDateFormat.parse("05/01/2013");
            date3 = newDateFormat.parse("05/24/2016");
            date4 = newDateFormat.parse("04/24/2016");
            date5 = new Date();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<5;i++){
            transactions.add(new Transaction(TransactionType.EXPENSE, date5,"food","newone"));
            transactions.add(new Transaction(TransactionType.EXPENSE, date1,"food","newone"));
            transactions.add(new Transaction(TransactionType.EXPENSE, date3,"food","newone"));
        }
    }

    public void addToData(List<Transaction> transactions){
        data.clear();
        data.addAll(transactions);
    }

    public List<Transaction> loadRecord(){
        Record e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("/transaction.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Record) in.readObject();
            in.close();
            fileIn.close();
            return e.transactions;
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }

}
