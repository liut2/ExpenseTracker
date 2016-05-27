package edu.carleton.expensetracker.model.listView;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the display of listView
 */
public class ListView {
    protected List<Transaction> transactions;

    public ListView(List<Transaction> transactions){
        this.transactions = transactions;

    }

    /*
     * This function filter the list only to get expense transactions
     * @return expenseTransactions
     */
    public List<Transaction> getExpenseTransactions(){
        List<Transaction> expenseTransactions = new ArrayList<Transaction>();
        for (Transaction tran : this.transactions){
            if (tran.getType() == TransactionType.EXPENSE){
                expenseTransactions.add(tran);
            }
        }
        return expenseTransactions;
    }

    /*
     * This function filter the list only to get income transactions
     * @return incomeTransactions
     */
    public List<Transaction> getIncomeTransactions(){
        List<Transaction> incomeTransactions = new ArrayList<Transaction>();
        for (Transaction tran : this.transactions){
            if (tran.getType() == TransactionType.INCOME){
                incomeTransactions.add(tran);
            }
        }
        return incomeTransactions;
    }
}
