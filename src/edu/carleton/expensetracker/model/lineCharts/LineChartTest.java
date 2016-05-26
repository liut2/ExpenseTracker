package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 */
public class LineChartTest {
    public static Record test(){
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;
        try {
            date1 = newDateFormat.parse("05/12/2016");
            date2 = newDateFormat.parse("05/01/2016");
            date3 = newDateFormat.parse("05/11/2016");
            date4 = newDateFormat.parse("05/24/2016");
            date5 = new Date();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction tran1 = new Transaction(TransactionType.EXPENSE);
        tran1.setDate(date1);

        Transaction tran2 = new Transaction(TransactionType.EXPENSE);
        tran2.setDate(date2);

        Transaction tran3 = new Transaction(TransactionType.EXPENSE);
        tran3.setDate(date3);

        Transaction tran4 = new Transaction(TransactionType.EXPENSE);
        tran4.setDate(date4);

        Transaction tran5 = new Transaction(TransactionType.EXPENSE);
        tran5.setDate(date5);

        transactions.add(tran1);
        transactions.add(tran2);
        transactions.add(tran3);
        transactions.add(tran4);
        transactions.add(tran5);
        Record record = new Record(transactions);
        return record;
    }
}
