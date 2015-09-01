package org.iiita.project;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewCheckboxActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	private CheckBoxifiedTextListAdapter cbla;
	// Create CheckBox List Adapter, cbla
	ArrayList<String> contacts = new ArrayList<String>();
//	private String[] contacts = {"initialised","abc","","","","","",""};
	// Array of string we want to display in our list
	public int i = 0;
	

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.newcheckbox);
        
        StringBuilder sb = new StringBuilder();
        String temp = null;
        Integer n;
        
        Button cancelButton = (Button) findViewById(R.id.cancel);
        
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
// deselect all the selected ones may be required.....            	
            	finish();
            }

        });

        Button okButton = (Button) findViewById(R.id.ok);
        
        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	getSelectedNumbers();
            }
         

        });

        
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
 		 	        		sb.append("\nPhone: " + phoneNumber + "\n");
 		 	        		
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
 	}
        
        cbla = new CheckBoxifiedTextListAdapter(this);
        for(int k=0; k<contacts.size(); k++)
        {
        	cbla.addItem(new CheckBoxifiedText(contacts.get(k), false));
        }  
        // Display it
        setListAdapter(cbla);
        
        cbla.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	getSelectedNumbers();
            }
         

        });
        	
    }
    
    
	protected void getSelectedNumbers() {
		// TODO Auto-generated method stub
/// ************** get the selected numbers *************	not working find it ******
		
		
		
		String str;
		for( int j = 0; j < contacts.size(); j++ ){
			if(  cbla.getChecked(j) ){
				str = ((CheckBoxifiedText) cbla.getItem(j)).getText();
				Toast.makeText(this, " number is  " + str
	    		          , Toast.LENGTH_SHORT).show();	    		          
			}
		}
//		finish();
	}
    
    /* Remember the other methods of the CheckBoxifiedTextListAdapter class!!!!
     * cbla.selectAll() :: Check all items in list
     * cbla.deselectAll() :: Uncheck all items
     */
}