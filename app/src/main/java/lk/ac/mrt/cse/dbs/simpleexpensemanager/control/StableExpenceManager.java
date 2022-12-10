package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.StableMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.StableTransactionAccountDAO;

public class StableExpenceManager extends ExpenseManager {
    @Override
    public void setup() throws ExpenseManagerException {
        TransactionDAO stableTransactionDAO = new StableTransactionAccountDAO();
        setTransactionsDAO(stableTransactionDAO);

        AccountDAO stableAccountDAO = new StableMemoryAccountDAO();
        setAccountsDAO(stableAccountDAO);
    }
}
