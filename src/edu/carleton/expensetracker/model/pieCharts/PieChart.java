package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Test;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by taoliu on 5/24/16.
 */
public class PieChart {
    protected List<Transaction> transactions;
    //private String[] expenseCategories;
    //private String[] incomeCategories;

    public PieChart(List<Transaction> transactions){
        this.transactions = transactions;
        //expenseCategories = new String[]{"Clothes", "Food", "Entertainment", "Gas", "Gifts", "Holidays", "Kids", "Shopping", "Sports", "Transportation"};
        //incomeCategories = new String[]{"Salary", "Bonus", "Stock", "Lottery"};
    }

    public List<Transaction> getExpenseTransactions(){
        List<Transaction> expenseTransactions = new ArrayList<Transaction>();
        for (Transaction tran : this.transactions){
            if (tran.getType() == TransactionType.EXPENSE){
                expenseTransactions.add(tran);
            }
        }
        return expenseTransactions;
    }

    public List<Transaction> getIncomeTransactions(){
        List<Transaction> incomeTransactions = new ArrayList<Transaction>();
        for (Transaction tran : this.transactions){
            if (tran.getType() == TransactionType.INCOME){
                incomeTransactions.add(tran);
            }
        }
        return incomeTransactions;
    }

    /*
    * The piechart view will call on this API method to construct the expense view
     */
    public List<PieChartComponent> getExpenseComponents(){
        List<PieChartComponent> pieChartComponents = new ArrayList<PieChartComponent>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        //first pass
        for (Transaction tran : getExpenseTransactions()){
            String category = tran.getCategory();
            if (map.containsKey(category)) {
                int value = map.get(category);
                value += tran.getValue();
                map.put(category, value);
            } else {
                map.put(category, tran.getValue());
            }
        }
        //second pass
        for (String key: map.keySet()) {
            pieChartComponents.add(new PieChartComponent(key, map.get(key)));
        }
        return pieChartComponents;
    }

    /*
    * The piechart view will call on this API method to construct the income view
     */
    public List<PieChartComponent> getIncomeComponents(){
        List<PieChartComponent> pieChartComponents = new ArrayList<PieChartComponent>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        //first pass
        for (Transaction tran : getIncomeTransactions()){
            String category = tran.getCategory();
            if (map.containsKey(category)) {
                int value = map.get(category);
                value += tran.getValue();
                map.put(category, value);
            } else {
                map.put(category, tran.getValue());
            }
        }
        //second pass
        for (String key: map.keySet()) {
            pieChartComponents.add(new PieChartComponent(key, map.get(key)));
        }
        return pieChartComponents;
    }

    public static void main(String[] args) {
        PieChart newPiechart = new PieChart(Test.test());
        System.out.println(newPiechart.getExpenseTransactions().size());
    }
}
