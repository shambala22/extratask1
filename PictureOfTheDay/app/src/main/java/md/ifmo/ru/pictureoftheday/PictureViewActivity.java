package md.ifmo.ru.pictureoftheday;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class PictureViewActivity extends ActionBarActivity {
    ImageView imageView;
    Bitmap bitmap = null;
    String hrLink;
    String webLink;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        hrLink = intent.getStringExtra("HR_LINK");
        webLink = intent.getStringExtra("WEB_LINK");
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new AppWebViewClient());
        webView.loadUrl(hrLink);
        try {
            URL url = url = new URL(hrLink);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //InputStream inputStream = urlConnection.getInputStream();
            //bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());


        } catch (Exception e) {
            Toast.makeText(this, "Can't Load Picture"+hrLink, Toast.LENGTH_LONG).show();
        }
        imageView.setImageBitmap(bitmap);


    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    private class AppWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
