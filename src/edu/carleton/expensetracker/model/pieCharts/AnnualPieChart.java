package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taoliu on 5/24/16.
 */
public class AnnualPieChart extends PieChart {
    public AnnualPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getAnnualTransactions();
    }

    private List<Transaction> getAnnualTransactions() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.DAY_OF_YEAR, 1);

        //System.out.println("Start of the year:       " + cal.getTime());
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
        AnnualPieChart year = new AnnualPieChart(Test.test());
        System.out.println(year.getExpenseTransactions().size());
    }
}
