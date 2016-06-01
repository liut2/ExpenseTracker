package edu.carleton.expensetracker.model;

/**
 * Created by chenx2 on 6/1/2016.
 */
public class ExpenseTransaction extends Transaction{
    private String type = "expense";
    public ExpenseTransaction(){
        super("expense");
    }

}
