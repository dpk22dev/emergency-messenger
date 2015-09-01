package org.iiita.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TryprojecttwoActivity extends Activity {
    /** Called when the activity is first created. */
	
//	private static final int NOTEPAD_ACTIVITY_CREATE=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        Button editButton = (Button) findViewById(R.id.editTemplate);
        
        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	edit();
            }

        });

        Button editNumberButton = (Button) findViewById(R.id.editNumbers);
        
        editNumberButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	editNumber();
            }

        });

        Button defaultNumberButton = (Button) findViewById(R.id.defaultNumbers);
        
        defaultNumberButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	editDefaultNumber();
            }

        });

        Button emergencyButton = (Button) findViewById(R.id.emergency);
        
        emergencyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	emergency();
            }

        });

    }

	protected void emergency() {
		// TODO Auto-generated method stub
		Intent i = new Intent( this, TemplateActivity.class);
        startActivity(i);
	}

	protected void editDefaultNumber() {
		// TODO Auto-generated method stub
		Intent i = new Intent( this, SelectLocationActivity.class);
        startActivity(i);
	}

	protected void editNumber() {
		// TODO Auto-generated method stub
		Intent i = new Intent( this, InsertInfoActivity.class);
        startActivity(i);
	}

	protected void edit() {
		// TODO Auto-generated method stub
		Intent i = new Intent( this, Notepadv3.class);
        startActivity(i);
	}
}