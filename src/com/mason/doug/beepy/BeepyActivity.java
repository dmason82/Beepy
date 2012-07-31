package com.mason.doug.beepy;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BeepyActivity extends Activity implements OnClickListener {
	Intent i;;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b =(Button) findViewById(R.id.button1);
        b.setOnClickListener(this);
       i = new Intent(this,BeepService.class);
      // i.putExtra("handler",new Messenger(handler));
    }

	@Override
	public void onClick(View v) {
		Button b =(Button) findViewById(R.id.button1);
		switch(v.getId()){
		case(R.id.button1):
			if(serviceRunning()){
				b.setText("Start");
				Toast t = Toast.makeText(getBaseContext(), "stopping", Toast.LENGTH_SHORT);
				t.show();
				stopService(i);
			}
			else
			{
				b.setText("Stop");
				Toast t = Toast.makeText(getBaseContext(),"starting",Toast.LENGTH_SHORT);
				t.show();
				startService(i);
			}
			break;
		}	
	}
	//returns as to whether or not our beep service is running.
	private boolean serviceRunning(){
		ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			if("aad.uw.CP210.hw004.BeepService".equals(service.service.getClassName())){
				return true;
			}
		}
		return false;
	}
	/**Need to check for whether or not our Service is running because onResume() will normally load the text for our button based on our resources and not
	 * based on our actual service state.
	*/
	public void onResume(){
		if(serviceRunning())
		{
			Button b =(Button) findViewById(R.id.button1);
			b.setText("Stop");
		}
		super.onResume();
	}
}