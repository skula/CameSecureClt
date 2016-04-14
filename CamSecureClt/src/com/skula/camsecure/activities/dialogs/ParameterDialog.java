package com.skula.camsecure.activities.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.skula.camsecure.R;
import com.skula.camsecure.activities.MainActivity;
import com.skula.camsecure.services.DatabaseService;

public class ParameterDialog extends Dialog implements OnClickListener {
	public Button btnSave;
	private EditText serverIp;
	private EditText serverPort;
	private EditText mailAddress;
	private EditText login;
	private String label;
	private String categoryId;
	private MainActivity parentActivity;
	
	private DatabaseService dbService;

	public ParameterDialog(MainActivity parentActivity, DatabaseService dbs) {
		super(parentActivity);
		this.dbService = dbs;
		this.parentActivity = parentActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parameter_layout);

		this.serverIp = (EditText) findViewById(R.id.param_ip);
		serverIp.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_IP));
		this.serverPort = (EditText) findViewById(R.id.param_port);
		serverPort.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_PORT));
		this.mailAddress = (EditText) findViewById(R.id.param_mailaddress);
		mailAddress.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_MAILADDRESS));
		this.login = (EditText) findViewById(R.id.param_login);
		login.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_LOGIN));

		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			String tmp = mailAddress.getText().toString();
			dbService.updateParameter(DatabaseService.PARAMETER_MAILADDRESS, tmp);
			tmp = login.getText().toString();
			dbService.updateParameter(DatabaseService.PARAMETER_LOGIN, tmp);
			tmp = serverIp.getText().toString();
			dbService.updateParameter(DatabaseService.PARAMETER_IP, tmp);
			tmp = serverPort.getText().toString();
			dbService.updateParameter(DatabaseService.PARAMETER_PORT, tmp);
			parentActivity.update();
			dismiss();
			break;
		default:
			dismiss();
			break;
		}
	}
}