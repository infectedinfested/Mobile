package com.example.kevin.mobile.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kevin.mobile.Adaptors.ItemAdaptor;
import com.example.kevin.mobile.Collectors.DatabaseHelper;
import com.example.kevin.mobile.R;

public class DumpActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer ;
    DatabaseHelper myDB;
    private Toast mToast;

    private void showToast(String text){
        if(mToast != null){
            mToast.cancel();
        }

        mToast = Toast.makeText(DumpActivity.this.getApplicationContext(),text, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        mDrawer  = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);

        final Button buttonDump = findViewById(R.id.buttonDump);
        buttonDump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDB.DropData();
                System.out.println("database Dropped");
                showToast("data Dropped");
            }
        });

        final Button buttonWatch = findViewById(R.id.buttonWatch);
        buttonWatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDB.DropWatch();
                System.out.println("watch Dropped");
                showToast("watch Dropped");
            }
        });


        final Button buttonHalve = findViewById(R.id.buttonHalve);
        buttonHalve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDB.halfItemWatch();
                System.out.println("first watch Halved");
                showToast("first watch Halved");
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
                fragmentClass = LogoActivity.class;
                startActivity(new Intent(getApplicationContext(),LogoActivity.class));
                break;
            case R.id.nav_activity3:
                fragmentClass = DumpActivity.class;
                startActivity(new Intent(getApplicationContext(),DumpActivity.class));
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
