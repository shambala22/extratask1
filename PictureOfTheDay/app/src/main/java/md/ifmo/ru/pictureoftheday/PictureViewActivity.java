package md.ifmo.ru.pictureoftheday;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
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

import java.util.ArrayList;


public class PictureViewActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<ArrayList<YPicture>> {
    private static final int PICURES_LOADER_ID = 1;
    public static final String APP_PREFERENCES_POSITION = "position";
    SharedPreferences settings;
    ArrayList<YPicture> list;
    int position;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_picture_view);
        pager = (ViewPager) findViewById(R.id.pager);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        if (settings.contains(APP_PREFERENCES_POSITION)) {
            position = settings.getInt(APP_PREFERENCES_POSITION, 0);
        } else position = 0;


        getLoaderManager().initLoader(PICURES_LOADER_ID, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(APP_PREFERENCES_POSITION, pager.getCurrentItem());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (settings.contains(APP_PREFERENCES_POSITION)) {
            position = settings.getInt(APP_PREFERENCES_POSITION, 0);
        } else position = 0;
        update();
    }

    private void update() {
        if (list != null && position >= 0 && position < list.size()) {
            pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            pager.setCurrentItem(position);
        }
    }

    public Loader<ArrayList<YPicture>> onCreateLoader(int i, Bundle bundle) {
        return new YPicturesListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<YPicture>> listLoader, final ArrayList<YPicture> list) {
        this.list = list;
        update();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<YPicture>> listLoader) {
        new YPicturesListLoader(this);
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureFullscreenFragment.newInstance(
                    list.get(position).bitmap,
                    list.get(position).hrLink,
                    list.get(position).pageLink
            );
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}
