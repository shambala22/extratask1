package shambala.md.ifmo.photoofthday;

import android.app.ActionBar;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by 107476 on 12.01.2015.
 */
public class OneImage extends ActionBarActivity {
    ImageView imageView;
    Bitmap b = null;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.imageView2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        index = getIntent().getIntExtra("index", 0);

        try {
            FileInputStream fis = this.openFileInput("" + index);
            b = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (Exception e) {
            Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
        }
        if (b!=null) {
            imageView.setImageBitmap(b);
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item1:
                OutputStream fOut = null;
                String strDirectory = Environment.getExternalStorageDirectory().toString();
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
                File f = new File(strDirectory, ""+seconds);
                    try {
                        fOut = new FileOutputStream(f);
                        b.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, f.getName());
                        values.put(MediaStore.Images.Media.DESCRIPTION, f.getName());
                        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
                        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Toast.makeText(this, "Image has been saved in gallery", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.wallpaper:
                WallpaperManager manager = WallpaperManager.getInstance(this);
                try {
                    manager.setBitmap(b);
                    Toast.makeText(this, "Wallpaper has been changed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
