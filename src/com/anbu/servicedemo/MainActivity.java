package com.anbu.servicedemo;

import com.anbu.servicedemo.DemoService.LocalBinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	boolean mBounded;
	DemoService mService;
	TextView text;
	Button btnAdd, btnDelete, btnShowFile, btnNewFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text = (TextView) findViewById(R.id.text);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnShowFile = (Button) findViewById(R.id.btnShowFile);
		btnNewFile = (Button) findViewById(R.id.btnNewFile);

		btnAdd.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnShowFile.setOnClickListener(this);
		btnNewFile.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent mIntent = new Intent(this, DemoService.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	};

	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(MainActivity.this, "Service is disconnected", 1000)
					.show();
			mBounded = false;
			mService = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(MainActivity.this, "Service is connected", 1000)
					.show();
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder) service;
			mService = mLocalBinder.getServerInstance();
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		if (mBounded) {
			unbindService(mConnection);
			mBounded = false;
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnAdd) {
			if (mService.isFileExist()) {
				addDialog();
			} else {
				Toast.makeText(MainActivity.this, "Please create new file",
						Toast.LENGTH_SHORT).show();
			}

		} else if (v == btnDelete) {

			if (mService.isFileExist()) {
				deleteDialog();
			} else {
				Toast.makeText(MainActivity.this, "Please create new file",
						Toast.LENGTH_SHORT).show();
			}

		} else if (v == btnShowFile) {

			showFileDialog();
		} else if (v == btnNewFile) {
			newFileDialog();
		}

	};

	private void addDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialoglayouttwo);
		dialog.setTitle("Add Value");

		final EditText editKey = (EditText) dialog.findViewById(R.id.editKey);
		final EditText editVal = (EditText) dialog.findViewById(R.id.editVal);
		editKey.setHint("Key");
		editVal.setHint("Value");

		Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (editKey.getText().toString().compareTo("") == 0
						|| editVal.getText().toString().compareTo("") == 0) {

					Toast.makeText(MainActivity.this, "Please enter value for all the field",
							Toast.LENGTH_SHORT).show();
				} else {
					boolean success = mService.addData(editKey.getText().toString(), editVal
							.getText().toString());
					if(success)
						text.setText(mService.showFile());
					else
						Toast.makeText(MainActivity.this, "Key already exists",
								Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void deleteDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialoglayoutone);
		dialog.setTitle("Delete Value");

		final EditText editKey = (EditText) dialog.findViewById(R.id.editKey);
		editKey.setHint("Key");

		Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editKey.getText().toString().compareTo("") == 0) {

					Toast.makeText(MainActivity.this, "Please enter value for all the field",
							Toast.LENGTH_SHORT).show();
				} else {
					mService.deleteData(editKey.getText().toString());
					text.setText(mService.showFile());
					dialog.dismiss();
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void newFileDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialoglayoutone);
		dialog.setTitle("New file");

		final EditText editKey = (EditText) dialog.findViewById(R.id.editKey);
		editKey.setHint("File Name");

		Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editKey.getText().toString().compareTo("") == 0) {

					Toast.makeText(MainActivity.this, "Please enter value for all the field",
							Toast.LENGTH_SHORT).show();
				} else {
					mService.newFile(editKey.getText().toString());
					text.setText(mService.showFile());
					dialog.dismiss();
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void showFileDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialoglayoutone);
		dialog.setTitle("Show file");

		final EditText editKey = (EditText) dialog.findViewById(R.id.editKey);
		editKey.setHint("File Name");

		Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editKey.getText().toString().compareTo("") == 0) {

					Toast.makeText(MainActivity.this, "Please enter value for all the field",
							Toast.LENGTH_SHORT).show();
				} else {
					text.setText(mService.showFile(editKey.getText().toString()));
					dialog.dismiss();
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
}