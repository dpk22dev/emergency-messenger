package org.iiita.project;
 
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class SmsserviceActivity extends Activity 
{
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
 
    String title ;
	String body ;
    String numbers;
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendsms);        
 
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
 
        Bundle extras = getIntent().getExtras(); 
        
    	if (extras != null) {
    	    title = extras.getString("title");
    	    body = extras.getString("body");
    	    // and get whatever type user account id is
    	    numbers = extras.getString("send");
    	    
    	}else{
    		title = "New Emergency Situation:";
    		body = "New Emergency Situation at Location :";
    	}
    	
    	Toast.makeText(getApplicationContext(),title + body
		          , Toast.LENGTH_LONG).show();
        
        txtMessage.setText( body );
        txtPhoneNo.setText( numbers );
        
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                String phoneNo = txtPhoneNo.getText().toString();
                
                String message = txtMessage.getText().toString();                 
                if (phoneNo.length()>0 && message.length()>0){
                	
                    verifyAndSend(phoneNo, message);                
                }
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
        });  
        finish();
    }    

   protected void verifyAndSend(String phoneNo, String message) {
		// TODO Auto-generated method stub
	   StringTokenizer st = new StringTokenizer(phoneNo, " ");
	   while(st.hasMoreTokens()) {
	   String key = st.nextToken();
	   if( isInteger(key) ){
		   sendSMS( key, message );
	   }else{
		   Toast.makeText(getApplicationContext(), "Unable to send sms to " + key
			          , Toast.LENGTH_SHORT).show();
	   }
	   
	   }
	}

private boolean isInteger(String key) {
	// TODO Auto-generated method stub
	try  
    {  
       Integer.parseInt( key );  
       return true;  
    }  
    catch( Exception e)  
    {  
       return false;  
    }  
}

/*
     
        //---sends an SMS message to another device---
        private void sendSMS(String phoneNumber, String message)
        {        
            PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, SmsserviceActivity.class), 0);                
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, pi, null);        
        }    
   /*/ 
  //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        final String p = phoneNumber;
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent to" + p, 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }



}