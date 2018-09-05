package com.example.kevin.mobile.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.kevin.mobile.R;

public class LogoActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer  = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);



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
                //mDrawer.closeDrawers();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                break;
            case R.id.nav_activity2:
                //fragmentClass = LogoActivity.class;
                //mDrawer.closeDrawers();
                startActivity(new Intent(getApplicationContext(),LogoActivity.class));
                break;
            case R.id.nav_activity3:
                //fragmentClass = DumpActivity.class;
                //mDrawer.closeDrawers();
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
