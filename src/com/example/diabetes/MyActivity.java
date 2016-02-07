package com.example.diabetes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Array;
import java.text.DecimalFormat;

public class MyActivity extends BaseActivity{
    /**
     * Called when the activity is first created.
     */

    EditText txtA;
    EditText txtB;
    Button btnCalc;
    RadioGroup formulaGroup;
    RadioButton rdoX;
    RadioButton rdoY;
    private boolean useA = true;
    private boolean useX = true;
    private double A1C;
    private double eAG;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        txtA = (EditText) findViewById( R.id.txtA );
        txtB = (EditText) findViewById( R.id.txtB );
        btnCalc = (Button) findViewById( R.id.btnCalc );
        formulaGroup = (RadioGroup) findViewById( R.id.formulaGroup );
        rdoX = (RadioButton) findViewById( R.id.rdoX );
        rdoY = (RadioButton) findViewById( R.id.rdoY );

        txtA.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange( View v, boolean hasFocus ){
                handleOnFocusChange( v, hasFocus, "txtA" );
            }
        } );

        txtB.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange( View v, boolean hasFocus ){
                handleOnFocusChange( v, hasFocus, "txtB" );
            }
        } );
    }

    private void handleOnFocusChange( View v, boolean hasFocus, String whichField ){
//        useA = whichField == "txtA" && hasFocus;
        if( whichField == "txtA" ){
            Log.i( "diabetes", whichField + " got the focus" );
            useA = true;
            txtA.setText( "" );
            txtB.setText( "0" );
        } else{
            Log.i( "diabetes", whichField + " lost focus" );
            useA = false;
            txtA.setText( "0.0" );
            txtB.setText( "" );
        }
//        Log.i( "diabetes", "useA: " + useA + "\nwhich field: " + whichField );
    }

    public void Convert( View v ){
        try{
            A1C = Double.parseDouble( txtA.getText().toString() );
            eAG = Double.parseDouble( txtB.getText().toString() );
            Double otherValue = 0.0;
            Log.i( "diabetes", "A1C: " + String.valueOf( A1C ));
            Log.i( "diabetes", "eAG: " + String.valueOf( eAG ));
            if( useA ){
                //We know A1C.
                if( useX ){
                    otherValue = (1.583 * A1C - 2.52) * 18.05;
                } else{
                    otherValue = (A1C * 35.6) - 77.3;
                }
                DecimalFormat f = new DecimalFormat( "##" );
                txtB.setText( f.format( otherValue ) );
            } else{
                //DCCT
                if( useX ){
                    otherValue = (eAG / 28.57315) + 1.59191409;
                } else{
                    otherValue = (eAG / 35.6) + 2.17134831;
                }
                DecimalFormat f = new DecimalFormat( "##.0" );
                txtA.setText( f.format( otherValue ) );
            }
        } catch( Exception e ){
            toastIt( "Please enter a number." );
        }
    }

    public void radioButtonClicked( View v ){
        Log.i( "diabetes", "Radio Button Clicked" );
        int selectedId = formulaGroup.getCheckedRadioButtonId();
        Log.i( "diabetes", "selectedId: " + selectedId );
        RadioButton selected = (RadioButton) findViewById( selectedId );
        String stuff = selected.getText().toString();
        Log.i( "diabetes", "stuff: " + stuff );
//        toastIt( stuff );
        useX = rdoX.isChecked();
        Convert( v );
    }

    /*public void logData( View v ){
        startActivity( new Intent( this, BloodSugarLogActivity.class ) );
    }*/

    public void switchViews( View v ){
        Intent extras = new Intent( this, BloodSugarLogActivity.class );
//        A1C = Double.parseDouble( txtA.getText().toString() );
//        eAG = Double.parseDouble( txtB.getText().toString() );
        extras.putExtra( "a1c", txtA.getText().toString() );
        extras.putExtra( "eag", txtB.getText().toString() );
        startActivity( extras );
    }

}
