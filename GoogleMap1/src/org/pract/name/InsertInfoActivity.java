package org.pract.name;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertInfoActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	private EditText mBodyText;
	private String type, temp = "";
	public Integer number;
	private WorkingInfoAdapter mDbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        
        mDbHelper = new WorkingInfoAdapter(this);
//        mDbHelper.open();
        
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        	
        fillData();
        
        spinner.setOnItemSelectedListener( new  AdapterView.OnItemSelectedListener(){

        	public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
        		type = parent.getItemAtPosition(pos).toString();
//				 Toast.makeText(parent.getContext(), "you selected : " + type
//				          , Toast.LENGTH_LONG).show();
        		
			
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Select to add Number "
				          , Toast.LENGTH_SHORT).show();
				
				
			}
			
			
			
        });
// 		        
        Button entryButton = ( Button )findViewById(R.id.addnumber);
        entryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                setResult(RESULT_OK);
//                  finish();
            	
            	insert_number();
            	fillData();
            	mBodyText = (EditText) findViewById(R.id.entry);
            	mBodyText.setText("");
          	
            	setResult(RESULT_OK);
            	

//            	finish();
            }
            
        });
        
        Button backButton = ( Button )findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                setResult(RESULT_OK);
//                  finish();
//            	Toast.makeText(getApplicationContext(), "Exiting!"
//				          , Toast.LENGTH_SHORT).show();
            	
//            	mDbHelper.close();
            	setResult(RESULT_OK);
            	finish();
            }

        });

        
    }
    
    private void fillData() {
    	
    	mDbHelper.open();
    	
        Cursor notesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{WorkingInfoAdapter.KEY_NUMBER};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.insertrow, notesCursor, from, to);
        setListAdapter(notes);
        mDbHelper.close();
    }

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        final long temppos = id;
        AlertDialog.Builder alt_bld = new AlertDialog.Builder( this );
		
		alt_bld.setMessage("Do you Want to delete this number")
//*********************** changed false to true *******************		
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//  Action for 'Yes' Button
/*
 	send sms lalaji's activities
*/
				delete( temppos );
				
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
			alert.setTitle("... Delete Number ...");
			// Icon for AlertDialog
			alert.setIcon(R.drawable.icon);
			alert.show();

        
//        mDbHelper.open();
//        if( mDbHelper.deleteNote( id ) ){
//        	Toast.makeText(getApplicationContext(), "item deleted with id " + id
//  		          , Toast.LENGTH_SHORT).show();
//        }
//        mDbHelper.close();
        fillData();
        	
    }

	
	protected void delete(long id) {
		// TODO Auto-generated method stub
		mDbHelper.open();
        if( mDbHelper.deleteNote( id ) ){
        	Toast.makeText(getApplicationContext(), "Deletion complete"
  		          , Toast.LENGTH_SHORT).show();
        }
        mDbHelper.close();
     
	}

	protected void insert_number() {
		// TODO Auto-generated method stub
		
		mDbHelper.open();
		mBodyText = (EditText) findViewById(R.id.entry);
		// *******************temp is null ***********************         
		        temp = mBodyText.getText().toString();
		        
		        if( isInteger(temp) == false ){
		        Toast.makeText(getApplicationContext(), " please enter a valid number"
				          , Toast.LENGTH_SHORT).show();
		        mDbHelper.close();
		        return ;
		        }
		        
		        System.out.println("number entered is :" + temp);
		        try{
		        	number = Integer.parseInt( temp );
		        	System.out.println("number entered is :" + number);
//		        	Toast.makeText(getApplicationContext(), "number : " + number
//					          , Toast.LENGTH_SHORT).show();
		        }catch(NumberFormatException ex ){
		        	System.out.println("enter valid string for numbers using 9999 instead");
		        	number = 9999;
		        }
		        
    	
//    	Toast.makeText(getApplicationContext(), "type :" + type + "number" + number
//		          , Toast.LENGTH_SHORT).show();
    	
    	long id = mDbHelper.createNote(type, number);
    	
//    	Toast.makeText(getApplicationContext(), "Entry Button clicked! id:" + id
//		          , Toast.LENGTH_SHORT).show();
    	
    	mDbHelper.close();
		
	}
	
    public boolean isInteger( String input )  
    {  
       try  
       {  
          Integer.parseInt( input );  
          return true;  
       }  
       catch( Exception e)  
       {  
          return false;  
       }  
    }  
}