package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch,btnNextday;
    TextView tvCity,tvState,tvThoitiet,tvNhietdo,tvWater,tvCloud,tvAir,tvDay;
    ImageView ImgIcon;
    String City ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        getcurenData("Hanoi"); // vừa vào thì truyền Hà nội cho nó luôn
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtSearch.getText().toString();
                if(City.equals("")){
                    City="Hanoi";
                    getcurenData(City);// nếu chưa có gì gán Hà Nội cho nó
                }else {
                    City = city; // nếu không thì cho nội dung nhập là nó
                    getcurenData(City);
                }

            }
        });
        btnNextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,MainActivity2.class); // Intent để truyền dữ liệu sang 1 cái MainActivity2
                intent.putExtra("name",city);
                startActivity(intent);

            }
        });
    }
    public void getcurenData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&appid=6235e14d297c85c75703c1a7147c3152&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            tvCity.setText("Tên thành phố: " + name);
                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = simpleDateFormat.format(date);//

                            tvDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);// lay phan tử đầu tiên của mảng
                            String status = jsonObjectWeather.getString("main");
                            String Icon = jsonObjectWeather.getString("icon");

                            String UrlImage = "http://openweathermap.org/img/wn/"+Icon+".png";

//


                            Picasso.with(MainActivity.this)
                                    .load(UrlImage)
                                    .into(ImgIcon);
//

                            tvThoitiet.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("temp");


                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());// doi kieu ve int xong ve string

                            tvNhietdo.setText(Nhietdo+" độ C");
                            tvWater.setText(doam+"%");


                            JSONObject jsonObjectAir = jsonObject.getJSONObject("wind");
                            String air = jsonObjectAir.getString("speed");
                            tvAir.setText(air+"m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            tvCloud.setText(may +"%");


                            JSONObject jsonObjectSYS = jsonObject.getJSONObject("sys");
                            String datnuoc = jsonObjectSYS.getString("country");
                            tvState.setText("Tên quốc gia :"+datnuoc);




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
    private void anhxa() {
        edtSearch = (EditText) findViewById(R.id.edtFind);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnNextday= (Button) findViewById(R.id.btn_Nextday);
        tvCity = (TextView) findViewById(R.id.tv_City);
        tvState = (TextView) findViewById(R.id.tv_State);
        tvAir = (TextView) findViewById(R.id.tv_Air);
        tvThoitiet = (TextView) findViewById(R.id.tv_Thoitiet);
        tvNhietdo = (TextView) findViewById(R.id.tv_Nhietdo);
        tvWater = (TextView) findViewById(R.id.tv_Water);
        tvCloud = (TextView) findViewById(R.id.tv_Cloud);
        tvDay = (TextView) findViewById(R.id.tvDay);
        ImgIcon = (ImageView) findViewById(R.id.ImgIcon);
    }
}