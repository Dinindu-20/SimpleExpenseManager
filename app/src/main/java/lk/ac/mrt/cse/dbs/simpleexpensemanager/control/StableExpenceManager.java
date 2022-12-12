package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.StableMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.StableTransactionAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.DatabaseHelper;

public class StableExpenceManager extends ExpenseManager {
    private DatabaseHelper databaseHelper;

    public StableExpenceManager(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        setup();
    }


    @Override
    public void setup() {
        TransactionDAO stableTransactionDAO = new StableTransactionAccountDAO(databaseHelper);
        setTransactionsDAO(stableTransactionDAO);

        AccountDAO stableAccountDAO = new StableMemoryAccountDAO(databaseHelper);
        setAccountsDAO(stableAccountDAO);
    }
}
