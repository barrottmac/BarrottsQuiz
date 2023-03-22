package com.example.assignment2_quizgenerator_barrottm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    Button btnTerm1,
            btnTerm2,
            btnTerm3,
            btnTerm4,
            btnNext;
    TextView definitionText;
    String correctAnswer;
    int nextClicked=0;

    UtilityFunctions util = new UtilityFunctions();

    String lastClicked="";
    int correctClicked=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable Declaration
        String fileLineStr = null;
        BufferedReader br = null;
        ArrayList<String> defList = new ArrayList<String>();
        ArrayList<String> termList = new ArrayList<String>();
        ArrayList<Button> buttonList = new ArrayList<Button>();
        ArrayList<String> termButtonList = new ArrayList<String>();



        Map<String,String>quizMap=new HashMap<String,String>();

        Context context = getApplicationContext();

        definitionText= findViewById(R.id.definition);

        btnTerm1=findViewById(R.id.term1);
        btnTerm2=findViewById(R.id.term2);
        btnTerm3=findViewById(R.id.term3);
        btnTerm4=findViewById(R.id.term4);
        btnNext=findViewById(R.id.next);

        buttonList.add(btnTerm1);
        buttonList.add(btnTerm2);
        buttonList.add(btnTerm3);
        buttonList.add(btnTerm4);
        int duration= Toast.LENGTH_SHORT;

        try {
            //File IO
            InputStream instream = getResources().openRawResource(R.raw.quizfile);
            br = new BufferedReader(new InputStreamReader(instream));
            Toast toastOpen=Toast.makeText(context,"File is open",duration);
            toastOpen.show();

            //File IO and Tokenizing
            while ((fileLineStr = br.readLine()) != null) {
                StringTokenizer stringtoken = new StringTokenizer(fileLineStr,"$$");
                while (stringtoken.hasMoreTokens()){
                    String definition = stringtoken.nextToken();
                    String term = stringtoken.nextToken();
                    //Adding to lists and Hash Map
                    defList.add(definition);
                    termList.add(term);
                    quizMap.put(definition,term);

                }
            }
            instream.close();
            Toast toastClose=Toast.makeText(context,"File is closed",duration);
            toastClose.show();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            Log.e("Error","an Exception occured");
        }


        //Shuffle Terms and Definitions Lists
        Collections.shuffle(defList);
        Collections.shuffle(termList);

        //Go to hash map to get matching term for the definition at position 0 in the list
        correctAnswer = quizMap.get(defList.get(0));

        //add the correct answer string to a list of terms to show on the buttons
        termButtonList.add(correctAnswer);

        //loop to add the rest of the terms to the list

        util.TermButton(termList,termButtonList,correctAnswer);

        //shuffle the list of terms containing the correct term
        Collections.shuffle(termButtonList);

        //Set the text to the button after the texts have been shuffled
        util.GenerateTerms(buttonList,termButtonList);

        //set the definition
        definitionText.setText(defList.get(0));


        //On click listeners for buttons
        //Set a "last clicked" variable to send to the next button to submit answer
        btnTerm1.setOnClickListener(view -> {lastClicked = (String) btnTerm1.getText();
            Toast toastSelected=Toast.makeText(context,String.format("%s selected", lastClicked),duration);
            toastSelected.show();
        });
        btnTerm2.setOnClickListener(view -> {lastClicked = (String) btnTerm2.getText();
            Toast toastSelected=Toast.makeText(context,String.format("%s selected", lastClicked),duration);
            toastSelected.show();});
        btnTerm3.setOnClickListener(view -> {lastClicked = (String) btnTerm3.getText();
            Toast toastSelected=Toast.makeText(context,String.format("%s selected", lastClicked),duration);
            toastSelected.show();});
        btnTerm4.setOnClickListener(view -> {lastClicked = (String) btnTerm4.getText();
            Toast toastSelected=Toast.makeText(context,String.format("%s selected", lastClicked),duration);
            toastSelected.show();});




        //Next Button Listener
        btnNext.setOnClickListener(view -> {
            //Compare correct answer to text on "last clicked" variabl
            ArrayList<String> tempDefList =(ArrayList<String>)defList.clone();
            nextClicked+=1;

            if (lastClicked==correctAnswer){
                //IF correct save to "correct clicked" variable
                Toast toastCorrect=Toast.makeText(context,"Correct!",duration);
                toastCorrect.show();
                correctClicked+=1;
                Toast toastCorrectAmount=Toast.makeText(context,String.valueOf(correctClicked),duration);
                toastCorrectAmount.show();
            }
            //remove definition from position 0 in def list

            defList.remove(0);
            lastClicked="";

            //IF definition list is 0 go to final score page with totalled "correct click" compared to length+1
            if (defList.size()==0){
                Intent i = new Intent(MainActivity.this,FinishPage.class);//create intent object
                Bundle extras = new Bundle();//create bundle object
                extras.putInt("correctClick", correctClicked);//fill bundle
                i.putExtras(extras);
                startActivity(i);
                Toast toastQuizOver=Toast.makeText(context,"Quiz Over!",duration);
                toastQuizOver.show();
                Toast toastCorrectAmount=Toast.makeText(context,String.valueOf(correctClicked),duration);
                toastCorrectAmount.show();

            }//End If
            //ELSE reset text for terms and definitions
            else {

                definitionText.setText(defList.get(0));
                correctAnswer = quizMap.get(defList.get(0));
                //add the correct answer string to a list of terms to show on the buttons
                termButtonList.clear();
                termButtonList.add(correctAnswer);

                Collections.shuffle(termList);

                //loop to add the rest of the terms to the list

                util.TermButton(termList,termButtonList,correctAnswer);

                //shuffle the list of terms containing the correct term
                Collections.shuffle(termButtonList);
                util.GenerateTerms(buttonList,termButtonList);
            }//End Else

        });//End of Next Button Listener












    }
}