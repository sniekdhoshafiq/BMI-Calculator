package com.sniekdho.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edWeight, edFeet, edInch;
    Button buttonBMI;
    TextView tvDisplay;

    TextToSpeech textToSpeech;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edWeight    = findViewById(R.id.edWeight);
        edFeet      = findViewById(R.id.edFeet);
        edInch      = findViewById(R.id.edInch);
        buttonBMI   = findViewById(R.id.buttonBMI);
        tvDisplay   = findViewById(R.id.tvDisplay);

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });


        buttonBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculate();

            }
        });

    }


    @SuppressLint("NewApi")
    public void calculate(){

        String stWeight = edWeight.getText().toString();
        String stFeet = edFeet.getText().toString();
        String stInch = edInch.getText().toString();

        if (stWeight.length()!=0 && stFeet.length()!=0 && stInch.length()!=0){

            double weight = Double.parseDouble(stWeight);
            double feet = Double.parseDouble(stFeet);
            double inch = Double.parseDouble(stInch);

            double feetToMeter = feet * 0.3048;
            double inchToMeter = inch * 0.0254;

            double height = feetToMeter + inchToMeter;

            double BMI = weight / (height * height);

            tvDisplay.setText("Your BMI Index = " + new DecimalFormat("##.##").format(BMI));

            textToSpeech.speak(""+tvDisplay.getText().toString(),
                                TextToSpeech.QUEUE_FLUSH, null, null);

            if (BMI<=18.4){
                tvDisplay.append("\n" + "You are Underweight");
                textToSpeech.speak(""+tvDisplay.getText().toString(), 
                                TextToSpeech.QUEUE_FLUSH, null, null);
                
            } else if (BMI>=18.5 && BMI<=25.0) {
                tvDisplay.append("\n" + "You have Normal Weight");
                textToSpeech.speak(""+tvDisplay.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH, null, null);
                
            } else if (BMI>=25.1 && BMI<=40.0) {
                tvDisplay.append("\n" + "You are Overweight");
                textToSpeech.speak(""+tvDisplay.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH, null, null);
                
            }else {
                tvDisplay.append("\n" + "You are Obesity");
                textToSpeech.speak(""+tvDisplay.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH, null, null);

            }


        }else {
            Toast.makeText(MainActivity.this, "Please input all fields", Toast.LENGTH_SHORT).show();
        }

    }


}