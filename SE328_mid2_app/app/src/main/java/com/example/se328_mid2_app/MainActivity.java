package com.example.se328_mid2_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String city;

    String apiKey="2269091e3fcea51a98bf3340a8432240";

    // we"ll make HTTP request to this URL to retrieve weather conditions
    String weatherWebserviceURL = "https://api.openweathermap.org/data/2.5/weather?" +
            "q="+city+"&appid="+apiKey+"&units=metric";

    TextView temperature, humidity;

    // JSON object that contains weather information
    JSONObject jsonObj;

    Spinner cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button act2 = (Button) findViewById(R.id.go2_Act2);

        Button btnDate = (Button) findViewById(R.id.datePicker);
        TextView selectedDate = (TextView) findViewById(R.id.selectedDate);

        Calendar c = Calendar.getInstance();
        DateFormat txtDate = DateFormat.getDateInstance();

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate.setText("Date Selected: " + txtDate.format(c.getTime()));
            }
        };

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cities = (Spinner) findViewById(R.id.spinner);
        temperature = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = String.valueOf(cities.getSelectedItem());
                weatherWebserviceURL = "https://api.openweathermap.org/data/2.5/weather?" +
                        "q="+city+"&appid="+apiKey+"&units=metric";

                weather(weatherWebserviceURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Please Select a City",Toast.LENGTH_SHORT).show();
            }
        });

// Going to Activity 2
act2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, Activity2.class));
    }
});


/*
// MAKING A TOAST (FIRST ARGUMENT IS THE JAVA CLASS NAME IN WHICH THE TOAST SHOWS, SECOND ARGUMENT IS HE TEXT TO BE SHOWN)
Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_SHORT).show();
 */

}

    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Amaan assgn 3","Response received");
                        Log.d("Amaan assgn 3",response.toString());

                        try{
                            JSONObject jsonMain= response.getJSONObject("main");
                            JSONObject jsonWind= response.getJSONObject("wind");

                            String townResponse = response.getString("name");
                            Log.d("Amaan-town",townResponse);

                            humidity.setText("Humidity: "+String.valueOf(jsonMain.getDouble("humidity")));

                            double temp = jsonMain.getDouble("temp");
                            Log.d("Amaan-temp",""+temp);
                            temperature.setText(String.valueOf(temp));
                        }

                        catch(JSONException e){
                            e.printStackTrace();
                            Log.e("Received Error", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Amaan assgn 3","Error retreiving url");
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}