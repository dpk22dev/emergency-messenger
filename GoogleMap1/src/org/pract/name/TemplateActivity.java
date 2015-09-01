package org.pract.name;

import org.pract.name.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TemplateActivity extends ListActivity {
	
	 private NotesDbAdapter mDbHelper;
	 public static String title;
	 public static String body;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.template);
	        
	        mDbHelper = new NotesDbAdapter(this);
	        mDbHelper.open();
	        
	        Button skipButton = ( Button )findViewById(R.id.skip);
	        skipButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
//	                setResult(RESULT_OK);
//	                  finish();
//	            	Toast.makeText(getApplicationContext(), "Skipping!"
//					          , Toast.LENGTH_SHORT).show();
	            	// do experimenting
	            	skip();
	            }
	        });
	        
	        Button backButton = ( Button )findViewById(R.id.back);
	        backButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {

//	            	Toast.makeText(getApplicationContext(), "Going back!"
//					          , Toast.LENGTH_SHORT).show();
	            	// do experimenting
	            	setResult(RESULT_OK);
	                  finish();	            	
	            }
	        });
	        
	        
	        filldata();
	        
	 }

	protected void skip() {
		// TODO Auto-generated method stub
		
		// call activity to get contact numbers
		Intent i = new Intent( this, SelectActivity.class);
        startActivity(i);
		
	}

	private void filldata() {
		// TODO Auto-generated method stub
		Cursor notesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{NotesDbAdapter.KEY_TITLE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.notes_row, notesCursor, from, to);
        setListAdapter(notes);

	}
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
// ************ get the string body from templates data base it can be used for message body .....
        
        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();    
        Cursor note = mDbHelper.fetchNote( id );
        startManagingCursor( note );
        title = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE));
        body = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY));
        
//        Toast.makeText(this, "title : " + title
//		          , Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "body : " + body
//		          , Toast.LENGTH_LONG).show();
        
        mDbHelper.close();
// **************see if u can get title and body in selectactivity *********                         
        Intent i = new Intent(this, SelectActivity.class);
        i.putExtra("title", title);
        i.putExtra("body", body);
        startActivity(i);
        
    }

}
