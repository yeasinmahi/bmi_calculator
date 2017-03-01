package com.devfolder.sami.bmicalculator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText heightFt;
    private TextInputLayout heightFtLayout;
    private TextInputLayout heightInLayout;
    private TextInputLayout weightLayout;
    private EditText heightIn;
    private EditText weight;
    private RadioButton imperialRadio;
    private RadioButton metricRadio;
    private ViewGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Others.clearAll(group);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void init() {
        imperialRadio = (RadioButton) findViewById(R.id.imperialRadioButton);
        metricRadio = (RadioButton) findViewById(R.id.metricRadioButton);
        heightFt = (EditText) findViewById(R.id.heightFt);
        heightFtLayout = (TextInputLayout) findViewById(R.id.heightFtLayout);
        heightInLayout = (TextInputLayout) findViewById(R.id.heightInLayout);
        weightLayout = (TextInputLayout) findViewById(R.id.weightLayout);
        heightIn = (EditText) findViewById(R.id.heightIn);

        weight = (EditText) findViewById(R.id.weight);
        group = (ViewGroup)findViewById(R.id.viewGroup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onCalculateButtonClick(View view) {
        if (validate()) {
            double heightF, heightI, heightW, bmi = 0;
            try {
                heightF = Double.parseDouble(heightFt.getText().toString());
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Check height textbox properly", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                heightI = Double.parseDouble(heightIn.getText().toString());
            } catch (Exception ex) {
                heightI = 0;
            }
            try {
                heightW = Double.parseDouble(weight.getText().toString());
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Check weight textbox properly", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imperialRadio.isChecked()) {
                bmi = Others.getImperialResult(heightW, Others.getInch(heightF, heightI));
            } else if (metricRadio.isChecked()) {
                bmi = Others.getMetricResult(heightW, heightF);
            }
            popUpWindow(bmi);
            Others.clearAll(group);
        }
    }

    private boolean validate() {
        if (Others.isEmpty(heightFt)) {
            Toast.makeText(getApplicationContext(), "Height should be given", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Others.isEmpty(weight)) {
            Toast.makeText(getApplicationContext(), "Weight should be given", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onRadioButtonClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.imperialRadioButton:
                if (checked)
                    changeOnDemandImperial();
                    Others.clearAll(group);
                break;
            case R.id.metricRadioButton:
                if (checked)
                    changeOnDemandMetric();
                    Others.clearAll(group);
                break;
        }
    }

    private void changeOnDemandMetric() {
        heightFtLayout.setHint("Meter");
        heightFtLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6));
        heightInLayout.setVisibility(View.GONE);
        weightLayout.setHint("KG");
    }

    private void changeOnDemandImperial() {
        heightFtLayout.setHint("Feet");
        heightFtLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
        heightInLayout.setVisibility(View.VISIBLE);
        weightLayout.setHint("Pound");
    }
    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }*/
    public void popUpWindow(double bmi) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.bmi_result);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.customTextView);
        DecimalFormat df = new DecimalFormat("###.##");
        text.setText("Your BMI Score is " + df.format(bmi) + "\n" + Others.getBmiResult(bmi));
        ImageView image = (ImageView) dialog.findViewById(R.id.ib_close);
        image.setImageResource(R.mipmap.close);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
