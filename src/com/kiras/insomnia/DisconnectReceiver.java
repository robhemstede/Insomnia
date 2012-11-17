package com.kiras.insomnia;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DisconnectReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Log.w("Insomnia","onReceive");	
		
		//When the service is running ask the user 
		boolean bIsRunning = false;
		
		ActivityManager manager = (ActivityManager) context.getSystemService( android.content.Context.ACTIVITY_SERVICE );
		List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);
		
        for( RunningServiceInfo service : services ) {
            
        	if( InsomniaService.class.getName().equals(service.service.getClassName())) {  
        		bIsRunning = true;
            }
        }		 
		
        if( bIsRunning ){
        	Intent i = new  Intent(context, MainActivity.class ); 
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	context.startActivity( i );	
        }
	}
}
