package com.example.diabetes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by markproaudio on 1/27/16.
 */
public class BloodSugarLogActivity extends BaseActivity{
    Button btnReturn;
    /**
     * Called when the activity is first created.
     */

    TextView txtDate;
    TextView txtTime;
    TextView txtA1c;
    TextView txtEag;
    private Calendar c = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ){
            c.set( Calendar.YEAR, year );
            c.set( Calendar.MONTH, monthOfYear );
            c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
            setCurrentDateOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ){
            c.set( Calendar.HOUR, hour );
            c.set( Calendar.MINUTE, minute );
            setCurrentDateOnView();
        }
    };

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.blood_sugar_log );

        txtDate = (TextView) findViewById( R.id.txtDate );
        txtTime = (TextView) findViewById( R.id.txtTime );
        txtA1c = (TextView) findViewById( R.id.txtA1c );
        txtEag = (TextView) findViewById( R.id.txtEag );
        getIntentData();
        setCurrentDateOnView();
    }

    public void saveData( View v ){
        String logA1c = txtA1c.getText().toString();
        String logEag = txtEag.getText().toString();
        String saveMe = logA1c + "," + logEag + "\n";
        Log.i( "Blood Sugar", saveMe );
        try{
            FileOutputStream out = openFileOutput( "bloodSugar.txt", Context.MODE_APPEND );
            out.write( saveMe.getBytes() );
            out.close();
            toastIt( "Entry saved" );
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    private void setCurrentDateOnView(){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
        txtDate.setText( dateFormat.format( c.getTime() ) );
        SimpleDateFormat timeFormat = new SimpleDateFormat( "h:mm a", Locale.US );
        txtTime.setText( timeFormat.format( c.getTime() ) );
    }

    private void getIntentData(){
        Bundle extras = getIntent().getExtras();
        if( extras != null ){
            String a1c = extras.getString( "a1c" );
            String eag = extras.getString( "eag" );
            txtA1c.setText( a1c );
            txtEag.setText( eag );
            /*DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
            Date date = new Date();
            txtDate.setText( date.toString() );*/
        }
    }

    public void dateOnClick( View v ){
        new DatePickerDialog( this, date, c.get( Calendar.YEAR ), c.get( Calendar.MONTH ), c.get( Calendar.DAY_OF_MONTH ) ).show();

    }

    public void timeOnClick( View v ){
        new TimePickerDialog( this, time, c.get( Calendar.HOUR ), c.get( Calendar.MINUTE ), false ).show();
    }

    //We need this to update the values when going back and forth from the other view.
    @Override
    protected void onStop(){
        super.onStop();
        getIntentData();
    }

    public void returnToCalc( View v ){
        startActivity( new Intent( this, MyActivity.class ) );
    }
}
