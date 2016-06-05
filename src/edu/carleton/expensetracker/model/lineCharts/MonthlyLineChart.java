package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class handles the display of Monthly Line Chart.
 */

public class MonthlyLineChart extends myLineChart {
    public MonthlyLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        getWeeklyTransaction();
    }

    /**
     *  Get a list of integers each represents the total expense or income within a month
     *  @return tempList
     */
    public void getWeeklyTransaction() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = cal.getTime().getDate();

        int[] expenseList = new int[cal.getActualMaximum(Calendar.DAY_OF_MONTH)];
        int[] incomeList = new int[cal.getActualMaximum(Calendar.DAY_OF_MONTH)];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;
        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tempDate.getDate();
                if(tran.getType().compareTo("expense") == 0) {
                    expenseList[offset-1] += tran.getValue();
                }else{
                    incomeList[offset-1] += tran.getValue();
                }
            }
        }
        expenseTransactionPerUnit = expenseList;
        incomeTransactionPerUnit = incomeList;
    }

}
