package com.example.diabetes;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by markproaudio on 1/25/16.
 */
public class BaseActivity extends Activity{


    public void toastIt(String message){
        Toast.makeText( getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
    }
}
