package com.example.savingfiles;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    String filename = "myfile";
    String string = "\\Hello World";
    FileOutputStream outputStream;
    FileInputStream fileInputStream;
    TextView tv_showdata;
    int data;

    //        toast
    Context context;
    int duration = Toast.LENGTH_SHORT;
    //        toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_showdata = (TextView) findViewById(R.id.tv_showdata);
        context = getApplicationContext();
    }

    public void saveInternalStorage(View view) {

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();

            Log.w("File Write","Success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
            Toast toast = Toast.makeText(context, "Directory not created : "  + file.mkdirs(), duration);
            toast.show();
        }
        return file;
    }

    public void saveExternalStorage(View view) {

        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
        Log.e("is SD Supported Device", String.valueOf(isSDSupportedDevice));

        boolean writeble = isExternalStorageWritable();
        boolean redable = isExternalStorageReadable();

        if (writeble && redable && isSDSupportedDevice) {
            getAlbumStorageDir(filename);

            Toast toast = Toast.makeText(context, "Sd Card Is flaged : " + isSDSupportedDevice.toString(), duration);
            toast.show();

            Log.w("Writable","True");
        } else {
            Toast toast = Toast.makeText(context, "Sd Card Is flaged : " + isSDSupportedDevice.toString(), duration);
            toast.show();
            Log.w("Writable","False");
        }
    }

    public void show(View view) {
        try {

            byte[] readData;
            fileInputStream = openFileInput(filename);
            String readDataValue = String.valueOf(fileInputStream.read());

            readData = new byte[new Integer(readDataValue)];

            fileInputStream.read(readData);

            tv_showdata.setText(new String(readData));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
