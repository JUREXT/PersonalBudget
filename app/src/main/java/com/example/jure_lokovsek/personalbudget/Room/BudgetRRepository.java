package com.example.jure_lokovsek.personalbudget.Room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BudgetRRepository {

    private BudgetRDao budgetRDao;
    private LiveData<List<BudgetR>> allBudgets;

    public BudgetRRepository(Application application){
        BudgetRDatabase database = BudgetRDatabase.getInstance(application);
        budgetRDao = database.budgetRDao();
        allBudgets = budgetRDao.getBudgets();
    }

    public void insert(BudgetR budgetR){
        new InsertBugderRAsynTask(budgetRDao).execute(budgetR);
    }

    public void update(BudgetR budgetR){
        new UpdateBugderRAsynTask(budgetRDao).execute(budgetR);
    }

    public void delete(BudgetR budgetR){
        new DeleteBugderRAsynTask(budgetRDao).execute(budgetR);
    }

    public void deleteAll(){
        new InsertBugderRAsynTask(budgetRDao).execute();
    }

    public LiveData<List<BudgetR>> getAllBudgets() {
        return allBudgets;
    }

    private static class InsertBugderRAsynTask extends AsyncTask<BudgetR, Void, Void>{
        private BudgetRDao budgetRDao;

        public InsertBugderRAsynTask(BudgetRDao budgetRDao) {
            this.budgetRDao = budgetRDao;
        }

        @Override
        protected Void doInBackground(BudgetR... budgetRS) {
            budgetRDao.insert(budgetRS[0]);
            return null;
        }
    }

    private static class UpdateBugderRAsynTask extends AsyncTask<BudgetR, Void, Void>{
        private BudgetRDao budgetRDao;

        public UpdateBugderRAsynTask(BudgetRDao budgetRDao) {
            this.budgetRDao = budgetRDao;
        }

        @Override
        protected Void doInBackground(BudgetR... budgetRS) {
            budgetRDao.update(budgetRS[0]);
            return null;
        }
    }

    private static class DeleteBugderRAsynTask extends AsyncTask<BudgetR, Void, Void>{
        private BudgetRDao budgetRDao;

        public DeleteBugderRAsynTask(BudgetRDao budgetRDao) {
            this.budgetRDao = budgetRDao;
        }

        @Override
        protected Void doInBackground(BudgetR... budgetRS) {
            budgetRDao.delete(budgetRS[0]);
            return null;
        }
    }

    private static class DeleteAllBugderRAsynTask extends AsyncTask<Void, Void, Void>{
        private BudgetRDao budgetRDao;

        public DeleteAllBugderRAsynTask(BudgetRDao budgetRDao) {
            this.budgetRDao = budgetRDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            budgetRDao.deleteAllBudgetsR();
            return null;
        }
    }

}
