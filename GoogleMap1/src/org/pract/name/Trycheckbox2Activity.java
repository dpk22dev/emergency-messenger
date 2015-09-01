package org.pract.name;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Trycheckbox2Activity extends Activity {
    /** Called when the activity is first created. */
	
	private CheckBox cbox;
	private ArrayList<CheckBox> al = new ArrayList<CheckBox>();
	
//	public String str[] = {"ram","lakshman","bharat","shatrughan"};
	public ArrayList<String> contacts = new ArrayList<String>();
	private Button Ok;
	private Button cancel;
    public String ret = "";
    private Intent resultIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        
        StringBuilder sb = new StringBuilder();
        String temp = null;
        Integer n;
        
        // aafat
        
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
        if (cur.getCount() > 0) {
	    while (cur.moveToNext()) {
	        String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
	        String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	        sb.append("Name :" + name);
 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
 		    //Query phone here.  Covered next
 				
 			
 			Cursor pCur = cr.query(
 		 		    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
 		 		    null, 
 		 		    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
 		 		    new String[]{id}, null);
 		 	        while (pCur.moveToNext()) {
 		 		    // Do something with phones
 		 	        	
 		 	        	String phoneNumber = pCur.getString(pCur
                                .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        
// 		 	        	Toast.makeText(this, "number :" + phoneNumber
//		 	    		          , Toast.LENGTH_SHORT).show();

 		 	        	 	        	
 		 	        	if( phoneNumber != null ){
 		 	        		StringTokenizer st = new StringTokenizer(phoneNumber,"-");
 		 	        		phoneNumber = "";
 		 	        		while( st.hasMoreTokens()){
 		 	        			phoneNumber += st.nextToken();
 		 	        		}
 		 	        		sb.append("\nPhone: " + phoneNumber + " ");
 		 	        		
//************** try to make workable but change with check box *********
 // ************ number is stored in 123-345-3211 string format how to retreive it ******		 	        		
/* 		 	        	try {
//							n = Integer.parseInt( phoneNumber );
							
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
	 		 	        	Toast.makeText(this, "number format exception ocuured here"
			 	    		          , Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
 */		 	        	
//************** back in case getselected numbers does not work ********* 		 	        	
 		 	        		
 		 	        	}else{
 		 	        		Toast.makeText(this, "null number deleted :"
 			 	    		          , Toast.LENGTH_SHORT).show();
 		 	        		if( sb.length() > 0)
 		 	      			 sb.delete(0, sb.length());
 		 	        	}
 		 	        	
 		 	        } 
 		 	        pCur.close();
 		 		
 		 	if( sb.length() > 0){
 			temp = sb.toString();
 			contacts.add( temp );
 			temp = null;
 			sb.delete(0, sb.length());
 		 	}
 		 	
 	        }else{
 	        	if( sb.length() > 0 )
 	        		sb.delete(0, sb.length());
 	        }
 		
            }
        
        //extra } inserted may be wrong
        }
        
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        for( int i = 0; i < contacts.size(); i++ ){
        
        cbox = ( CheckBox )new CheckBox( this );
        al.add( cbox );
        al.get(i).setText(contacts.get(i));
        layout.addView(al.get(i) , new LinearLayout.LayoutParams( 
        		ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));

/*        if( cbox.isChecked() ){
        	Toast.makeText(getApplicationContext(), "checkbox was checked "
			          , Toast.LENGTH_SHORT).show();
        }
        if( !cbox.isChecked() ){
        	Toast.makeText(getApplicationContext(), "checkbox was unchecked "
			          , Toast.LENGTH_LONG).show();
        }
*/
        cbox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks, depending on whether it's now checked
                if (((CheckBox) v).isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), "Not selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
        
        Ok = new Button(this);
        Ok.setText("Ok");
        layout.addView(Ok, new LinearLayout.LayoutParams( 
        		ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        
        Ok.setOnClickListener(new View.OnClickListener() {
        	
        	
            public void onClick(View v) {
            	String temp, temp1;
            	StringTokenizer st;
            	for( int i = 0; i < contacts.size(); i++ ){
            		if( al.get(i).isChecked() ){
            			
            			temp =  al.get(i).getText().toString();
            			st = new StringTokenizer( temp," ");
            			
            			while( st.hasMoreTokens() ){
            				temp1 = st.nextToken();
            				if( isNumber(temp1) ){
//            					Toast.makeText(getApplicationContext(), "number " + temp1, Toast.LENGTH_SHORT).show();
            					ret += temp1;
            					ret += " ";
            				}
            			}
            			
            		}
            	}
            	
            	sendContacts();
            }
        });
        
        
        /// s*************** toped ************
        cancel = new Button(this);
        cancel.setText("Cancel");
        layout.addView(cancel, new LinearLayout.LayoutParams( 
        		ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });
        
        
        
        setContentView(layout);
    }

	protected void sendContacts() {
		// TODO Auto-generated method stub
		resultIntent = new Intent();
		resultIntent.putExtra("data" , ret );
    	setResult(Activity.RESULT_OK, resultIntent);
    	finish();
		
	}

	protected boolean isNumber(String input) {
		// TODO Auto-generated method stub
		  
	      
	       try  
	       {  
	          Long.parseLong( input );  
	          return true;  
	       }  
	       catch( Exception e)  
	       {  
	          return false;  
	       }  
	    
	}
}
