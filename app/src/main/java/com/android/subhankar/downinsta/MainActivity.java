package com.android.subhankar.downinsta;

import android.content.ClipDescription;
import android.content.Context;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url;
    EditText editTextUrl;
    Bitmap image;
    ImageView imgDisplay;
    ProgressBar pbLoading;
    TextView tvPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        editTextUrl = (EditText) findViewById(R.id.editText);
        editTextUrl.setInputType(InputType.TYPE_NULL);
        imgDisplay = (ImageView) findViewById(R.id.imageView);
        tvPercent = (TextView) findViewById(R.id.tvProgress);
        pbLoading = (ProgressBar) findViewById(R.id.progressBar);

        Button btnPasteUrl = (Button) findViewById(R.id.btnPasteUrl);
        btnPasteUrl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onClickButtonPasteToUrlField(view);
            }
        });

        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imgDisplay.setVisibility(View.INVISIBLE);
                editTextUrl.setText("");
                tvPercent.setText("0%");
                pbLoading.setProgress(0);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

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

    public void onClickButtonPasteToUrlField(View v) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm.hasPrimaryClip()) {
            ClipDescription desc = cm.getPrimaryClipDescription();
            Log.e("asd", desc.toString());
            if (desc.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                CharSequence pasteText = cm.getPrimaryClip().getItemAt(0).getText();
                editTextUrl.setText(pasteText);
                download();
            } else {
                Log.e("asd", "not text");
                //makeToast("Unable to paste non-text data. Please copy from Instagram again.");
                Snackbar.make(v, "Unable to paste non-text data. Please copy from Instagram again.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Log.e("asd", "nothing to paste");
            //makeToast("Clipboard is empty. Please copy from Instagram again.");
            Snackbar.make(v, "Clipboard is empty. Please copy from Instagram again.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void download(){
        //imgDisplay.setImageResource(RES_PLACEHOLDER);
        imgDisplay.setVisibility(View.INVISIBLE);
        tvPercent.setVisibility(View.VISIBLE);
        tvPercent.setText("0%");
        pbLoading.setVisibility(View.VISIBLE);
        url = editTextUrl.getText().toString()+"media/?size=l";
        final BasicImageDownloader downloader = new BasicImageDownloader(new BasicImageDownloader.OnImageLoaderListener() {
            @Override
            public void onError(BasicImageDownloader.ImageError error) {
                Toast.makeText(MainActivity.this, "Error code " + error.getErrorCode() + ": " +
                        error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
                //imgDisplay.setImageResource(RES_ERROR);
                tvPercent.setVisibility(View.INVISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onProgressChange(int percent) {
                pbLoading.setProgress(percent);
                tvPercent.setText(percent + "%");
            }

            @Override
            public void onComplete(Bitmap result) {
                image = result;
                tvPercent.setVisibility(View.INVISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                imgDisplay.setVisibility(View.VISIBLE);
                imgDisplay.setImageBitmap(result);
                imgDisplay.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
            }
        });
        downloader.download(url, true);
    }

    public void save(){
        int num = 0;
        /* save the image - I'm gonna use JPEG */
        final Bitmap.CompressFormat mFormat = Bitmap.CompressFormat.JPEG;
                        /* don't forget to include the extension into the file name */
        File myImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "DownInsta" + File.separator + "image" + "." + mFormat.name().toLowerCase());

        while(myImageFile.exists()) {
            num++;
            myImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "DownInsta" + File.separator + "image" + num + "." + mFormat.name().toLowerCase());
        }

        final File copy = myImageFile;

        BasicImageDownloader.writeToDisk(myImageFile, image, new BasicImageDownloader.OnBitmapSaveListener() {
            @Override
            public void onBitmapSaved() {
                Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_LONG).show();
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[] { copy.toString() }, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });
            }

            @Override
            public void onBitmapSaveError(BasicImageDownloader.ImageError error) {
                Toast.makeText(MainActivity.this, "Error code " + error.getErrorCode() + ": " +
                        error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }


        }, mFormat, false);
    }
}