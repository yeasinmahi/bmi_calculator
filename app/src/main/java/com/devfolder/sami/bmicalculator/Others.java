package com.devfolder.sami.bmicalculator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Arafat on 25/02/2017.
 */

public class Others {
    public static double getImperialResult(double pound,double inch){
        return (pound/(inch*inch))*703;
    }
    public static double getMetricResult(double kg,double meter){
        return (kg/(meter*meter));
    }
    public static double getInch(double feet,double inch){
        return (feet*12)+inch;
    }
    public static boolean isEmpty(EditText editText){
        return editText.getText().equals("");
    }

    public static String getBmiResult(double bmi){
        String result="";
        if (bmi<18.5){
            result = "You are Underweight";
        }else if (bmi>=18.5 && bmi<25){
            result = "You are Normal Weight";
        }else if (bmi>=25 && bmi<30){
            result = "You are OverWeight";
        }else if (bmi>=30){
            result = "You are Obesity";
        }else{
            result = "A problem occured";
        }
        return result;
    }
    public static void clearAll(ViewGroup root) {
        for (int j = root.getChildCount(); j >0; j--) {
            View view = root.getChildAt(j);
            if (view instanceof ViewGroup) {
                clearAll((ViewGroup) view);
                continue;
            }
            if (view instanceof EditText) {
                ((EditText) view).setText("");
                continue;
            }
        }
    }

}
