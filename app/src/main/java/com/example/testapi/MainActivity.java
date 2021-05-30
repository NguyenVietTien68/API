package com.example.testapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList itemList;
    ListView listView;
    Button btnGetAll, btnDelete;
    EditText editTextID;
    String url = "https://60b32e4f1bec230017bf3480.mockapi.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.lvItem);
        itemList= new ArrayList();
        btnGetAll= findViewById(R.id.btnGetApi);
        btnDelete= findViewById(R.id.btnDelete);
        editTextID= findViewById(R.id.ETVid);

        String id= editTextID.getText().toString();
        String getAll= url + "/Item";
        String delete= url + "/Item/" + id;

        btnGetAll.setOnClickListener(view->{
            getData(getAll);
        });

//        btnDelete.setOnClickListener(view->{
//            delete();
//           // getData();
//        });
    }

//    private void delete() {
//    }

    private void getData(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                try {
                    for (int i=0; i<response.length();i++){
                        JSONObject obj = (JSONObject)response.get(i);
                        itemList.add(obj.getString("itemName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.setAdapter(new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, itemList));
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}