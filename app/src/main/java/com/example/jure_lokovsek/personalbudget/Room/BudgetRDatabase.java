package com.example.jure_lokovsek.personalbudget.Room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.jure_lokovsek.personalbudget.Database.BudgetType;

import org.joda.time.DateTime;

import java.util.concurrent.ThreadLocalRandom;

@Database(entities = BudgetR.class, version = 1)
public abstract class BudgetRDatabase extends RoomDatabase {

    private static BudgetRDatabase instance;
    public abstract BudgetRDao budgetRDao();

    public static synchronized BudgetRDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, BudgetRDatabase.class, "budget_database").fallbackToDestructiveMigration().addCallback(roomCallback).build(); // we need this, when upgrading db version number
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedDb(instance).execute();
        }
    };

    private static class PopulatedDb extends AsyncTask<Void, Void, Void> {
        private BudgetRDao budgetRDao;

        public PopulatedDb(BudgetRDatabase database) {
            this.budgetRDao = database.budgetRDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            budgetRDao.insert(new BudgetR(new DateTime().getMillis(), ThreadLocalRandom.current().nextDouble(), "1 insertt db", BudgetType.DRINKS));
            budgetRDao.insert(new BudgetR(new DateTime().getMillis(), ThreadLocalRandom.current().nextDouble(), "2 insertt db", BudgetType.CAR));
            budgetRDao.insert(new BudgetR(new DateTime().getMillis(), ThreadLocalRandom.current().nextDouble(), "3 insertt db", BudgetType.FOOD));
            budgetRDao.insert(new BudgetR(new DateTime().getMillis(), ThreadLocalRandom.current().nextDouble(), "4 insertt db", BudgetType.RENT));
            return null;
        }
    }



}
