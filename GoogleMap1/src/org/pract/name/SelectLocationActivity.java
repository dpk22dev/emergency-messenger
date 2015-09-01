package org.pract.name;

import java.io.IOException;

import org.pract.name.R;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class SelectLocationActivity extends Activity {
	
	 SQLiteDatabase sampleDB = null;
	 //private WorkingInfoAdapter workingInfoHelper = new WorkingInfoAdapter( this );
	 
	private static final String SAMPLE_DB_NAME = "newcountriesinfo";
	private static final String SAMPLE_TABLE_NAME = "contacts";
	private DataBaseHelper mDbHelper;
	private AutoCompleteTextView acTextView; 
	private String cName;
	private int numPolice;
	private int numFire;
	private int numAmbulance;
	private SQLiteOpenHelper sqlHelper;
	private Cursor c;
	private WorkingInfoAdapter wHelper;
	
	String title = "Change Location";
	String mymessage = "Are you sure to change your Location ?";
	String data;
	
	String[] countries = 
	{
		"India",
		"China",
		"Israel",
		"Japan",
		"Malaysia",
		"Pakistan"
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlocationactivity);
        setTitle(R.string.select_location);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries);
        acTextView = (AutoCompleteTextView)findViewById(R.id.countries_view);
        acTextView.setThreshold(3);
        acTextView.setAdapter(adapter);
        
//************************* data could not be got ************
        
//        data = acTextView.getText().toString().toLowerCase();
        //data = acTextView.getText().toString().toLowerCase();
        
//        Toast.makeText(this, "data : " + data
//		          , Toast.LENGTH_SHORT).show();
        
        final Context context = this;
        
        try {
            Button okButton = (Button)findViewById(R.id.countries_button);
            okButton.setOnClickListener(new OnClickListener() {
            	
            	public void onClick(View v){
            		
            		data = acTextView.getText().toString().toLowerCase();
            		
            		AlertDialog.Builder alt_bld = new AlertDialog.Builder( context );
            		
            		alt_bld.setMessage("Are you sure to change your Location ?")
            		.setCancelable(false)
            		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            		public void onClick(DialogInterface dialog, int id) {
            		// Action for 'Yes' Button
            			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            			//add code here to change location
            			//delete from previous working database
            			//take from countries database
            			//insert into working database
            			
            				selectLocation();
//************************** after getting data delete the working database and insert new values 
            			
            		}

					private void selectLocation() {
						// TODO Auto-generated method stub
						
						mDbHelper = new DataBaseHelper(context);
            		
				        try {
							mDbHelper.createDataBase();
//							Toast.makeText(context, "database created "
//	        				          , Toast.LENGTH_SHORT).show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//********************** try to print ***********
						
						try {
							String results;
//							mDbHelper.getReadableDatabase(); // (SAMPLE_DB_NAME, MODE_PRIVATE, null);
				        	sampleDB =  mDbHelper.openDataBase();

//************************** try data = india instead ************
				     
				        	
//				        	Toast.makeText(context, "data : " + data
//	        				          , Toast.LENGTH_SHORT).show();
				        	
/*				        	c = sampleDB.rawQuery("SELECT countryname, police, fire, ambulance FROM " +
				        			SAMPLE_TABLE_NAME +
				        			" where countryname" + " = " + data, null);
*/
				        	data = "'" + data + "'";
				        	c = sampleDB.query(SAMPLE_TABLE_NAME, new String[] {"countryname", "police","fire",
                "ambulance"}, "countryname = " + data , null, null, null, null, null);
				        	
				        	if (c != null ){ 
//				        		Toast.makeText(context, "cursor is not null "
//		        				          , Toast.LENGTH_LONG).show();
					}else{
				        		Toast.makeText(context, "cursor is null "
		        				          , Toast.LENGTH_LONG).show();
				        	}
				        	
				        	if (c != null ) {
				        		if  (c.moveToFirst()) {
				        			do {
				        				
				        				cName = c.getString(c.getColumnIndex("countryname"));
				        				numPolice = c.getInt(c.getColumnIndex("police"));
				        				numFire = c.getInt(c.getColumnIndex("fire"));
				        				numAmbulance = c.getInt(c.getColumnIndex("ambulance"));
				        				
				        				results = cName + numPolice + numFire + numAmbulance;
//				        				results.add(" " + cName + ",Police: " + numPolice);
				        				
//				        				Toast.makeText(context, "result : " + results
//				        				          , Toast.LENGTH_LONG).show();
				        				results = "";
				        			}while (c.moveToNext());
				        		} 
				        	}else{
				        		Toast.makeText(context, "Couldn't get data from database... sorry! "
		        				          , Toast.LENGTH_LONG).show();
				        	}
				        		
			        	
				        	wHelper = new WorkingInfoAdapter( context );
				        	wHelper.open();
				        	wHelper.deleteAllNotes();
				        		
				        		wHelper.createNote("Police", numPolice);
				        		wHelper.createNote("FireBrigade", numFire);
				        		wHelper.createNote("Ambulance", numAmbulance);
				        		Toast.makeText(context, "Numbers for new Location has been inserted in database "
		        				          , Toast.LENGTH_SHORT).show();
/*				        	}else{
				        		System.out.println("error in opening database");
				        		Toast.makeText(context, " sorry! Error in opening database WorkingInfo"
		        				          , Toast.LENGTH_LONG).show();
				        	}
*/				        	
				        	wHelper.close();
				        	finish();
				        	
			        }catch(SQLiteException se){
			        	Log.e(getClass().getSimpleName(), "Could not create or Open or read the database");
			        }finally {
//			        	if (sampleDB != null) 
//			    		sampleDB.execSQL("DELETE FROM " + SAMPLE_TABLE_NAME);
			        	sampleDB.close();
			    }

						
						
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
            			alert.setTitle("Change Location");
            			// Icon for AlertDialog
            			alert.setIcon(R.drawable.icon);
            			alert.show();
            			
            	}
            });
        // try below
        }catch (ActivityNotFoundException anfe) {
        	Log.e("onCreate", " Not Found", anfe);
        }// catch
   }// fun
}// class
