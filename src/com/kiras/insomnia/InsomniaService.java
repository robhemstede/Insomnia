package com.kiras.insomnia;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

//TODO ACTION_BATTERY_CHANGED

public class InsomniaService extends IntentService {

	public InsomniaService() {
		super("Insomnia");
		// TODO Auto-generated constructor stub
	}

	private NotificationManager mNM;
	private PowerManager mPM;
	private PowerManager.WakeLock wl;	
	private int NOTIFICATION = 1;	
	
	private static final long TIME_HIDDEN = -Long.MAX_VALUE;
	
	 @Override
	 public void onCreate() {
		 Log.w("Insomnia", "onCreate");
		 mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		 mPM = (PowerManager)getSystemService(POWER_SERVICE); 
		 wl = mPM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "InsomniaLock");
		 
		 Intent i = new Intent();
		 i.setClass( this, InsomniaService.class );
		 i.setAction( Intent.ACTION_SHUTDOWN );
		 PendingIntent ip = PendingIntent.getService(this, 0,  i, 0);
		 	 	 
		 Notification.Builder nb = new Notification.Builder(this);
		 nb.setContentTitle("Imsomnia")
		   .setContentText("Click here to allow some sleep!")
		   .setSmallIcon(R.drawable.eye24)
		   .setOngoing(true)
		   .setDeleteIntent(ip)
		   .setContentIntent(ip);
		   
		 
		 Notification n  = nb.getNotification();
		 //n.flags = n.flags | Notification.FLAG_FOREGROUND_SERVICE;
		 mNM.notify(NOTIFICATION,n);
		 wl.acquire();
	 }
	 
	 @Override
	 public void onStart(Intent intent, int startid)
	 {
		 Log.w("SDf", "onStart");
		 if( intent.getAction() == Intent.ACTION_SHUTDOWN ){
			 
			 //Toast t = new Toast(this);
			 //t.setText("Insomnia ended");	
			 //t.show();
			 //startActivity( new Intent(this, MainActivity.class ));	
			 
			 stopSelf();			 
			 return;
		 }			 
	 }	
	 
	 @Override
	 public void onDestroy() {
		 // Cancel the persistent notification.
	     wl.release();
		 mNM.cancel(NOTIFICATION);
	     Log.w( "INS", "onDestroy" );	
	 }	
	 
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.w( "INS", "onHandleIntent" );			
	}

}
