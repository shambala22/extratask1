package md.ifmo.ru.pictureoftheday;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Илья on 17.01.2015.
 */
public class YPicturesListLoader extends AsyncTaskLoader<ArrayList<YPicture>> {
    Context context;
    Bitmap bmp;

    public YPicturesListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ArrayList<YPicture> loadInBackground() {
        ArrayList<YPicture> list = new ArrayList<YPicture>();

        Cursor c = context.getContentResolver().query(
                PicturesContentProvider.PICTURES_URI,
                null,
                null,
                null,
                null
        );

        if (c != null) {
            c.moveToFirst();
            while (!c.isBeforeFirst() && !c.isAfterLast()) {
                byte[] byteArray = c.getBlob(c.getColumnIndex(DBPictures.COLUMN_PICTURE));
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                YPicture img = new YPicture(
                        bmp,
                        c.getString(c.getColumnIndex(DBPictures.COLUMN_PICTURE_HR)),
                        c.getString(c.getColumnIndex(DBPictures.COLUMN_PICTURE_LINK))
                );
                list.add(img);
                c.moveToNext();
            }
        }
        c.close();

        return list;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
