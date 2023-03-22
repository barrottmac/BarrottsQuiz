package com.example.assignment2_quizgenerator_barrottm;

import android.widget.Button;

import java.util.ArrayList;

public class UtilityFunctions {

    public UtilityFunctions() {

    }
    public ArrayList TermButton(ArrayList largeList,ArrayList buttonsList, String correctAnswer){
        for (int i=0;i<=2;i++){

            //if one of the terms is the correct answer pick the last term in the list

            if (largeList.get(i)==correctAnswer){
                buttonsList.add(largeList.get(i+3));
            }
            else{
                buttonsList.add(largeList.get(i));
            }
        }
        return buttonsList;
    }


    public ArrayList GenerateTerms(ArrayList<Button> buttonList, ArrayList<String> termsList){
        for (int i=0;i<=3;i++) {
            buttonList.get(i).setText(termsList.get(i));
        }

        return buttonList;

    }
}
