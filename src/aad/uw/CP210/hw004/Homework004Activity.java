package aad.uw.CP210.hw004;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Homework004Activity extends Activity implements OnClickListener {
	Intent i;
	Handler handler;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       handler =new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        setContentView(R.layout.main);
        Button b =(Button) findViewById(R.id.button1);
        b.setOnClickListener(this);
       i = new Intent(this,BeepService.class);
       i.putExtra("handler",new Messenger(handler));
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
	private boolean serviceRunning(){
		ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			if("aad.uw.CP210.hw004.BeepService".equals(service.service.getClassName())){
				return true;
			}
		}
		return false;
	}

}