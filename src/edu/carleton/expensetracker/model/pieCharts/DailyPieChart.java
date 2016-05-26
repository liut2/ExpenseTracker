package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static sun.misc.Version.print;
import static sun.misc.Version.println;

/**
 * Created by taoliu on 5/24/16.
 */
public class DailyPieChart extends PieChart {
    public DailyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getDailyTransactions();
    }

    public List<Transaction> getDailyTransactions() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

//        System.out.println("Start of the day:       " + cal.getTime());
//        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

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
        DailyPieChart day = new DailyPieChart(Test.test());
        System.out.println(day.getExpenseTransactions().size());
    }
}
