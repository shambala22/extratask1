package shambala.md.ifmo.photoofthday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;

/**
 * Created by 107476 on 12.01.2015.
 */
public class OneImage extends Activity {
    ImageView imageView;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.imageView2);

        int index = getIntent().getIntExtra("index", 0);
        Bitmap b = null;
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

    public  void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
