package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class handles the display of Annual Line Chart.
 */
public class AnnualBaseLineChart extends BaseLineChart {
    public AnnualBaseLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        getMonthlyTransaction();
    }

    /**
     *  Get a list of integers each represents the total expense or income within a month
     *  @return tempList
     */
    public void getMonthlyTransaction() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.DAY_OF_YEAR, 1);

        int[] expenseList = new int[12];
        int[] incomeList = new int[12];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;
        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tempDate.getMonth();
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
