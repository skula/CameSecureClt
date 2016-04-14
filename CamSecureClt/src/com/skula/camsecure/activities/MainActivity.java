package com.skula.camsecure.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skula.camsecure.R;
import com.skula.camsecure.activities.dialogs.ParameterDialog;
import com.skula.camsecure.models.Snapshot;
import com.skula.camsecure.services.CamSecureService;
import com.skula.camsecure.services.DatabaseService;

public class MainActivity extends Activity {
	private TextView server;
	private TextView mailAddress;
	private EditText passwd;
	private TextView login;
	private Button btnSend;

	private DatabaseService dbService;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.main_layout);

		this.dbService = new DatabaseService(this);

		//dbService.bouchon();
		this.server = (TextView) findViewById(R.id.server);
		server.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_IP) + ":"
				+ dbService.getParameterLabel(DatabaseService.PARAMETER_PORT));
		this.mailAddress = (TextView) findViewById(R.id.mail_address);
		mailAddress.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_MAILADDRESS));
		this.login = (TextView) findViewById(R.id.login);
		login.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_LOGIN));
		this.passwd = (EditText) findViewById(R.id.passwd);
		// passwd.setText("plop");
		this.btnSend = (Button) findViewById(R.id.btn_send);

		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ma = mailAddress.getText().toString();
				String lo = login.getText().toString();
				String pw = passwd.getText().toString();
				if (ma.isEmpty() || lo.isEmpty() || pw.isEmpty()) {
					Toast.makeText(v.getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
				} else {
					try {
						String response = CamSecureService.sendCommand(new Snapshot(ma, lo, pw));
						Toast.makeText(v.getContext(), response, Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(v.getContext(), "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.parameter:
			ParameterDialog dial = new ParameterDialog(this, dbService);
			dial.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void update() {
		server.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_IP) + ":"
				+ dbService.getParameterLabel(DatabaseService.PARAMETER_PORT));
		mailAddress.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_MAILADDRESS));
		login.setText(dbService.getParameterLabel(DatabaseService.PARAMETER_LOGIN));
	}
}
