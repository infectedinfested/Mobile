package com.example.kevin.mobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.kevin.mobile.Adaptors.ItemAdaptor;
import com.example.kevin.mobile.Collectors.DatabaseHelper;
import com.example.kevin.mobile.Collectors.getUrlContentTask;
import com.example.kevin.mobile.Fragments.Fragment1;
import com.example.kevin.mobile.Fragments.Fragment2;
import com.example.kevin.mobile.Fragments.Fragment3;
import com.example.kevin.mobile.Models.Item;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NotificationHelper helper;
    DatabaseHelper myDB;
    ArrayList<Item> items = null;

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startTimer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);

        items = new ArrayList<>();
        if (myDB.IsEmpty()){

            items = getItemsCall();
            myDB.insertAllItems(items);
            System.out.println("getting data and pushing into Database");

        }else{
            items = myDB.selectAllItems();
            System.out.println("data found in database");
        }

        if (items.size() > 0) {
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(final Item object1, final Item object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }




        mAdapter = new ItemAdaptor(this, items);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        setupDrawerContent(nvDrawer);

    }




    private Cursor getJSONCursor(ArrayList<Item> response){
        try{
            JSONArray array = new JSONArray(new Gson().toJson(response));
            return new JSONArrayCursor(array);
        } catch(JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    private String getItems(){
        try {
            return new getUrlContentTask().execute("https://api.guildwars2.com/v2/items/").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getItemsData(int itemNumber){
        try {
            return new getUrlContentTask().execute("https://api.guildwars2.com/v2/items/"+itemNumber).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getItemsPrizes(ArrayList<Integer> itemNumbers){
        StringBuilder commaSepValueBuilder = new StringBuilder();

        for ( int i = 0; i< itemNumbers.size(); i++){
            commaSepValueBuilder.append(itemNumbers.get(i));
            if ( i != itemNumbers.size()-1){
                commaSepValueBuilder.append(", ");
            }
        }
        try {
            return new getUrlContentTask().execute("https://api.guildwars2.com/v2/commerce/prices?ids="+commaSepValueBuilder.toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getItemsPrice(int itemNumber){
        try {
            return new getUrlContentTask().execute("https://api.guildwars2.com/v2/commerce/prices/"+itemNumber).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ArrayList<Item> getItemsCall(){
        ArrayList<Item> lItems = new ArrayList<>();
        String items = getItems();
        JSONArray jsonItems = new JSONArray();
        try {
            jsonItems = new JSONArray(items);
        } catch (JSONException e) {
            System.out.println("can't parse items");
            e.printStackTrace();
        }

        Item item;
        System.out.println("going into item filler" + jsonItems.length());
        int max;
        try {
            max = Integer.parseInt(jsonItems.get(jsonItems.length()-1).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<150; i++){
            //max
            try {
                int id = (int) jsonItems.get(i);
                String price = getItemsPrice(id);
                if (price != null){
                    System.out.println(id);
                    item = new Item(id);
                    item.InsertData(getItemsData(id));
                    item.InsertPrice(price);
                    lItems.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return lItems;
    }


    private void startTimer(){
        TimerTask updateWatchItems = new TimerTask() {

            @Override
            public void run() {
                List<Item> watch = myDB.selectWatch();
                for (Item item : watch){
                    System.out.println("Buy : "+item.getBuy());
                    System.out.println("Buyorigi : "+item.getOriginBuy());
                    System.out.println("Sell : "+item.getSell());
                    System.out.println("SellOrigi : "+item.getOriginSell());
                    System.out.println("watch : "+item.getWatch());


                    System.out.println("ID : " + item.getId());
                    //testcase
                    if ( item.getId() == 103 )
                        item.setBuy(3000);


                    System.out.println("test1 > "+(((double)item.getBuy())*((((double)item.getWatch()*10))/100+1)));
                    if (item.getOriginBuy() == (((double)item.getBuy())*((((double)item.getWatch()*10))/100+1))){
                        noficationCall(item.getName()+ " has alerted,test");
                    }

                    System.out.println("test2 < "+(((double)item.getBuy())/((((double)item.getWatch()*10))/100+1)));
                    if (item.getOriginBuy()< (((double)item.getBuy())/((((double)item.getWatch()*10))/100+1))){
                        noficationCall(item.getName()+ " has alerted");
                    }

                    System.out.println("test3 > "+(((double)item.getSell())*((((double)item.getWatch()*10))/100+1)));
                    if (item.getOriginSell()> (((double)item.getSell())*((((double)item.getWatch()*10))/100+1))){
                        noficationCall(item.getName()+ " has alerted");
                    }
                    System.out.println("test3 < "+(((double)item.getSell())/((((double)item.getWatch()*10))/100+1)));
                    if (item.getOriginSell()< (((double)item.getSell())/((((double)item.getWatch()*10))/100+1))){
                        noficationCall(item.getName()+ " has alerted");
                    }
                }
            }
        };
        TimerTask updateItems = new TimerTask() {

            @Override
            public void run() {
                myDB.updateAllItems(getItemsCall());
            }
        };



        Timer updateWatchItemsTimer = new Timer("watch");//create a new Timer
        Timer updateItemsTimer = new Timer("reload");//create a new Timer

        updateWatchItemsTimer.scheduleAtFixedRate(updateWatchItems, 1800000, 1800000);//update the wacthlist every 30 mins 1800000
        updateItemsTimer.scheduleAtFixedRate(updateItems, 28800000, 28800000);//update the whole list every 12 hours 28800000
    }

    private void noficationCall(String text){
        helper = new NotificationHelper(this);
        Notification.Builder builder= helper.getChannelNotification(text,"");
        helper.getManager().notify(new Random().nextInt(),builder.build());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    //    System.out.println(item.getItemId());
    //    if ( item.getItemId()==2131230823){
    //        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    //    }
    //    if ( item.getItemId()==2131230824){
    //        startActivity(new Intent(getApplicationContext(),Fragment2.class));
    //    }
        if ( item.getItemId()==2131230825){
            startActivity(new Intent(getApplicationContext(),Fragment3.class));
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_activity1:
                //if (this.getClass()!= MainActivity.class)
                    fragmentClass = Fragment1.class;
                break;
            case R.id.nav_activity2:
                fragmentClass = Fragment2.class;
                break;
            case R.id.nav_activity3:
                fragmentClass = Fragment3.class;
                break;
            default:
                fragmentClass = Fragment1.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}
