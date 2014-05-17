package com.example.wechat;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is a simple Android mobile client
 * This application read any string massage typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 *
 */
public class ClientActivity extends Activity {

	private Socket client;
	private PrintWriter printwriter;
	private EditText textField;
	private Button button;
	private String messsage;
    StringBuffer br=new StringBuffer();
	TextView textFieldScreen;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slimple_text_client);

		textField = (EditText) findViewById(R.id.editText1); // reference to the text field
		button = (Button) findViewById(R.id.button1); // reference to the send button
		textFieldScreen = (TextView) findViewById(R.id.textView1); 
		// Button press event listener
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				textFieldScreen.setText("");
				messsage = textField.getText().toString(); // get the text message on the text field
				br.append("\n"+messsage);
				textField.setText(""); // Reset the text field to blank
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				textFieldScreen.setText(br.toString());
			}
		});
	}

	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				client = new Socket("192.168.0.4",5577); // connect to the server
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(messsage); // write the message to output stream

				printwriter.flush();
				printwriter.close();
				client.close(); // closing the connection

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slimple_text_client, menu);
		return true;
	}

}
