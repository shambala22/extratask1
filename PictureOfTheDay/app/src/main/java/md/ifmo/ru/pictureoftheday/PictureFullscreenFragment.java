package md.ifmo.ru.pictureoftheday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by Илья on 17.01.2015.
 */
public class PictureFullscreenFragment extends Fragment {
    private static final String ARGUMENT_BITMAP = "arg_bmp";
    private static final String ARGUMENT_HR_LINK = "arg_hrLink";
    private static final String ARGUMENT_WEB_LINK = "arg_webLink";

    int pageNumber;
    Bitmap bmp;
    String hrLink;
    String webLink;

    static PictureFullscreenFragment newInstance(Bitmap bmp, String hrLink, String webLink) {
        PictureFullscreenFragment pageFragment = new PictureFullscreenFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_BITMAP, bmp);
        arguments.putString(ARGUMENT_HR_LINK, hrLink);
        arguments.putString(ARGUMENT_WEB_LINK, webLink);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bmp = getArguments().getParcelable(ARGUMENT_BITMAP);
        hrLink = getArguments().getString(ARGUMENT_HR_LINK);
        webLink = getArguments().getString(ARGUMENT_WEB_LINK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, null);
        try {
            URL picUrl = new URL(hrLink);
            bmp = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
        } catch (Exception e) {
        }
        ((ImageView) view.findViewById(R.id.fullscreenImageView)).setImageBitmap(bmp);

        return view;
    }
}
