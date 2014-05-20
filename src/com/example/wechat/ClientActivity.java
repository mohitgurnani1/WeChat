package com.example.wechat;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
This Client sends text message to the server
 */
public class ClientActivity extends Activity {
   private InputStreamReader isr;
	static boolean flag=false;
	private Socket client;
	private PrintWriter printwriter;
	private EditText textField;            //Accepting the text
	private Button button;
	public String messsage;               //send text message
    StringBuffer br=new StringBuffer();  //For Appending purpose
	TextView textFieldScreen;//To Display the text Panel
    TextView uname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null)
		{
			br.append(savedInstanceState.getSerializable("text").toString());
		}
		setContentView(R.layout.activity_slimple_text_client);

		textField = (EditText) findViewById(R.id.editText1); // reference to the text field
		button = (Button) findViewById(R.id.button1); // reference to the send button
		textFieldScreen = (TextView) findViewById(R.id.textView1); 
		// Button press event listener
		uname=(TextView) findViewById(R.id.textView2);
		String uname2=getIntent().getExtras().getString("uname");
		System.out.println(uname2);
		uname.setText(uname2);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				flag=false;                       //flag is used to check whether message is delivered or not,depending on that * will be appended along with text Message
				textFieldScreen.setText("");
				messsage = textField.getText().toString(); // get the text message on the text field
				br.append("\n"+messsage);  //Appending the message
				textField.setText(""); // Reset the text field to blank
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();    //Instead of using threads and handlers,Asyn Task is used to handle them.Its executed by object.execute();
			    System.out.println("client="+flag);
				flag=sendMessageTask.onPostExecute(); //To get final result				
			    if(flag==true)
				textFieldScreen.setText(br.toString()+"  *"); //Message Delivered
			    else
			    textFieldScreen.setText(br.toString());  //Message not delivered
				   	
			    
			    }
		});}
		@Override
protected void onSaveInstanceState(Bundle state) {
   
    state.putSerializable("text",br);
    super.onSaveInstanceState(state);

	}

	private class SendMessage extends AsyncTask<Void, Void, Void> {
boolean flag=false;
		@Override
		protected Void doInBackground(Void... params) {
			try {

			client = new Socket("192.168.2.24",5591); // connect to the server: wifi ipv4 address
		///	printwriter = new PrintWriter(client.getOutputStream(), true);
			flag=true;
			br.append("  *");

			
			
			
			
			
			DataInputStream in=new DataInputStream(client.getInputStream());
			DataOutputStream out=new DataOutputStream(client.getOutputStream());
			out.writeUTF(""+messsage);
			
			br.append("   ..."+in.readUTF());
			
			
			
			
			
		    
			
			
		///	printwriter.write(messsage); // write the message to output stream
		///	isr = new InputStreamReader(client.getInputStream());
			///BufferedReader bufferedReader = new BufferedReader(isr); // get the client message
		  ///  String message = bufferedReader.readLine();
///br.append("..."+message);
		///    System.out.println("send mesage="+ClientActivity.flag); //debugging
	///			printwriter.flush();
		///		printwriter.close();
			client.close(); // closing the connection
           
			} catch (UnknownHostException e) {
				e.printStackTrace();
				flag=false;
			} catch (IOException e) {
				e.printStackTrace();
				flag=false;
			}
			return null;
		}

		
		
		 public boolean onPostExecute(Void... params) {
		     return flag;
		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slimple_text_client, menu);
		return true;
	}

}
