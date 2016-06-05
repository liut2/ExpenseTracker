package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;

import java.util.List;

/**
 * This class handles the display of Line Chart.
 */
public class myLineChart {
    int[] expenseTransactionPerUnit;
    int[] incomeTransactionPerUnit;
    List<Transaction> transactions;

    public int[] getExpenseTransactions(){
        return expenseTransactionPerUnit;
    }

    public int[] getIncomeTransactions(){
        return incomeTransactionPerUnit;
    }
}
