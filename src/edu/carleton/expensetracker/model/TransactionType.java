package edu.carleton.expensetracker.model;

/**
* Created by chenx2 on 5/24/2016.
* This enum defines two type of transactions and their display names
*/
public enum TransactionType {
    INCOME("income"),
    EXPENSE("expense");

    private String mDisplayName;

    TransactionType(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
