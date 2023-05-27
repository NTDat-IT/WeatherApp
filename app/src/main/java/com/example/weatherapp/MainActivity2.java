package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {


    String tenThanhpho = "";


    //khai bao
    ImageView imageBack;
    TextView tvName ;
    ListView lv7day;

    CustomAdapter customAdapter;

    ArrayList<ThoiTiet> arrayWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        Anhxa();
        String city = intent.getStringExtra("name"); // tạo 1 biến city để nhận dữ liệu truyền sang

        if(city.equals("")){
            tenThanhpho = "Hanoi";
            laydulieu7ngay(tenThanhpho);
        }else {
            tenThanhpho=city;
            laydulieu7ngay(tenThanhpho);
        }
        // bắt sự kiện cho nút back
       imageBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
    }

    private void Anhxa() {

        imageBack = (ImageView)findViewById(R.id.imgback);
        tvName = (TextView) findViewById(R.id.tv_cityname);
        lv7day = (ListView) findViewById(R.id.lv_7day);


        arrayWeather = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(MainActivity2.this,arrayWeather);
        lv7day.setAdapter(customAdapter);

    }

    private void laydulieu7ngay(String data) {

        String Url7day = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=6235e14d297c85c75703c1a7147c3152";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url7day,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            tvName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            arrayWeather.clear();

                            // vì là 7 ngày giống nhau nên dùng for để xử lí

                            for(int i =0 ; i<jsonArrayList.length();i++){

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt_txt");




//                                long l = Long.valueOf(ngay);
//                                Date date = new Date(l * 1000); // Sửa lại thành l * 1000
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
//                                String Day = simpleDateFormat.format(date);


//                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
//                                String ngay = jsonObjectList.getString("dt");
//
//                                long l = Long.valueOf(ngay);
//                                Date date = new Date(l * 1000L);
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd ");
//                                String Day = simpleDateFormat.format(date);


                                //đọc dữ liệu nhiểt độ

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String Tempmax = jsonObjectTemp.getString("temp_max");
                                String Tempmin = jsonObjectTemp.getString("temp_min");


                                // nhiệt độ thì có thể có 30,2 nên chuyển về string
                                Double a = Double.valueOf(Tempmax);
                                String TempMax = String.valueOf(a.intValue());
                                Double b = Double.valueOf(Tempmin);
                                String TeapMin = String.valueOf(b.intValue());


                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String Status = jsonObjectWeather.getString("main");
                                String iCON = jsonObjectWeather.getString("icon");

                                arrayWeather.add(new ThoiTiet(ngay,Status,iCON,Tempmax,Tempmin));



                            }

                            customAdapter.notifyDataSetChanged();




                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}