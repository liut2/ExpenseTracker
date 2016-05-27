package edu.carleton.expensetracker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoliu on 5/26/16.
 */
public class Test{
    public static List<Transaction> test() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;
        try {
            date1 = newDateFormat.parse("05/12/2016");
            date2 = newDateFormat.parse("05/22/2016");
            date3 = newDateFormat.parse("05/21/2016");
            date4 = newDateFormat.parse("04/12/2016");
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
        return transactions;
    }
}
