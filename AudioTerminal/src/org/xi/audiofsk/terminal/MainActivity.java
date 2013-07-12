package org.xi.audiofsk.terminal;

import org.xi.audiofsk.FSKModem;
import org.xi.audiofsk.FSKModemListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jonas.reedsolomon.Berlekamp;
import com.jonas.reedsolomon.RS;
import com.jonas.reedsolomon.Settings;

public class MainActivity extends Activity {
	static byte codeword[] = new byte[20];
	private FSKModem fskm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fskm = new FSKModem();
		FSKModem.debugPrint(fskm);

		fskm.addDataReceiver(new FSKModemListener() {
			@Override
			public void dataReceivedFromFSKModem(final byte[] data) {
				runOnUiThread(new Runnable() {
				    public void run() {
						int erasures[] = new int[16];
						int nerasures = 15;
				    	RS.decode_data(data, data.length);
						if (RS.check_syndrome() != 0) {
							Berlekamp.correct_errors_erasures(data, data.length,nerasures,erasures);
						}
						
				    	StringBuilder sb = new StringBuilder();
						TextView recvView = (TextView) findViewById(R.id.tvReceived);
						sb.append(recvView.getText().toString());
						for(int i=0; data != null && i < data.length; i++) {
							int v = data[i] & 0xff;
							if (v == 0xff) {
								continue;
							}
							if (v > 31 && v < 127) {
								sb.append((char)v);
							} else {
								if (v < 16) {
									sb.append(" 0");
									sb.append(Integer.toHexString(v));
									sb.append(' ');
								} else {
									sb.append(' ');
									sb.append(Integer.toHexString(v));
									sb.append(' ');
								}
							}
						}
						/* Now decode -- encoded codeword size must be passed */
						
						recvView.setText(sb.toString());
				    }
				});
			}
		});
//encode_data(byte[] msg, int nbytes, byte[] codeword)
		final Button sendBtn = (Button) findViewById(R.id.btnSend);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				EditText inputBox = (EditText) findViewById(R.id.edtInput);
				String msg = inputBox.getText().toString();
				// Initial ECC
				RS.initialize_ecc();
				// Encode data by using RS ECC
				RS.encode_data(msg.getBytes(), msg.length(), codeword);
				// To show ECC message
				TextView recvView = (TextView) findViewById(R.id.tvReceived);
				String tt = new String(codeword);
				recvView.setText(tt);
				// Send ECC
				fskm.writeBytes(codeword);
				inputBox.setText(null);
			}
		});

		final Button clearBtn = (Button) findViewById(R.id.btnClear);
		clearBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView recvView = (TextView) findViewById(R.id.tvReceived);
				recvView.setText(null);
			}
		});

		fskm.start();
	}
	


	@Override
	protected void onDestroy() {
		fskm.stop();
		fskm = null;
		super.onDestroy();
	}

	/*
	 * Trim off excess zeros and parity bytes.
	 */
	static byte[] rtrim(byte[] bytes) {
		int t = bytes.length - 1;
		while (bytes[t] == 0)
			t -= 1;
		byte[] trimmed = new byte[(t+1) - Settings.kParityBytes];
		for (int i = 0; i < trimmed.length; i++) {
			trimmed[i] = bytes[i];
		}
		return trimmed;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
