package com.example.jure_lokovsek.personalbudget.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BudgetRDao {

    @Insert
    void insert(BudgetR budgetR);

    @Update
    void update(BudgetR budgetR);

    @Delete
    void delete(BudgetR budgetR);

    @Query("DELETE FROM budget_table")
    void deleteAllBudgetsR();

    @Query("SELECT * FROM budget_table ORDER BY timeStamp DESC")
    LiveData<List<BudgetR>> getBudgets();



}
