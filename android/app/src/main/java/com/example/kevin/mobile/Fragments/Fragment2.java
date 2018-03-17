package com.example.kevin.mobile.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.kevin.mobile.MainActivity;
import com.example.kevin.mobile.R;

public class Fragment2 extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer  = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupDrawerContent(nvDrawer);
    }





    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        mDrawer  = findViewById(R.id.drawer_layout);
        Fragment fragment = null;
        Class fragmentClass = null;
        System.out.println(menuItem.getItemId());
        System.out.println(R.id.nav_activity3);
        switch(menuItem.getItemId()) {
            case R.id.nav_activity1:
                //if (this.getClass()!= MainActivity.class)
                //fragmentClass = Fragment1.class;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.nav_activity2:
                fragmentClass = Fragment2.class;
                startActivity(new Intent(getApplicationContext(),Fragment2.class));
                break;
            case R.id.nav_activity3:
                fragmentClass = Fragment3.class;
                startActivity(new Intent(getApplicationContext(),Fragment3.class));
                break;
            default:
                //fragmentClass = Fragment1.class;
        }



        // Insert the fragment by replacing any existing fragment
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer

        //mDrawer.closeDrawers();
    }

}
