package com.example.android.gamescores;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOError;

public class MainActivity extends AppCompatActivity {

    private GamesListAdapter mAdapter;
    private RecyclerView mGamesList;
    private Cursor mCursor;

    private String sample_response = "[{\"id\":235,\"finished\":1,\"gameType\":\"Jokeren\",\"ranking\":{\"GICA\":390,\"NIVA\":311,\"GEPE\":310}},{\"id\":234,\"finished\":1,\"gameType\":\"Schaken\",\"ranking\":{\"GEPE\":1,\"GICA\":0}},{\"id\":233,\"finished\":1,\"gameType\":\"Stripketrekken\",\"ranking\":{\"NIVA\":3,\"DAPI\":4,\"GICA\":4,\"GEPE\":5,\"MABE\":6}}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set local attributes to corresponding views
        mGamesList = (RecyclerView) this.findViewById(R.id.rv_games);

        // Create an adapter for that cursor to display the data
        mCursor = getJSONCursor(sample_response);
        mAdapter = new GamesListAdapter(this, mCursor);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        mGamesList.setLayoutManager(new LinearLayoutManager(this));

        // Link the adapter to the RecyclerView
        mGamesList.setAdapter(mAdapter);
    }

    private Cursor getJSONCursor(String response){
        try{
            JSONArray array = new JSONArray(response);
            return new JSONArrayCursor(array);
        } catch(JSONException exception)
        {
            String ex = exception.getMessage();
        }
        return null;
    }

}
