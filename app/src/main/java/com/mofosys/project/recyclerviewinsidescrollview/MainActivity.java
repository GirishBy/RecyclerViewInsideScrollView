package com.mofosys.project.recyclerviewinsidescrollview;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView mb_recyclerView;
    private MyBrandsAdapter myBrandsAdapter;
    private ArrayList<UpdateBrandsModel> my_brands_list;

    //add your URl here
    private String URL = "http://testsell.eleczo.com/api-selected-brands";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mb_recyclerView = (RecyclerView) findViewById(R.id.mb_recycler_view);

        //progress dialog initialization
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        my_brands_list = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mb_recyclerView.setLayoutManager(mLayoutManager);
        mb_recyclerView.setItemAnimator(new DefaultItemAnimator());
        myBrandsAdapter = new MyBrandsAdapter(my_brands_list);
        mb_recyclerView.setAdapter(myBrandsAdapter);

        getSelectedBrands();

    }


    public void getSelectedBrands() {

        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.i("TEST", "Selected Brands Response: " + response);

                        if (response.equalsIgnoreCase("token mis-match")) {
                            Toast.makeText(MainActivity.this, "Token mis-match", Toast.LENGTH_SHORT).show();
                            mb_recyclerView.setVisibility(View.GONE);

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if (jsonArray.length() == 0) {
                                    mb_recyclerView.setVisibility(View.GONE);
                                } else {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        UpdateBrandsModel selectBrandModel = new UpdateBrandsModel();
                                        JSONObject jsonData1 = jsonArray.getJSONObject(i);
                                        selectBrandModel.setMy_brand_name(jsonData1.getString("name"));
                                        selectBrandModel.setMy_brand_id(jsonData1.getString("brandId"));
                                        selectBrandModel.setImage(jsonData1.getString("brand_image"));

                                        my_brands_list.add(selectBrandModel);
                                    }

                                    myBrandsAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Server Down...try again later", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("token", "9000080000ZRiXOCUNBjuLguyQJLmmzh9uMecA7a");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}
