package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.lineCharts.AnnualLineChart;
import edu.carleton.expensetracker.model.lineCharts.myLineChart;
import edu.carleton.expensetracker.model.lineCharts.MonthlyLineChart;
import edu.carleton.expensetracker.model.lineCharts.WeeklyLineChart;
import edu.carleton.expensetracker.model.listView.AnnualListView;
import edu.carleton.expensetracker.model.listView.DailyListView;
import edu.carleton.expensetracker.model.listView.MonthlyListView;
import edu.carleton.expensetracker.model.listView.WeeklyListView;
import edu.carleton.expensetracker.model.pieCharts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by taoliu on 5/23/16.
 * ViewController.java
 * This file serves to control the events on view expense scene.
 */
public class ViewController {
    @FXML
    private ComboBox<String> changeView;
    @FXML
    private TableView table;
    @FXML
    private ToggleButton day;
    @FXML
    private VBox container;

    private LineChart<Number,Number> lineChart;
    private final ObservableList<Transaction> data = FXCollections.observableArrayList();
    private String timeRange = "day";
    private String displayView = "listView";
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private ObservableList<PieChart.Data> expensePieChartData = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> incomePieChartData = FXCollections.observableArrayList();
    private PieChart expenseChart;
    private PieChart incomeChart;
    private HBox hContainer;
    private boolean initialized = false;

    public void initialize(){
        // deserialize the record object
        Record wrapper = new Record();
        transactions = wrapper.deserializeRecord();
        // select the day button
        day.setSelected(true);
        // initialize the list view and pie chart view if not initialized
        if(!initialized){
            listViewInit();
            PieChartViewInit();
            initialized = true;
        }
        // display the list view
        listView();
    }

    /**
     * initialize the list view
     */
    private void listViewInit(){
        // create 5 column, each representing a property of a transaction
        TableColumn type = new TableColumn("type");
        type.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("type"));
        TableColumn note = new TableColumn("note");
        note.setMinWidth(200);
        note.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("note"));
        TableColumn  value = new TableColumn("value");
        value.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("value"));
        TableColumn date = new TableColumn("date");
        date.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("displayDate"));
        TableColumn category = new TableColumn("category");
        category.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("category"));
        // add 5 columns to the table
        table.getColumns().addAll(type, value, category, date, note);
    }

    /**
     * initialize the pie chart view
     */
    private void PieChartViewInit(){
        // create the expense pie chart and the income pie chart
        expenseChart = new PieChart(expensePieChartData);
        incomeChart = new PieChart(incomePieChartData);
        // create the Hbox container and add two pie charts to the container
        hContainer = new HBox();
        hContainer.getChildren().addAll(expenseChart,incomeChart);
    }

    /**
     * display the list view
     */
    public void listView(){
        // remove the object that is displayed currently
        if(displayView.compareTo("lineChart") == 0){
            container.getChildren().remove(lineChart);
            container.getChildren().add(1,table);
        }else if(displayView.compareTo("pieChart") == 0){
            container.getChildren().remove(hContainer);
            container.getChildren().add(1,table);
        }
        // set the display view to list
        displayView = "listView";
        // get the data for the indicated time range
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
        // clean up the previous data and add the new data
        data.clear();
        data.addAll(tempData);
        // add the data to the table
        table.setItems(data);
    }

    /**
     * display the pie chart view
     */
    private void pieChartView(){
        // remove the object that is displayed currently
        if(displayView.compareTo("listView") == 0) {
            container.getChildren().remove(table);
        }else if(displayView.compareTo("lineChart") == 0){
            container.getChildren().remove(lineChart);
        }
        // set the display view to pie chart
        displayView = "pieChart";
        // clear the data in the pie charts previously
        incomePieChartData.clear();
        expensePieChartData.clear();
        // display the pie chart according to the time range
        if(timeRange.compareTo("day") == 0){
            String dateType = "Daily";
            DailyPieChart dailyPieChart = new DailyPieChart(transactions);
            showPieChart(dateType, dailyPieChart);
        }else if(timeRange.compareTo("week") == 0){
            String dateType = "Weekly";
            WeeklyPieChart weeklyPieChart = new WeeklyPieChart(transactions);
            showPieChart(dateType, weeklyPieChart);
        }else if(timeRange.compareTo("month") == 0){
            String dateType = "Monthly";
            MonthlyPieChart monthlyPieChart = new MonthlyPieChart(transactions);
            showPieChart(dateType, monthlyPieChart);
        }else{
            String dateType = "Annual";
            AnnualPieChart annualPieChart = new AnnualPieChart(transactions);
            showPieChart(dateType, annualPieChart);
        }
        // display the hContainer
        container.getChildren().add(1,hContainer);
    }

    /**
     * This helper function helps rendering the pieChart with corresponding date range.
     * @param dateType the indicated time range
     * @param pieChart the pie chart object
     */
    private void showPieChart(String dateType, MyPieChart pieChart) {
        expensePieChartData.clear();
        incomePieChartData.clear();
        expenseChart.setTitle(dateType + " Expense");
        incomeChart.setTitle(dateType + " Income");

        List<PieChartComponent> expenseComponents = pieChart.getExpenseComponents();

        for (PieChartComponent component : expenseComponents) {
            expensePieChartData.add(new PieChart.Data(component.getCategory(), component.getValue()));
        }

        List<PieChartComponent> incomeComponents = pieChart.getIncomeComponents();
        for (PieChartComponent component : incomeComponents) {
            incomePieChartData.add(new PieChart.Data(component.getCategory(), component.getValue()));
        }
    }

    /**
     * display the line chart view
     */
    private void lineChartView(){
        // remove the object that is displayed currently
        if(displayView.compareTo("listView")==0){
            container.getChildren().remove(table);
        }else if(displayView.compareTo("pieChart")==0){
            container.getChildren().remove(hContainer);
        }
        // set the display view to line Chart
        displayView = "lineChart";
        // display the line chart according to the time range
        if(timeRange.compareTo("week") == 0 || timeRange.compareTo("day") == 0){
            showLineChart("day");
        }else if(timeRange.compareTo("month") == 0){
            showLineChart("month");
        }else if(timeRange.compareTo("year") == 0){
            showLineChart("year");
        }
        container.getChildren().add(1,lineChart);
    }

    /**
     * This helper function helps rendering the lineChart with corresponding date range.
     * @param timeRange the indicated time range
     */
    private void showLineChart(String timeRange){
        // remove the line chart if there is already a line chart displaying
        if(container.getChildren().contains(lineChart)){
            container.getChildren().remove(lineChart);
        }
        //defining the x axes according to the time range
        NumberAxis xAxis;
        if(timeRange.compareTo("week") == 0 || timeRange.compareTo("day") == 0){
            xAxis = new NumberAxis(1,7,1);
            xAxis.setLabel("Number of Day");
        }else if(timeRange.compareTo("month") == 0){
            Calendar calendar = Calendar.getInstance();
            xAxis = new NumberAxis(1,calendar.getActualMaximum(Calendar.DAY_OF_MONTH),1);
            xAxis.setLabel("Number of Day");
        }else{
            xAxis = new NumberAxis(1,12,1);
            xAxis.setLabel("Number of Month");
        }
        // define the yAxis
        NumberAxis yAxis = new NumberAxis();
        // define the line chart
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        // define 2 series, income and expense
        XYChart.Series income = new XYChart.Series();
        income.setName("Income");
        XYChart.Series expense = new XYChart.Series();
        expense.setName("Expense");
        // add the two series to the line chart
        lineChart.getData().add(expense);
        lineChart.getData().add(income);
        // set line chart's name and data according to the time range
        myLineChart temp;
        if(timeRange.compareTo("week") == 0 || timeRange.compareTo("day") == 0){
            lineChart.setTitle("Expense and Income in this week");
            temp = new WeeklyLineChart(transactions);
        }else if(timeRange.compareTo("month") == 0){
            lineChart.setTitle("Expense and Income in this month");
            temp = new MonthlyLineChart(transactions);
        }else{
            lineChart.setTitle("Expense and Income in this year");
            temp = new AnnualLineChart(transactions);
        }
        // add the data to expense and income
        for (int i =0; i < temp.getExpenseTransactions().length; i ++){
            expense.getData().add(new XYChart.Data(i+1, temp.getExpenseTransactions()[i]));
        }
        for (int i =0; i < temp.getIncomeTransactions().length; i ++){
            income.getData().add(new XYChart.Data(i+1, temp.getIncomeTransactions()[i]));
        }

    }


    /**
     * display the view object, on click the day button
     * @param event
     */
    @FXML
    public void onClickDayButton(ActionEvent event) {
        timeRange = "day";
        if(displayView.compareTo("listView") == 0){
            DailyListView temp = new DailyListView(transactions);
            List<Transaction> tempData = temp.getDailyTransactions();
            data.clear();
            data.addAll(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            lineChartView();
        }else{
            String dateType = "Daily";
            DailyPieChart dailyPieChart = new DailyPieChart(transactions);
            showPieChart(dateType, dailyPieChart);
        }
    }

    /**
     * display the view object, on click the week button
     * @param event
     */
    @FXML
    public void onClickWeekButton(ActionEvent event) {
        timeRange = "week";
        if(displayView.compareTo("listView") == 0){
            WeeklyListView temp = new WeeklyListView(transactions);
            List<Transaction> tempData = temp.getWeeklyTransactions();
            data.clear();
            data.addAll(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            lineChartView();
        }else{
            String dateType = "Weekly";
            WeeklyPieChart weeklyPieChart = new WeeklyPieChart(transactions);
            showPieChart(dateType, weeklyPieChart);
        }
    }

    /**
     * display the view object, on click the month button
     * @param event
     */
    @FXML
    public void onClickMonthButton(ActionEvent event) {
        timeRange = "month";
        if(displayView.compareTo("listView") == 0){
            MonthlyListView temp = new MonthlyListView(transactions);
            List<Transaction> tempData = temp.getMonthlyTransactions();
            data.clear();
            data.addAll(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            lineChartView();
        }else{
            String dateType = "Monthly";
            MonthlyPieChart monthlyPieChart = new MonthlyPieChart(transactions);
            showPieChart(dateType, monthlyPieChart);
        }
    }

    /**
     * display the view object, on click the year button
     * @param event
     */
    @FXML
    public void onClickYearButton(ActionEvent event) {
        timeRange = "year";
        if(displayView.compareTo("listView") == 0){
            AnnualListView temp = new AnnualListView(transactions);
            List<Transaction> tempData = temp.getAnnualTransactions();
            data.clear();
            data.addAll(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            lineChartView();
        }else{
            String dateType = "Annual";
            AnnualPieChart annualPieChart = new AnnualPieChart(transactions);
            showPieChart(dateType, annualPieChart);
        }
    }

    /**
     * change the display view, on click the combo
     * @param event
     */
    @FXML
    public void onClickCombo(ActionEvent event){
        String value = changeView.getValue();
        if(value.compareTo("List View") == 0){
            listView();
        }else if(value.compareTo("Pie Chart")==0){
            pieChartView();
        }else{
            lineChartView();
        }
    }

    public void onClickCreateNewRecord(ActionEvent event) {
        //redirect to create page
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main main = new Main();
        stageTheEventSourceNodeBelongs.setScene(main.createExpenseScene());
    }


}
