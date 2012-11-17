package com.kiras.insomnia;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;



public class MainActivity extends Activity {
	
	final String TAG = "Imsomina"; 
	
	private Intent      m_intent;
	private Handler     m_handler = new Handler();
	private AlertDialog m_alertDialog;
	
	private Runnable m_timeout = new Runnable() {
		   public void run() {
			   if( m_alertDialog != null ){
				   m_alertDialog.dismiss();
				   
				   if( m_intent != null ){
					   m_intent.setAction( Intent.ACTION_SHUTDOWN );
					   startService( m_intent );
				   }
				   				   
				   MainActivity.this.finish();
			   }
		   }
	};	
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        Log.w(TAG, "onCreate" ); 
        
        final boolean bRunning = isMyServiceRunning(); 
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        
        String strMessage;
        if(bRunning)
        	strMessage = "Continue Insomnia?";
         else
        	strMessage = "Start Insomnia?"; 
        
        m_intent = new Intent( this, InsomniaService.class );
        
        alertDialogBuilder
        	.setMessage(strMessage)
        	.setCancelable(false)
        	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					m_alertDialog = null;
					if( !bRunning )
					{
						//Start the service	
						m_intent.setAction( Intent.ACTION_MAIN );
						startService( m_intent );						
					}					
					MainActivity.this.finish();
				}
        	})
        	.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					if( bRunning )
					{
						m_intent.setAction( Intent.ACTION_SHUTDOWN );
						startService( m_intent );												
					}
					MainActivity.this.finish();
				}
        	});    
        
         
        m_alertDialog = alertDialogBuilder.create();
        if(bRunning){
            m_handler.postDelayed(m_timeout, 10000);    
        }
    
        m_alertDialog.show();
				
        
        //Intent i = new Intent(this, InsomniaService.class ); 
        //startService( i ); 
		
		       
        //finish();
        //setContentView(R.layout.activity_main);
    }
    
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
 */
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.w( TAG, service.service.getClassName() ); 
        	if("com.kiras.insomnia.InsomniaService".equals(service.service.getClassName())) {  
            	Log.w(TAG, "Service found" );
            	return true;
            }
        }
        Log.w(TAG, "No service found" ); 
        return false;
    }
}
