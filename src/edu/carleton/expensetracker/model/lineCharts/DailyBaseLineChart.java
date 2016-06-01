package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Transaction;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class handles the display of Daily Line Chart.
 */
public class DailyBaseLineChart extends BaseLineChart {

    public DailyBaseLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        getHourlyTransaction();
    }

    /**
     *  Get a list of integers each represents the total expense or income within an hour
     *  @return tempList
     */
    public void getHourlyTransaction() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        int[] expenseList = new int[24];
        int[] incomeList = new int[24];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;
        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tempDate.getHours();
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
