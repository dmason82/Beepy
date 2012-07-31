package com.mason.doug.beepy;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
/**
 * 
 * @author Doug Mason
 */
public class BeepService extends Service {
	private NotificationManager manager;
	BeepTask beepy;
	private class BeepTask extends AsyncTask<Void,Void,Void>{

		@Override
		//We shall beep every 5 seconds using a basic tonegenerator, until we call cancel on the AsyncTask via the Services onDestroy() method
		protected Void doInBackground(Void... arg0) {
			try{
				while(true){
					ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC,ToneGenerator.MAX_VOLUME);
					tg.startTone(ToneGenerator.TONE_DTMF_3, 1000);
					Thread.sleep(5000);
				}

				}
			catch(Exception e)
			{
				Log.d("Exception raised",e.toString());
			}
			return null;
		}
		
	}
	public int onStartCommand(Intent intent, int flags, int startId) {
		beepy = new BeepTask();
		beepy.execute();
		showNotification();
	    return START_STICKY;
	}
	Messenger messenger;
	public void onCreate()
	{
		manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		showNotification();
	}
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	public void onDestroy(){
		manager.cancel(R.string.started);
		beepy.cancel(true);
	}
	private void showNotification()
	{
		        CharSequence description = getText(R.string.started);
		        Notification notification = new Notification(R.drawable.ic_launcher, description,
		                System.currentTimeMillis());
		        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		                new Intent(this, BeepyActivity.class), 0);
		        // Set the info for the views that show in the notification panel.
		        notification.setLatestEventInfo(this, getText(R.string.service_label),
		                       description, contentIntent);

		        // Send the notification.
		        // We use a string id because it is a unique number.  We use it later to cancel.
		        manager.notify(R.string.started, notification);
	}
}
