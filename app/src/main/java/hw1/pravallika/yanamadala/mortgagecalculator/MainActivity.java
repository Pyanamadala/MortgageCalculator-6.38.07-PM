package hw1.pravallika.yanamadala.mortgagecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.*;
import android.widget.SeekBar.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText amtBorrowed;
    private SeekBar interestRate;
    private TextView disInterestRate;
    private RadioButton loanTerm;
    private RadioGroup rg;
    private TextView monthlyPay;
    private Button calculate;
    double amt;
    double tax=0.0;
    boolean taxChecked;
    double value;
    double result;
    private CheckBox taxesAndInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amtBorrowed = (EditText)findViewById(R.id.amtBorrowed);
        interestRate= (SeekBar)findViewById(R.id.seekBar);
        disInterestRate=(TextView)findViewById(R.id.interestRate);
        monthlyPay=(TextView)findViewById(R.id.monthlyPayment);
        value=interestRate.getProgress()/10.0;
        interestRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                value= progress/10.0;
                disInterestRate.setText("  "+String.valueOf(value)+"%");

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }}
        );
        rg = (RadioGroup) findViewById(R.id.radiogrp);
        taxesAndInsurance=(CheckBox)findViewById(R.id.taxes);

        calculate=(Button)findViewById(R.id.button);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String amtPattern= "\\d+(\\.\\d+)?";
                Pattern pattern = Pattern.compile(amtPattern);
                Matcher matcher = pattern.matcher(amtBorrowed.getText().toString());
                if(!matcher.matches()){
                    Toast.makeText(MainActivity.this,"Please enter a valid amount.",Toast.LENGTH_LONG).show();
                    return;
                }
                amt = Double.parseDouble(amtBorrowed.getText().toString());
                taxChecked = taxesAndInsurance.isChecked();
                if(taxChecked){
                    tax=0.001*(amt);
                }
                else{tax=0.0;
                }
                int selectedId=rg.getCheckedRadioButtonId();
                loanTerm=(RadioButton)findViewById(selectedId);
                double termValue = Double.parseDouble(loanTerm.getText().toString());
                if(value==0.0){
                    result=amt/(termValue*12)+tax;
                    monthlyPay.setText(" = $ "+Double.toString(result));
                    return;
                }
                result=(amt*value/1200)*(1/(1-Math.pow((1+(value/1200)),-(termValue*12))))+tax;
                monthlyPay.setText(" = $ "+Double.toString(result));

            }

        });
    }
}
