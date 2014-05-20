package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
private Button submit;
private EditText et;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
submit=(Button)findViewById(R.id.button1);
et=(EditText)findViewById(R.id.editText1);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Login.this,ClientActivity.class);
				final String uname=et.getText().toString();
				intent.putExtra("uname",uname);
				startActivity(intent);
			}
		});
	};
	
}
