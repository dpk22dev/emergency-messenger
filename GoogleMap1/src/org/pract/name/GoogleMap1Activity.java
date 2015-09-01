package org.pract.name;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.Toast;

import java.util.*;

import org.pract.name.R;
import org.pract.name.R.drawable;
import org.pract.name.R.id;
import org.pract.name.R.layout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;

public class GoogleMap1Activity extends MapActivity {
    /** Called when the activity is first created. */
   MapView mapView;
   MapController mc;
   GeoPoint p;
private Button useButton;
private Button cancelButton;
private Intent resultIntent;
public String PUBLIC_STATIC_STRING_IDENTIFIER;
public double x = 25.430524,y = 81.772267;


   class MapOverlay extends com.google.android.maps.Overlay
   {
       @Override
       public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
       {
           super.draw(canvas, mapView, shadow);                   

           //---translate the GeoPoint to screen pixels---
           Point screenPts = new Point();
           mapView.getProjection().toPixels(p, screenPts);

           //---add the marker---
           Bitmap bmp = BitmapFactory.decodeResource(
               getResources(), R.drawable.pushpin);            
           canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
           return true;

       }
 
       @Override
       public boolean onTouchEvent(MotionEvent event, MapView mapView) 
       {   
           //---when user lifts his finger---
           if (event.getAction() == 1) {                
               GeoPoint p = mapView.getProjection().fromPixels(
                   (int) event.getX(),
                   (int) event.getY());
               
                   Toast.makeText(getBaseContext(), 
                       p.getLatitudeE6() / 1E6 + "," + 
                       p.getLongitudeE6() /1E6 , 
                       Toast.LENGTH_SHORT).show();
                   
                   x = p.getLatitudeE6() / 1E6;
                   y = p.getLongitudeE6() /1E6;
           }                            
           return false;
           }
   } 
   
   public boolean onKeyDown(int keyCode, KeyEvent event) 
   {
       mc = mapView.getController(); 
       switch (keyCode) 
       {
           case KeyEvent.KEYCODE_3:
               mc.zoomIn();
               break;
           case KeyEvent.KEYCODE_1:
               mc.zoomOut();
               break;
       }
       return super.onKeyDown(keyCode, event);
   } 
   
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        mc = mapView.getController();
        String coordinates[] = {"25.430524", "81.772267"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        useButton = (Button) findViewById(R.id.location);
        cancelButton = (Button) findViewById(R.id.mapcancel);
        
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(17);
        
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        
 
        useButton.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                sendLocation();
            }
        });   
        
        cancelButton.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                finish();
            }
        });   
        
        mapView.invalidate();
    }
    
    protected void sendLocation() {
		// TODO Auto-generated method stub
    	resultIntent = new Intent();
		String enteredTextValue = "GPS Location : ( ";
		enteredTextValue += x;
		enteredTextValue += " , ";
		enteredTextValue += y;
		enteredTextValue += " ) ";
		
		resultIntent.putExtra("data" , enteredTextValue );
    	setResult(Activity.RESULT_OK, resultIntent);
    	finish();

	}

	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
}