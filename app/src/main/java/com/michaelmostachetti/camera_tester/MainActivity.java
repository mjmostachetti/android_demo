package com.michaelmostachetti.camera_tester;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        System.out.println("About to play that track");
        isExternalStorageReadable();
        System.out.println("^^ Status of the storage.");
        listOutExternalStorageFiles();
        //playThatTrack();
    }


    public static Context getAppContext(){
        return MainActivity.context;
    }

    // Plays a track from the res/raw file
    public void playThatTrack(){
        Context rightNow = getAppContext();
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);
        mediaPlayer.start();
    }

    // trying to access file from system to play
    public void playThatTrackFromUri(){
        //Uri myUri; //
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mediaPlayer.setDataSource(getApplicationContext(), myUri);
        //mediaPlayer.prepare();
        //®®mediaPlayer.start();
    }

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        System.out.println(state);
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        System.out.println(state);
        if(Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    // List out all the files in your external storage
    public void listOutExternalStorageFiles(){
        File sdCard = Environment.getExternalStorageDirectory();
        File dirs = new File(sdCard.getAbsolutePath());

        if(dirs.exists()){
            File[] fdirs = dirs.listFiles();
            for(File dir : fdirs){
                System.out.println(dir);
                // now get each sub directory
                File subdirs = new File(dir.getAbsolutePath());
                if(subdirs.exists()) {
                    File[] subDirs = subdirs.listFiles();
                    for(File subFile : subDirs){

                        System.out.println(subFile);
                    }
                }
            }
        }

        Long freeStorageSpace = sdCard.getFreeSpace();
        Long totalStorageSpace = sdCard.getTotalSpace();
        System.out.println("Here is how much free space I have on my external storage " + freeStorageSpace);
        System.out.println("Here is how much total space I have on my external storage " + totalStorageSpace);
    }




    */
    ////////

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //create Intent to take picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); //create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);   // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check taht the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if(! mediaStorageDir.exists()){
            if(! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        //Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_" + timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println("WTF");
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            System.out.println("Sure");
            if(resultCode == RESULT_OK){
                //Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                    data.getData(), Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED){
                // User cancelled the image capture
                System.out.println("USER CANCELLED THE IMAGE");
            } else {
                // Image capture failed, advise user
                System.out.println("THIS FUCKED UP");
            }
        }
        System.out.println("IT RAN THE ON ACTIVITY");
    }
    //////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
