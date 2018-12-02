package com.example.jure_lokovsek.personalbudget.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String TAG = DatabaseManager.class.getSimpleName();

    private static DaoSession mDaoSession;
    private static DaoMaster.DevOpenHelper daoMasterDevOpenHelper;
    private static DaoMaster daoMaster;
    private static SQLiteDatabase sqLiteDatabase;
    private Context context;
    private static final boolean ENCRYPTED = false;

    public DatabaseManager(Context context) {
        this.context = context;
        setupDatabase();
    }

    private void setupDatabase(){
        daoMasterDevOpenHelper = new DaoMaster.DevOpenHelper(context, ENCRYPTED ? "app-db-encrypted" : "app-db");
        sqLiteDatabase = daoMasterDevOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(sqLiteDatabase);
        mDaoSession = daoMaster.newSession();
    }


    public void storeBudget(Double newValue, BudgetType budgetType, String comment) {
        long startOfTheDay = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay().getMillis();
        Budget budget = getFindBudget(startOfTheDay, budgetType);
        budget.setValue(newValue);
        budget.setComment(comment);
        mDaoSession.update(budget);
    }

    private void updateBudget(Budget budget) {
        mDaoSession.update(budget);
    }

    private Budget getFindBudget(Long timeStamp, BudgetType budgetType) {
        Budget budgetInDb = mDaoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.BudgetType.eq(budgetType), BudgetDao.Properties.TimeStamp.eq(timeStamp)).unique();
        if(budgetInDb == null){
            Log.d(TAG, "Budget not found...creating budget");
            budgetInDb = new Budget(timeStamp, budgetType);
            mDaoSession.getBudgetDao().insert(budgetInDb);
        }else {
            Log.d(TAG, "Budget Exists");
        }
        Log.d(TAG, "Budget stored");
        return budgetInDb;
    }

    public int getSize(){
        return mDaoSession.getBudgetDao().loadAll().size();
    }

    public List<Budget> getBudgetListWithinTimeFrame(Long from, Long to, BudgetType budgetType){
        return mDaoSession.getBudgetDao().queryBuilder()
                .where(BudgetDao.Properties.BudgetType.eq(budgetType),BudgetDao.Properties.TimeStamp.le(to),BudgetDao.Properties.TimeStamp.ge(from)).orderAsc(BudgetDao.Properties.TimeStamp).list();
    }

    public double getAverageBudgetValue(BudgetType budgetType) {
        double sum = 0.0;
        List<Budget> budgetList = null;
        if (budgetType.equals(BudgetType.UNKNOWN)) {
            budgetList = mDaoSession.getBudgetDao().loadAll();
        } else {
            budgetList = mDaoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.BudgetType.eq(budgetType.ordinal())).list();
        }
        if (budgetList != null && budgetList.size() > 0) {
            for (Budget budget : budgetList) {
                sum += budget.getValue();
            }
            return sum / budgetList.size();
        } else {
            return sum;
        }
    }

    public List<Budget> getTodayBudgetList(){
        long startOfTheDay = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay().getMillis();
        long endOfTheDay = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay().plusHours(24).minusMinutes(1).getMillis();
        return mDaoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.TimeStamp.between(startOfTheDay, endOfTheDay)).list();
    }

    public void izpis(List<Budget> list){
        for (int i=0;i<list.size();i++){
            if(list.get(i).getValue() != null){
                Log.d(TAG, "Vrednost " + list.get(i).getValue() +" Index " + i + " Type " + list.get(i).getBudgetType() + " Komentar " + list.get(i).getComment());
            }else {
                Log.d(TAG, "Nima Vrednosti " + " Index " + i);
            }

        }
    }

    public List<BudgetType> getBudgetTypeList(){
        final List<BudgetType> budgetTypes = new ArrayList<>();
        budgetTypes.add(BudgetType.UNKNOWN);
        budgetTypes.add(BudgetType.FOOD);
        budgetTypes.add(BudgetType.DRINKS);
        budgetTypes.add(BudgetType.DIESEL);
        budgetTypes.add(BudgetType.RENT);
        budgetTypes.add(BudgetType.CAR);
        return budgetTypes;
    }


}
