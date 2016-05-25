package edu.carleton.expensetracker.model.pieCharts;

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

class Test{
    public static List<Transaction>  test() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;
        try {
            date1 = newDateFormat.parse("05/12/2016");
            date2 = newDateFormat.parse("05/22/2016");
            date3 = newDateFormat.parse("05/21/2016");
            date4 = newDateFormat.parse("04/12/2016");
            date5 = new Date();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction tran1 = new Transaction(TransactionType.EXPENSE);
        tran1.setDate(date1);

        Transaction tran2 = new Transaction(TransactionType.EXPENSE);
        tran2.setDate(date2);

        Transaction tran3 = new Transaction(TransactionType.EXPENSE);
        tran3.setDate(date3);

        Transaction tran4 = new Transaction(TransactionType.EXPENSE);
        tran4.setDate(date4);

        Transaction tran5 = new Transaction(TransactionType.EXPENSE);
        tran5.setDate(date5);

        transactions.add(tran1);
        transactions.add(tran2);
        transactions.add(tran3);
        transactions.add(tran4);
        transactions.add(tran5);
        return transactions;
    }
}
