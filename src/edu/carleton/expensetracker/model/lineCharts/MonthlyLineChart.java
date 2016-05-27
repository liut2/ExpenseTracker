package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 * This class handles the display of Monthly Line Chart.
 */

public class MonthlyLineChart extends LineChart{
    public MonthlyLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        this.expenseTransactionPerUnit = getWeeklyTransaction().get(0);
        this.incomeTransactionPerUnit = getWeeklyTransaction().get(1);
    }

/**
 *    takes a list of transactions
 *    return a list of integers each represents the total expense or income within a day
 */
    public List<int[]> getWeeklyTransaction() {
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
                if(tran.getType() == TransactionType.EXPENSE) {
                    expenseList[offset-1] += tran.getValue();
                }else{
                    incomeList[offset-1] += tran.getValue();
                }
            }
        }
        tempList.add(expenseList);
        tempList.add(incomeList);
        return tempList;
    }

}
