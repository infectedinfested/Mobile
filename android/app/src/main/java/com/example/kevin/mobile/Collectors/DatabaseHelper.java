package com.example.kevin.mobile.Collectors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kevin.mobile.Models.Item;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;
    public static final String DATABASSE_NAME="item.db";
    public static final String TABLE_NAME="item_table";
    public static final String COL_0="ID";
    public static final String COL_1="NAME";
    public static final String COL_2="WATCH";
    public static final String COL_3="BUY";
    public static final String COL_4="BUYORIGIN";
    public static final String COL_5="SELL";
    public static final String COL_6="SELLORIGIN";
    public static final String COL_7="TYPE";



    public DatabaseHelper(Context context) {
        super(context, DATABASSE_NAME, null, 1);
        db = this.getWritableDatabase();
    }
    /*private int id;
    private String name;
    private Boolean watch;
    private int buy;
    private int targetBuy;
    private int sell;
    private int targetSell;
    private String type;*/

    public boolean IsEmpty(){
        Cursor item = db.rawQuery("select * from item_table",null);
        if (item.getCount()>0)
            return false;
        return true;
    }

    public boolean insertItem(Item item){
        ContentValues content = new ContentValues();
        content.put(COL_0,item.getId());
        content.put(COL_1,item.getName());
        content.put(COL_2,item.getWatch());
        content.put(COL_3,item.getBuy());
        content.put(COL_4,item.getOriginBuy());
        content.put(COL_5,item.getSell());
        content.put(COL_6,item.getOriginSell());
        content.put(COL_7,item.getType());
        long result = db.insert(TABLE_NAME,null,content);
        if (result==-1)
            return false;
        return true;
    }
    public boolean updateItem(Item item){
        ContentValues content = new ContentValues();
        content.put(COL_1,item.getName());
        content.put(COL_2,item.getWatch());
        content.put(COL_3,item.getBuy());
        content.put(COL_4,item.getOriginBuy());
        content.put(COL_5,item.getSell());
        content.put(COL_6,item.getOriginSell());
        content.put(COL_7,item.getType());
        long result = db.update(TABLE_NAME, content,"id = ?",new String[] {String.valueOf(item.getId())});
        if (result==-1)
            return false;
        return true;
    }



    public Item selectItem(int id){
        Item result;
        try {
            Cursor item = db.rawQuery("select * from item_table where id="+id,null);
            result = new Item(id);
            result.setName(item.getString(1));
            result.setWatch(item.getInt(2));
            result.setBuy(item.getInt(3));
            result.setOriginBuy(item.getInt(4));
            result.setSell(item.getInt(5));
            result.setOriginSell(item.getInt(6));
            result.setType(item.getString(7));
            return result;
        }catch(Exception e) {
            return null;
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + " " +
                "( "+ COL_0+" INTEGER PRIMARY KEY," +   //id
                " "+COL_1+" TEXT," +                    //name
                " "+COL_2+" INTEGER," +                 //watch
                " "+COL_3+" INTEGER," +                 //buy
                " "+ COL_4 +" INTEGER," +               //buyOrigi
                " "+ COL_5 +" INTEGER," +               //sell
                " "+ COL_6 +" INTEGER," +               //sellorigi
                " "+ COL_7 +" TEXT)" );                 //type
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public List<Integer> insertAllItems(ArrayList<Item> items) {
        List<Integer> results = new ArrayList<>();
        for (Item item: items) {
            ContentValues content = new ContentValues();
            content.put(COL_0,item.getId());
            content.put(COL_1,item.getName());
            content.put(COL_2,item.getWatch());
            content.put(COL_3,item.getBuy());
            content.put(COL_4,item.getOriginBuy());
            content.put(COL_5,item.getSell());
            content.put(COL_6,item.getOriginSell());
            content.put(COL_7,item.getType());
            long result = db.insert(TABLE_NAME,null,content);
            if (result==-1)
                results.add(item.getId());
        }
        return results;
    }


    public List<Integer> updateAllItems(ArrayList<Item> items) {
        List<Integer> results = new ArrayList<>();
        for (Item item: items) {
            ContentValues content = new ContentValues();
            content.put(COL_1,item.getName());
            content.put(COL_2,item.getWatch());
            content.put(COL_3,item.getBuy());
            content.put(COL_4,item.getOriginBuy());
            content.put(COL_5,item.getSell());
            content.put(COL_6,item.getOriginSell());
            content.put(COL_7,item.getType());
            long result = db.update(TABLE_NAME, content, "id="+item.getId(), null);
            if (result==-1)
                results.add(item.getId());
        }
        return results;
    }

    public ArrayList<Item> selectAllItems() {
        Cursor mCursor = db.rawQuery("select * from item_table",null);
        ArrayList<Item> items = new ArrayList<>();

        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Item result = new Item(mCursor.getInt(0));
            result.setName(mCursor.getString(1));
            result.setWatch(mCursor.getInt(2));
            result.setBuy(mCursor.getInt(3));
            result.setOriginBuy(mCursor.getInt(4));
            result.setSell(mCursor.getInt(5));
            result.setOriginSell(mCursor.getInt(6));
            result.setType(mCursor.getString(7));
            items.add(result);
        }
        return items;
    }

    public ArrayList<Item> selectWatch() {
        Cursor mCursor = db.rawQuery("select * from item_table WHERE watch > 0",null);
        ArrayList<Item> items = new ArrayList<>();

        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Item result = new Item(mCursor.getInt(0));
            result.setName(mCursor.getString(1));
            result.setWatch(mCursor.getInt(2));
            result.setBuy(mCursor.getInt(3));
            result.setOriginBuy(mCursor.getInt(4));
            result.setSell(mCursor.getInt(5));
            result.setOriginSell(mCursor.getInt(6));
            result.setType(mCursor.getString(7));
            items.add(result);
        }
        return items;
    }


}
