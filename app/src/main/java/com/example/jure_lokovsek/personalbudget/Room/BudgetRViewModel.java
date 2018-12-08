package com.example.jure_lokovsek.personalbudget.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class BudgetRViewModel extends AndroidViewModel{
    private BudgetRRepository repository;
    private LiveData<List<BudgetR>> rLiveData;

    public BudgetRViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetRRepository(application);
        rLiveData = repository.getAllBudgets();
    }

    public void insert(BudgetR budgetR){
        repository.insert(budgetR);
    }

    public void update(BudgetR budgetR){
        repository.update(budgetR);
    }

    public void delete(BudgetR budgetR){
        repository.delete(budgetR);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<BudgetR>> getrLiveData() {
        return rLiveData;
    }
}
