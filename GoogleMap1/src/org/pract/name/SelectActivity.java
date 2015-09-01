package org.pract.name;

import java.util.ArrayList;

import org.pract.name.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SelectActivity extends Activity {
    /** Called when the activity is first created. */
	
	private WorkingInfoAdapter mDbHelper;
	private int tempnum;
	private int count = 0;
	private int numbers[];		/// ******* may be cause of error *********
	CheckBox policeCheckbox;
	CheckBox fireCheckbox;
	CheckBox ambulanceCheckbox;
	public String title = "No title";
	public String body = "No Body";
	public String send="";
	private static final int GET_CONTACTS = 1;
	
//	public static ArrayList<Integer> al = new ArrayList<Integer>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        
        
        
//        ArrayList<Integer> numbers= new ArrayList<Integer>();
        
        
        Button backButton = (Button)findViewById(R.id.back);
        Button otherButton = (Button)findViewById(R.id.choose);
        Button sendButton = (Button)findViewById(R.id.send);
        
         policeCheckbox = (CheckBox) findViewById(R.id.check1);
         fireCheckbox = (CheckBox) findViewById(R.id.check2);
         ambulanceCheckbox = (CheckBox) findViewById(R.id.check3);
///************************* check code *******************        
/*        if( policeCheckbox.isChecked() ){
        	
        	Toast.makeText(this, "police clicked "
			          , Toast.LENGTH_LONG).show();
        	
        	getInfo( 0 );
        	
        	Toast.makeText(this, "done after police checking clicked " 
			          , Toast.LENGTH_LONG).show();
        	
        }
       
        if(  fireCheckbox.isChecked() ){
        
        	Toast.makeText(this, "fire clicked "
			          , Toast.LENGTH_LONG).show();
        	
        	getInfo( 1 );
        	
        	Toast.makeText(this, "done after fire clicked "
			          , Toast.LENGTH_LONG).show();
        	
        }
        
        if( ! ambulanceCheckbox.isChecked() ){
        	
        	Toast.makeText(this, "ambulance clicked "
			          , Toast.LENGTH_LONG).show();
        	
        	getInfo( 2 );
        	
        	Toast.makeText(this, "after ambulance clicked "
			          , Toast.LENGTH_LONG).show();
        }
*/        	
//*************************************************************************        
        
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	finish();
            }

        });
        
        sendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
            	if( policeCheckbox.isChecked() ){
                	
//                	Toast.makeText(getApplicationContext(), "police clicked "
//        			          , Toast.LENGTH_LONG).show();
                	
                	getInfo( 0 );
                	
//                	Toast.makeText(getApplicationContext(), "done after police checking clicked " 
//        			          , Toast.LENGTH_LONG).show();
                	
                }
            	
            	if(  fireCheckbox.isChecked() ){
                    
//                	Toast.makeText(getApplicationContext(), "fire clicked "
//        			          , Toast.LENGTH_LONG).show();
                	
                	getInfo( 1 );
                	
//                	Toast.makeText(getApplicationContext(), "done after fire clicked "
//        			          , Toast.LENGTH_LONG).show();
                	
                }
            	
            	if( ambulanceCheckbox.isChecked() ){
                	
//              	Toast.makeText(getApplicationContext(), "ambulance clicked "
//        			          , Toast.LENGTH_LONG).show();
                	
                	getInfo( 2 );
                	
//                	Toast.makeText( getApplicationContext(), "after ambulance clicked "
//        			          , Toast.LENGTH_LONG).show();
                }
            	
            	send();
            }

        });
        
        otherButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	choose_contacts();
            }

        });



        
    }

	private void getInfo(int i) {
		// TODO Auto-generated method stub
		String s;
		mDbHelper = new WorkingInfoAdapter( this );
		mDbHelper.open();
		Cursor c;
/// get police ambulance or fire numbers		
		
//		Toast.makeText(this, "trying to get info from workingInfo "
//		          , Toast.LENGTH_LONG).show();
		
			if( i == 0 )
				 c = mDbHelper.fetchNoteByType("'Police'"); /// error no such coloumn police
			else if( i == 1 )
				 c = mDbHelper.fetchNoteByType("'FireBrigade'");
			else 
				 c = mDbHelper.fetchNoteByType("'Ambulance'");
				
			if( c != null ){
//				Toast.makeText(this, "cursor is not null"
//			          , Toast.LENGTH_LONG).show();
				System.out.println("cursor is not null");
			}
			else{
				Toast.makeText(this, "cursor is null "
				          , Toast.LENGTH_LONG).show();
				System.out.println("cursor is null....sorry");
			}
				
			
				startManagingCursor( c );
//		        title = c.getString(c.getColumnIndexOrThrow(WorkingInfoAdapter.KEY_TITLE));
				if( c != null ){
					if( c.moveToFirst() ){
						do{
				s = c.getString(c.getColumnIndexOrThrow(WorkingInfoAdapter.KEY_NUMBER));
				if( s != null ){
//					tempnum = Integer.parseInt( s );
//		        	al.add(tempnum);
					
					send += s; 
					send += " ";
					
//		        	Toast.makeText(this, "number : " + s
//				          , Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(this, "Sorry... Error Occured in getting data " 
					          , Toast.LENGTH_SHORT).show();
					System.out.println("string become null .. sorry");
					break;
				}
						}while( c.moveToNext() );
					}
				}
		
		mDbHelper.close();
	}

	protected void choose_contacts() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Trycheckbox2Activity.class);
		startActivityForResult(i,GET_CONTACTS );
	}

	@Override
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     switch(requestCode) {
	       case ( GET_CONTACTS ) : {
	         if (resultCode == Activity.RESULT_OK) {
	           // TODO Extract the data returned from the child Activity.
	        	 String newText = data.getStringExtra("data");
	        	 
	        	 send += " ";
	        	 send += newText;
// code remain6in6g*****************************
//	        	 Toast.makeText(getApplicationContext(),"contacts" + send
//		   		          , Toast.LENGTH_LONG).show();
	         }
	         break;
	       } 
	     }
	   }

	
	protected void send() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder( this );
		
		alt_bld.setMessage("Are you sure to Send Location to selected People?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//  Action for 'Yes' Button
/*
 	send sms lalaji's activities
*/
//				Toast.makeText(getApplicationContext(), "sending.... "
//				          , Toast.LENGTH_LONG).show();
				
				sendSMS();
				}
				})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			//  Action for 'NO' Button
			// cancel the dialog and do nothing
			dialog.cancel();
			}
			});
		
			AlertDialog alert = alt_bld.create();
			// Title for AlertDialog
			alert.setTitle("... Send Location Info ...");
			// Icon for AlertDialog
			alert.setIcon(R.drawable.icon);
			alert.show();

	}

	protected void sendSMS() {
		// TODO Auto-generated method stub
		
		Bundle extras = getIntent().getExtras(); 
		
		
		if (extras != null) {
		    title = extras.getString("title");
		    body = extras.getString("body");
		    // and get whatever type user account id is
		}else{
			title = "Emergency :";
			body = "Help !!! Emergency Situtation here";
		}

//		Toast.makeText(getApplicationContext(),title + body
//		          , Toast.LENGTH_LONG).show();
		
		Intent i = new Intent(this, SmsserviceActivity.class);
		i.putExtra( "title",title);
		i.putExtra("body", body);
		i.putExtra("send", send);

		startActivity(i);
		
	}
}