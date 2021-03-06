package com.example.jure_lokovsek.personalbudget;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jure_lokovsek.personalbudget.Database.BudgetType;
import com.example.jure_lokovsek.personalbudget.Database.DatabaseManager;
import com.example.jure_lokovsek.personalbudget.Fragment.BudgetListFragment;
import com.example.jure_lokovsek.personalbudget.Fragment.GraphViewFragment;
import com.example.jure_lokovsek.personalbudget.Room.BudgetR;
import com.example.jure_lokovsek.personalbudget.Room.BudgetRViewModel;

import org.joda.time.DateTime;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseManager mDatabaseManager;
    private Context mContext;
    private BudgetRViewModel mBudgetRViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        mDatabaseManager = new DatabaseManager(mContext);

        mBudgetRViewModel = ViewModelProviders.of(this).get(BudgetRViewModel.class);
        mBudgetRViewModel.getLiveData().observe(this, new Observer<List<BudgetR>>() {
            @Override
            public void onChanged(@Nullable List<BudgetR> budgetRS) {
                // update listview
                Log.d(TAG, "onChange");
                Toast.makeText(mContext, "onChange", Toast.LENGTH_LONG).show();
            }
        });

        loadMainFragment();
     //   mDatabaseManager.izpis(mDatabaseManager.getTodayBudgetList());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mDatabaseManager.storeBudget(ThreadLocalRandom.current().nextDouble(1.22, 300.55), BudgetType.DRINKS, "Stanje");

             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the
                // dialog layout
                final View dialogView= inflater.inflate(R.layout.add_budget_item, null);
                builder.setView(dialogView);

                final EditText value = dialogView.findViewById(R.id.editText_value);
                final Spinner spinnerType = dialogView.findViewById(R.id.spinner_type);
                // Creating adapter for spinner
                ArrayAdapter<BudgetType> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, BudgetType.getBudgetTypeList());
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // attaching data adapter to spinner
                spinnerType.setAdapter(dataAdapter);

                builder.setTitle("Dodaj nov dnevni strošek");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.ic_menu_manage);

                builder.setPositiveButton("Shrani", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(value.length() > 0){
                          //  mDatabaseManager.storeBudget(Double.valueOf(value.getText().toString()), (BudgetType) spinnerType.getSelectedItem(), null);
                          mBudgetRViewModel.insert(new BudgetR(DateTime.now().getMillis(), Double.valueOf(value.getText().toString()), "No comm", (BudgetType) spinnerType.getSelectedItem()));
                            Snackbar.make(dialogView, "Budget Shranjen!", Snackbar.LENGTH_LONG).setAction(null, null).show();
                        } else {
                            Snackbar.make(dialogView, "Vnesi podatke!", Snackbar.LENGTH_LONG).setAction(null, null).show();

                        }
                    }
                })
                .setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          //  super.onBackPressed();
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_budget_list) {
            loadMainFragment();
        } else if (id == R.id.nav_graph_view) {
            GraphViewFragment budgetListFragment = new GraphViewFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_fragment, budgetListFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadMainFragment(){
        BudgetListFragment budgetListFragment = new BudgetListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_fragment, budgetListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
