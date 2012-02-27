package aad.uw.CP210.hw004;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

public class BeepService extends IntentService {

	Messenger messenger;
	
	public BeepService() {
		super("BeepService");
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.v("LOG_DEBUG","Intent handled by service");

		try {
			ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC,ToneGenerator.MAX_VOLUME);
			tone.startTone(ToneGenerator.TONE_DTMF_C,1000);
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.sendNotification(this);

	}

}
