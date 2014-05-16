package com.anbu.servicedemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

public class DemoService extends Service {

	IBinder mBinder = new LocalBinder();
	
	SharedPreferences preferences;
    SharedPreferences.Editor editor;
    

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public DemoService getServerInstance() {
			return DemoService.this;
		}
	}

	public String getTime() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return mDateFormat.format(new Date());
	}
	
	public void newFile(String fileName) {
		preferences = getSharedPreferences(fileName,  getApplicationContext().MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	public boolean isFileExist() {
		if(preferences == null)
				return false;
			else
				return true;
	}
	
	public boolean addData(String key, String val) {
		if(!preferences.contains(key))
		{
			editor.putString(key, val);
			editor.commit();
			return true;
		}else
			return false;
	}
	
	public void deleteData(String key) {
		
		editor.remove(key);
	    editor.commit();
	}
	
	public String showFile(String fileName)
	{
		preferences = getSharedPreferences(fileName,  getApplicationContext().MODE_PRIVATE);
		editor = preferences.edit();
		
		String str="";
		
		Map<String,?> keys = preferences.getAll();
		
		for(Map.Entry<String,?> entry : keys.entrySet()){
            str = str + "\n"+entry.getKey() + ": " + 
                                  entry.getValue().toString();            
		}
		
		return str;
	}
	
	public String showFile()
	{
		
		String str="";
		
		Map<String,?> keys = preferences.getAll();
		
		for(Map.Entry<String,?> entry : keys.entrySet()){
            str = str + "\n"+entry.getKey() + ": " + 
                                  entry.getValue().toString();            
		}
		
		return str;
	}
}