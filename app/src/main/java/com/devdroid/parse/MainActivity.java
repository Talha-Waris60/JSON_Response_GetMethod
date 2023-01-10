package com.devdroid.parse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // Get Method
        String url = "https://jsonplaceholder.typicode.com/users";
        ArrayList<String> arrayNames = new ArrayList<>();

        // Initialize it in onCreate() Method of application class and pass reference :

        AndroidNetworking.initialize(this);

        // Get api
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        // set log whether is coming or not
                        Log.d("Response",jsonArray.toString());

                        // Start parsing
                        // Create object of JSON Array
                        try
                        {

                            // use for to fetch all name from JSON Array
                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                // fetch the name from JSONObject
                                String name = jsonObject.getString("name");

                                // Add the names of all object in ArrayList
                                arrayNames.add(name);

                                // Set Adapter
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayNames);
                                listView.setAdapter(arrayAdapter);
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("Error",anError.toString());

                    }
                });



    }
}