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
 * This class handles the display of Weekly Line Chart.
 */
public class WeeklyLineChart extends LineChart {
    public WeeklyLineChart(List<Transaction> transactions) {
        this.transactions = transactions;
        this.expenseTransactionPerUnit = getDailyTransaction().get(0);
        this.incomeTransactionPerUnit = getDailyTransaction().get(1);
    }

/**
 *    takes a list of transactions
 *    return a list of integers each represents the total expense or income within a day
 */
    public List<int[]> getDailyTransaction() {
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
                if(tran.getType() == TransactionType.EXPENSE) {
                    expenseList[offset] += tran.getValue();
                }else{
                    incomeList[offset] += tran.getValue();
                }
            }
        }
        tempList.add(expenseList);
        tempList.add(incomeList);
        return tempList;
    }
    public static void main(String[] args) {
        WeeklyLineChart day = new WeeklyLineChart(LineChartTest.test());
        System.out.println(day.getDailyTransaction().size());
    }
}
