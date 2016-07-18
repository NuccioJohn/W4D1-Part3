package com.example.simpleloader;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";

    private static final String SMS_CONTENT_URI = "content://sms/";

    private static final int LIST_ID = 10;

    private static final int CODE_READ_SMS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                Log.d(TAG, "onCreate: " + "Show explanation");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, CODE_READ_SMS);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, CODE_READ_SMS);
            }
        } else {
            Log.d(TAG, "onCreate: " + "Permission already granted!");
            doMagic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE_READ_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: Good to go!");
                    doMagic();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Bad user");
                }
            }
        }
    }

    public void doMagic() {
        Uri uri = Uri.parse(SMS_CONTENT_URI);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Log.d(TAG, "doMagic: " +
                        cursor.getString(cursor.getColumnIndex("body")));
                Log.d(TAG, "doMagic: " +
                        cursor.getString(cursor.getColumnIndex("address")));

            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}
