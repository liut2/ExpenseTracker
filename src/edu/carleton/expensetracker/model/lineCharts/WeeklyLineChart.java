package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class handles the display of Weekly Line Chart.
 */
public class WeeklyLineChart extends myLineChart {
    public WeeklyLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        getDailyTransaction();
    }

    /**
     *  Get a list of integers each represents the total expense or income within a day
     *  @return tempList
     */
    public void getDailyTransaction() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        int[] expenseList = new int[7];
        int[] incomeList = new int[7];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;

        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tran.getDate().getDay();
                if(tran.getType().compareTo("expense") == 0) {
                    expenseList[offset] += tran.getValue();
                }else{
                    incomeList[offset] += tran.getValue();
                }
            }
        }
        expenseTransactionPerUnit = expenseList;
        incomeTransactionPerUnit = incomeList;

    }

}
