package com.example.assignment2_quizgenerator_barrottm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_page);
        TextView txtYourScore=findViewById(R.id.score);
        Button btnRestart;
        btnRestart=findViewById(R.id.restart);



        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            int correctAns=extras.getInt("correctClick");
            txtYourScore.setText(String.format("%s/20", String.valueOf(correctAns)));
        }

        btnRestart.setOnClickListener(view -> {
            Intent intent= new Intent(FinishPage.this,MainActivity.class);
            startActivity(intent);
        });

    }
}