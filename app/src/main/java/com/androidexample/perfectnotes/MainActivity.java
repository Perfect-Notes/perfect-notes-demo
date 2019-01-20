package com.androidexample.perfectnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //ListView listView;
    ToDoFragment f;
    ReminderFragment r;
    public static final String TAG = "Main Activty";
    time_table_fragment t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f = new ToDoFragment();
        r=new ReminderFragment();
        t=new time_table_fragment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        Log.i(TAG, "onCreate: Main Activity");
        setSupportActionBar(toolbar);
        //listView = findViewById(R.id.lv1);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String[] string = new String[]{"Time Table", "Today's Events", "Notes", "Reminders"};
        //ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,string);
        //listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        if (id == R.id.time_table) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, t).commit();

        } else if (id == R.id.events) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ).commit();

        } else if (id == R.id.reminders) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,r).commit();

        } else if (id == R.id.notes) {
//            Bundle b = new Bundle();
//            b.putBoolean("BACK_PRESSED", false);
//            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
        if(data.hasExtra("REMINDER")){
            r.onActivityResult(requestCode,resultCode,data);
        }
        else if (data.hasExtra("TIME_TABLE")){
            t.onActivityResult(requestCode,resultCode,data);
        }
        else
        f.onActivityResult(requestCode,resultCode,data);
        }
    }
}
