package com.marakana.android.barcodeexample;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * For this demo to work correctly, your Android device must have the
 * ZXing barcode scanning app installed. See http://code.google.com/p/zxing/
 * for more information on ZXing, and a link to the app on Google Play.
 */

public class ScanActivity extends Activity implements OnClickListener {
	private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	private static final int SCAN_REQUEST = 1;
	
	private TextView mTextFormat;
	private TextView mTextContent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button scanButton = (Button) findViewById(R.id.button_scan);
        mTextFormat = (TextView) findViewById(R.id.text_format);
        mTextContent = (TextView) findViewById(R.id.text_content);

        // Check to see if a scanner activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(ACTION_SCAN), 0);
        if (activities.size() != 0) {
            scanButton.setOnClickListener(this);
        } else {
            scanButton.setEnabled(false);
            scanButton.setText(R.string.scanner_not_installed);
        }

    }
    
    public void onClick(View v) {
        Intent intent = new Intent(ACTION_SCAN);

        // Optionally, limit the intent recipient to just the "official" scan app 
        // intent.setPackage("com.google.zxing.client.android");
        
        // Optionally, set this extra to limit the format accepted for scanning
        // intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

        startActivityForResult(intent, SCAN_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	
        if (requestCode == SCAN_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Handle successful scan
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                
                mTextFormat.setText(format);
                mTextContent.setText(contents);
            } else if (resultCode == RESULT_CANCELED) {
                mTextFormat.setText(R.string.scan_unrecognized);
            }
        }
    }
}