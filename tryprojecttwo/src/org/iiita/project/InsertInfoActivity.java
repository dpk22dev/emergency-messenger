package org.iiita.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertInfoActivity extends Activity {
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
        	
       
        
/*        spinner.setOnItemSelectedListener( new  AdapterView.OnItemSelectedListener(){

        	@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
        		type = parent.getItemAtPosition(pos).toString();
//				 Toast.makeText(parent.getContext(), "you selected : " + type
//				          , Toast.LENGTH_LONG).show();
        		
			
			}
*/
//			@Override
			/*public void onNothingSelected(AdapterView<?> arg0){
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "please select something "
				          , Toast.LENGTH_LONG).show();
				
//				mBodyText = (EditText) findViewById(R.id.entry);
        		// *******************temp is null ***********************         
//        		        temp = mBodyText.getText().toString();	
				
			}
			
			
			
        });
        */
// 		********************************* source of error can't convert string to int        
//        mBodyText = (EditText) findViewById(R.id.entry);
// *******************temp is null ***********************         
//        temp = mBodyText.getText().toString();
//        temp = mBodyText.getEditableText().toString();
        
/*        Toast.makeText(getApplicationContext(), "temp : " + temp
		          , Toast.LENGTH_LONG).show();
        
        System.out.println("number entered is :" + temp);
        try{
        	number = Integer.parseInt( temp );
        	System.out.println("number entered is :" + number);
        	Toast.makeText(getApplicationContext(), "number : " + number
			          , Toast.LENGTH_LONG).show();
        }catch(NumberFormatException ex ){
        	System.out.println("enter valid string for numbers using 9999 instead");
        	number = 9999;
        }
  */      
        
        Button entryButton = ( Button )findViewById(R.id.addnumber);
        entryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                setResult(RESULT_OK);
//                  finish();
            	
            	insert_number();
            	mBodyText = (EditText) findViewById(R.id.entry);
            	mBodyText.setText("");
/*            	
            	mBodyText = (EditText) findViewById(R.id.entry);
        		// *******************temp is null ***********************         
        		        temp = mBodyText.getText().toString();
        		        
        		        System.out.println("number entered is :" + temp);
        		        try{
        		        	number = Integer.parseInt( temp );
        		        	System.out.println("number entered is :" + number);
        		        	Toast.makeText(getApplicationContext(), "number : " + number
        					          , Toast.LENGTH_LONG).show();
        		        }catch(NumberFormatException ex ){
        		        	System.out.println("enter valid string for numbers using 9999 instead");
        		        	number = 9999;
        		        }
        		        
            	
            	Toast.makeText(getApplicationContext(), "type :" + type + "number" + number
				          , Toast.LENGTH_SHORT).show();
            	
            	long id = mDbHelper.createNote(type, number);
            	
            	Toast.makeText(getApplicationContext(), "Entry Button clicked! id:" + id
				          , Toast.LENGTH_SHORT).show();
            	
            	mDbHelper.close();
*/            	
            	setResult(RESULT_OK);
// originally finish here
//            	finish();
            }
            
        });
        
        Button backButton = ( Button )findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                setResult(RESULT_OK);
//                  finish();
            	Toast.makeText(getApplicationContext(), "Exiting!"
				          , Toast.LENGTH_SHORT).show();
            	
//            	mDbHelper.close();
            	setResult(RESULT_OK);
            	finish();
            }

        });

        
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
		        	Toast.makeText(getApplicationContext(), "number : " + number
					          , Toast.LENGTH_LONG).show();
		        }catch(NumberFormatException ex ){
		        	System.out.println("enter valid string for numbers using 9999 instead");
		        	number = 9999;
		        }
		        
    	
    	Toast.makeText(getApplicationContext(), "type :" + type + "number" + number
		          , Toast.LENGTH_SHORT).show();
    	
    	long id = mDbHelper.createNote(type, number);
    	
    	Toast.makeText(getApplicationContext(), "Entry Button clicked! id:" + id
		          , Toast.LENGTH_SHORT).show();
    	
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