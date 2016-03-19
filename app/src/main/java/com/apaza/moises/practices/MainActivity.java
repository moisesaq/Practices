package com.apaza.moises.practices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apaza.moises.practices.countdowntimer.ListCountDownTimer;
import com.apaza.moises.practices.ormlite.ContactActivity;
import com.apaza.moises.practices.pinnedlistview.ListHeaderCustom;

public class MainActivity extends AppCompatActivity {

    private ListHeaderCustom listHeaderCustom;
    private MainMenu mainMenu;
    private ListCountDownTimer listCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(mainMenu == null) mainMenu = new MainMenu();
        showFragment(mainMenu, MainMenu.TAG);
    }

    public void showFragment(Fragment fragment, String tag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =  fm.beginTransaction();
        ft.replace(R.id.containerMain, fragment);
        if(tag!=null){
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_show_pinned_section_list_view:
                if(listHeaderCustom == null) listHeaderCustom = new ListHeaderCustom();
                showFragment(listHeaderCustom, ListHeaderCustom.TAG);
                return true;

            case R.id.action_show_test_ormlite:
                Intent intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_show_count_down_timer:
                if(listCountDownTimer == null) listCountDownTimer = new ListCountDownTimer();
                showFragment(listCountDownTimer, ListCountDownTimer.TAG);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}