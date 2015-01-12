package shambala.md.ifmo.photoofthday;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by 107476 on 10.01.2015.
 */
public class ImagesProvider extends ContentProvider {

    static final String DB_NAME = "mydb";
    static final int DB_VERSION = 3;

    private static final int IMAGES = 0;
    private static final int IMAGES_ID = 1;


    static final String IMAGES_TABLE = "cities";



    static final String IMAGE_ID ="id";
    static final String IMAGE_DATA = "data";

    static final String AUTHORITY = "ru.ifmo.md.shambala";

    static final String IMAGES_PATH = IMAGES_TABLE;


    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(AUTHORITY, IMAGES_PATH, IMAGES);
        matcher.addURI(AUTHORITY, IMAGES_PATH + "/#", IMAGES_ID);
    }
    public static final Uri IMAGES_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + IMAGES_PATH);



    static final String IMAGES_TABLE_CREATE = "create table " + IMAGES_TABLE + "("
            + IMAGE_ID + " integer primary key autoincrement, "
            + IMAGE_DATA + " blob" + ");";


    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (matcher.match(uri)) {
            case IMAGES:
                queryBuilder.setTables(IMAGES_TABLE);
                break;
            case IMAGES_ID:
                queryBuilder.setTables(IMAGES_TABLE);
                queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID;
        switch (matcher.match(uri)) {
            case IMAGES:
                rowID = db.insert(IMAGES_TABLE, null, values);
                break;

            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        Uri resultUri = Uri.withAppendedPath(uri, ""+rowID);
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int deleted = 0;
        String id;
        switch (matcher.match(uri)) {
            case IMAGES:
                deleted = db.delete(IMAGES_TABLE, selection, selectionArgs);
                break;
            case IMAGES_ID:
                id = uri.getLastPathSegment();
                deleted = db.delete(IMAGES_TABLE, IMAGE_ID + "=" + id, null);
                break;

            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.execSQL(IMAGES_TABLE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists images");
            onCreate(db);
        }
    }
}
