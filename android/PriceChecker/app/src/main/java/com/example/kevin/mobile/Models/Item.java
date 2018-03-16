package com.example.kevin.mobile.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 14/03/2018.
 */

public class Item {
    private int id;
    private String name;
    private int watch;
    private int buy;
    private int originBuy;
    private int sell;
    private int originSell;
    private String type;

    public Item (int id){
        this.id = id;
        this.watch = 0;
        this.originBuy = 0;
        this.originSell = 0;
    }
    public boolean InsertPrice(String prices) throws JSONException {
        JSONObject reader = new JSONObject(prices);
        if (id == reader.getInt("id")){
            JSONObject jBuy  = reader.getJSONObject("buys");
            this.buy = jBuy.getInt("unit_price");
            JSONObject jSell  = reader.getJSONObject("sells");
            this.sell = jSell.getInt("unit_price");
            return true;
        }
        return false;
    }
    public boolean InsertData(String data) throws JSONException{
        if (data != null) {
            JSONObject reader = new JSONObject(data);
            if (id == reader.getInt("id")) {
                this.name = reader.getString("name");
                this.type = reader.getString("type");
                return true;
            }
        }
        return false;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getOriginBuy() {
        return originBuy;
    }

    public void setOriginBuy(int originBuy) {
        this.originBuy = originBuy;
    }

    public int getOriginSell() {
        return originSell;
    }

    public void setOriginSell(int originSell) {
        this.originSell = originSell;
    }

}
