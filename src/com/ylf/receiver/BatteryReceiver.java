package com.ylf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
			//��ȡ��ǰ����
			int level=intent.getIntExtra("level", 0);
			//��ȡ�ܵ���
			int scale=intent.getIntExtra("scale", 100);
		    Toast.makeText(context, "��ǰ������"+(level*100)/scale, 5).show();
			if(level<15){
				//dosomething
			}
		}

	}

}
