package edu.carleton.expensetracker.controllers;

import edu.carleton.expensetracker.Main;
import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.lineCharts.AnnualLineChart;
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
import java.util.List;

/**
 * Created by taoliu on 5/23/16.
 * ViewExpenseController.java
 * This file serves to control the events on view expense scene.
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
    @FXML
    private VBox container;
    private LineChart<Number,Number> lineChart;
    private TableColumn type;
    private TableColumn note;
    private TableColumn value;
    private TableColumn category;
    private TableColumn date;
    private final ObservableList<Transaction> data = FXCollections.observableArrayList();
    private String timeRange = "day";
    private String displayView = "listView";
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private boolean listViewInitialized = false;
    private boolean lineChartInitialized = false;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series expense;
    private XYChart.Series income;
    private ObservableList<PieChart.Data> expensePieChartData = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> incomePieChartData = FXCollections.observableArrayList();
    private PieChart expenseChart;
    private PieChart incomeChart;
    public HBox hContainer;

    public void initialize(){
        Record wrapper = new Record();
        transactions = wrapper.deserializeRecord();
        day.setSelected(true);
        listViewInit();
        listView();
    }

    private void listViewInit(){
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
                new PropertyValueFactory<Transaction, String>("displayDate"));
        category = new TableColumn("category");
        category.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("category"));

    }

    public void listView(){
        if(displayView.compareTo("lineChart") == 0){
            container.getChildren().remove(lineChart);
            container.getChildren().add(1,table);
        }else if(displayView.compareTo("pieChart") == 0){
            container.getChildren().remove(hContainer);
            container.getChildren().add(1,table);
        }
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
        if(!listViewInitialized) {
            table.getColumns().addAll(type, value, category, date, note);
            listViewInitialized = true;
        }
    }

    private void pieChartView(){
        System.out.println("pie view init");
        if(displayView.compareTo("listView") == 0) {
            container.getChildren().remove(table);
        }else if(displayView.compareTo("lineChart") == 0){
            container.getChildren().remove(lineChart);
        }
        displayView = "pieChart";
        expenseChart = new PieChart(expensePieChartData);
        incomeChart = new PieChart(incomePieChartData);
        incomePieChartData.clear();
        expensePieChartData.clear();

        if(timeRange.compareTo("day") == 0){
            String datetype = "Daily";
            DailyPieChart dailyPieChart = new DailyPieChart(transactions);
            showPieChart(datetype, dailyPieChart);
        }else if(timeRange.compareTo("week") == 0){
            String datetype = "Weekly";
            WeeklyPieChart weeklyPieChart = new WeeklyPieChart(transactions);
            showPieChart(datetype, weeklyPieChart);
        }else if(timeRange.compareTo("month") == 0){
            String datetype = "Monthly";
            MonthlyPieChart monthlyPieChart = new MonthlyPieChart(transactions);
            showPieChart(datetype, monthlyPieChart);
        }else{
            String datetype = "Annual";
            AnnualPieChart annualPieChart = new AnnualPieChart(transactions);
            showPieChart(datetype, annualPieChart);
        }
        hContainer = new HBox();
        hContainer.getChildren().addAll(expenseChart,incomeChart);
        container.getChildren().add(1,hContainer);
    }

    /**
     * This helper function helps rendering the pieChart with corresponding date range.
     * @param datetype
     * @param pieChart
     */
    private void showPieChart(String datetype, edu.carleton.expensetracker.model.pieCharts.PieChart pieChart) {
        expenseChart.setTitle(datetype + " Expense");
        incomeChart.setTitle(datetype + " Income");

        List<PieChartComponent> expenseComponents = pieChart.getExpenseComponents();

        for (PieChartComponent component : expenseComponents) {
            expensePieChartData.add(new PieChart.Data(component.getCategory(), component.getValue()));
        }

        List<PieChartComponent> incomeComponents = pieChart.getIncomeComponents();
        for (PieChartComponent component : incomeComponents) {
            incomePieChartData.add(new PieChart.Data(component.getCategory(), component.getValue()));
        }
    }

    private void lineChartView(){
        if(displayView.compareTo("listView")==0){
            container.getChildren().remove(table);
        }else{
            container.getChildren().remove(hContainer);
        }
        displayView = "lineChart";
        if(!lineChartInitialized) {
            //defining the axes
            xAxis = new NumberAxis();
            yAxis = new NumberAxis();
            //creating the chart
            lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            //defining a series
            income = new XYChart.Series();
            income.setName("Income");
            expense = new XYChart.Series();
            expense.setName("Expense");
            //populating the series with data
            lineChart.getData().add(expense);
            lineChart.getData().add(income);
            lineChartInitialized = true;
        }
        container.getChildren().add(1,lineChart);

        if(timeRange.compareTo("week") == 0 || timeRange.compareTo("day") == 0){
            showDailyLineChart();
        }else if(timeRange.compareTo("month") == 0){
            showMonthlyLineChart();
        }else if(timeRange.compareTo("year") == 0){
            showAnnualyLineChart();
        }
    }

    private void showDailyLineChart(){
        income.getData().clear();
        expense.getData().clear();
        xAxis.setLabel("Number of Day");
        lineChart.setTitle("Expense and Income in this week");
        WeeklyLineChart temp = new WeeklyLineChart(transactions);
        for (int i =0; i < temp.getExpenseTransactions().length; i ++){
            expense.getData().add(new XYChart.Data(i+1, temp.getExpenseTransactions()[i]));
        }
        for (int i =0; i < temp.getIncomeTransactions().length; i ++){
            income.getData().add(new XYChart.Data(i+1, temp.getIncomeTransactions()[i]));
        }
    }
    private void showMonthlyLineChart(){
        income.getData().clear();
        expense.getData().clear();
        xAxis.setLabel("Number of Day");
        lineChart.setTitle("Expense and Income in this month");
        MonthlyLineChart temp = new MonthlyLineChart(transactions);
        for (int i =0; i < temp.getExpenseTransactions().length; i ++){
            expense.getData().add(new XYChart.Data(i+1, temp.getExpenseTransactions()[i]));
        }
        for (int i =0; i < temp.getIncomeTransactions().length; i ++){
            income.getData().add(new XYChart.Data(i+1, temp.getIncomeTransactions()[i]));
        }
    }
    private void showAnnualyLineChart(){
        income.getData().clear();
        expense.getData().clear();
        xAxis.setLabel("Number of Month");
        lineChart.setTitle("Expense and Income in this year");
        AnnualLineChart temp = new AnnualLineChart(transactions);
        for (int i =0; i < temp.getExpenseTransactions().length; i ++){
            expense.getData().add(new XYChart.Data(i+1, temp.getExpenseTransactions()[i]));
        }
        for (int i =0; i < temp.getIncomeTransactions().length; i ++){
            income.getData().add(new XYChart.Data(i+1, temp.getIncomeTransactions()[i]));
        }
    }
    @FXML
    public void onClickDayButton(ActionEvent event) {
        timeRange = "day";
        System.out.println("day button clicked");
        if(displayView.compareTo("listView") == 0){
            DailyListView temp = new DailyListView(transactions);
            List<Transaction> tempData = temp.getDailyTransactions();
            addToData(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            income.getData().clear();
            expense.getData().clear();
            showDailyLineChart();
        }else{
            expensePieChartData.clear();
            incomePieChartData.clear();
            String datetype = "Daily";
            DailyPieChart dailyPieChart = new DailyPieChart(transactions);
            showPieChart(datetype, dailyPieChart);
        }
    }

    @FXML
    public void onClickWeekButton(ActionEvent event) {
        timeRange = "week";
        System.out.println("week button clicked");
        if(displayView.compareTo("listView") == 0){
            WeeklyListView temp = new WeeklyListView(transactions);
            List<Transaction> tempData = temp.getWeeklyTransactions();
            addToData(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            showDailyLineChart();
        }else{
            expensePieChartData.clear();
            incomePieChartData.clear();
            String datetype = "Weekly";
            WeeklyPieChart weeklyPieChart = new WeeklyPieChart(transactions);
            showPieChart(datetype, weeklyPieChart);
        }
    }

    @FXML
    public void onClickMonthButton(ActionEvent event) {
        timeRange = "month";
        System.out.println("month button clicked");
        if(displayView.compareTo("listView") == 0){
            MonthlyListView temp = new MonthlyListView(transactions);
            List<Transaction> tempData = temp.getMonthlyTransactions();
            addToData(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            showMonthlyLineChart();
        }else{
            expensePieChartData.clear();
            incomePieChartData.clear();
            String datetype = "Monthly";
            MonthlyPieChart monthlyPieChart = new MonthlyPieChart(transactions);
            showPieChart(datetype, monthlyPieChart);
        }
    }

    @FXML
    public void onClickYearButton(ActionEvent event) {
        timeRange = "year";
        System.out.println("year button clicked");
        if(displayView.compareTo("listView") == 0){
            AnnualListView temp = new AnnualListView(transactions);
            List<Transaction> tempData = temp.getAnnualTransactions();
            addToData(tempData);
        }else if(displayView.compareTo("lineChart") == 0){
            showAnnualyLineChart();
        }else{
            expensePieChartData.clear();
            incomePieChartData.clear();
            String datetype = "Annual";
            AnnualPieChart annualPieChart = new AnnualPieChart(transactions);
            showPieChart(datetype, annualPieChart);
        }
    }

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

    private void addToData(List<Transaction> transactions){
        data.clear();
        data.addAll(transactions);
    }
}
