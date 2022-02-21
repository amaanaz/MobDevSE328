package com.example.chap3_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double prices [] = {50,60,80};
    double totalCost;
    int tickets;
    String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button costBtn = (Button) findViewById(R.id.priceCalcButton);
        final EditText ticketCount = (EditText) findViewById(R.id.editTextNumber);
        final Spinner concert = (Spinner) findViewById(R.id.spinner);
        TextView result = (TextView) findViewById(R.id.costResult);
        DecimalFormat curency = new DecimalFormat("###,###.##");

        costBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickets = Integer.parseInt(ticketCount.getText().toString());
                choice = concert.getSelectedItem().toString();
                if(choice.equals("Life Revealed")){
                    totalCost = tickets * prices[0];
                }
                else if(choice.equals("Spatial Sense")){
                    totalCost = tickets * prices[1];
                }
                else{
                    totalCost = tickets * prices[2];
                }
                result.setText("Total Cost for "+tickets+" ticket(s) for "+ choice +
                        " is: "+ curency.format(totalCost));

            }
        });
    }
}