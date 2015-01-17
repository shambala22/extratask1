package md.ifmo.ru.pictureoftheday;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Илья on 17.01.2015.
 */
public class PictureFullscreenFragment extends Fragment {
    private static final String ARGUMENT_BITMAP = "arg_bmp";
    private static final String ARGUMENT_PICTURE_NAME = "arg_pictureName";
    private static final String ARGUMENT_USERNAME = "arg_username";

    int pageNumber;
    Bitmap bmp;
    String pictureName;
    String username;

    static PictureFullscreenFragment newInstance(Bitmap bmp, String pictureName, String username) {
        PictureFullscreenFragment pageFragment = new PictureFullscreenFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_BITMAP, bmp);
        arguments.putString(ARGUMENT_PICTURE_NAME, pictureName);
        arguments.putString(ARGUMENT_USERNAME, username);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bmp = getArguments().getParcelable(ARGUMENT_BITMAP);
        pictureName = getArguments().getString(ARGUMENT_PICTURE_NAME);
        username = getArguments().getString(ARGUMENT_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, null);

        ((ImageView) view.findViewById(R.id.fullscreenImageView)).setImageBitmap(bmp);
        //((TextView) view.findViewById(R.id.pictureName)).setText(pictureName);
        //((TextView) view.findViewById(R.id.username)).setText("by " + username);

        return view;
    }
}
