package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taoliu on 5/24/16.
 */
public class WeeklyPieChart extends PieChart {
    public WeeklyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getWeeklyTransactions();
    }
    /*
    filter the list only to get transactions happened this week
     */
    public List<Transaction> getWeeklyTransactions() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        //System.out.println("Start of this week:       " + cal.getTime());
        //System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

        List<Transaction> tempList = new ArrayList<Transaction>();
        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if (cal.getTime().before(tempDate) || cal.getTime().equals(tempDate)) {
                tempList.add(tran);
            }
        }
        return tempList;
    }

    public static void main(String[] args) {
        WeeklyPieChart week = new WeeklyPieChart(Test.test());
        System.out.println(week.getExpenseTransactions().size());
    }
}
