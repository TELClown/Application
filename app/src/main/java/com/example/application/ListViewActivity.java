package com.example.application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.ui.Utils;
import com.nineoldandroids.view.ViewHelper;

public class ListViewActivity extends AppCompatActivity {

    public static SparseArray<Bitmap> sPhotoCache = new SparseArray<Bitmap>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Used to get the dimensions of the image views to load scaled down bitmaps
        final View parent = findViewById(R.id.parent);
        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Utils.removeOnGlobalLayoutListenerCompat(parent, this);
                setImageBitmap((ImageView) findViewById(R.id.card_photo_1).findViewById(R.id.photo), R.drawable.lianhuafeng);
                setImageBitmap((ImageView) findViewById(R.id.card_photo_2).findViewById(R.id.photo), R.drawable.nanaodao);
                setImageBitmap((ImageView) findViewById(R.id.card_photo_3).findViewById(R.id.photo), R.drawable.qianmei);
                setImageBitmap((ImageView) findViewById(R.id.card_photo_4).findViewById(R.id.photo), R.drawable.queshi);
            }
        });
    }

    /**
     * Loads drawables into the given image view efficiently. Uses the method described
     * <a href="http://developer.android.com/training/displaying-bitmaps/load-bitmap.html">here.</a>
     *
     * @param imageView
     * @param resId     Resource identifier of the drawable to load from memory
     */
    private void setImageBitmap(ImageView imageView, int resId) {
        Bitmap bitmap = Utils.decodeSampledBitmapFromResource(getResources(),
                resId, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        sPhotoCache.put(resId, bitmap);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.hasLollipop()) {
            // The activity transition animates the clicked image alpha to zero, reset that value when
            // you come back to this activity
            ViewHelper.setAlpha(findViewById(R.id.card_photo_1), 1.0f);
            ViewHelper.setAlpha(findViewById(R.id.card_photo_2), 1.0f);
            ViewHelper.setAlpha(findViewById(R.id.card_photo_3), 1.0f);
            ViewHelper.setAlpha(findViewById(R.id.card_photo_4), 1.0f);
        }
    }

    /**
     * When the user clicks a thumbnail, bundle up information about it and launch the
     * details activity.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void showPhoto(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);

        // Interesting data to pass across are the thumbnail location, the map parameters,
        // the picture title & description, and the key to retrieve the bitmap from the cache
        int resId = 0;
        switch (view.getId()) {
            case R.id.show_photo_1:
                intent.putExtra("lat", 37.6329946)
                        .putExtra("lng", -122.4938344)
                        .putExtra("zoom", 14.0f)
                        .putExtra("title", "莲花峰风景区")
                        .putExtra("description", getResources().getText(R.string.description_lianhuafeng))
                        .putExtra("photo", R.drawable.lianhuafeng);
                resId = R.id.card_photo_1;
                break;
            case R.id.show_photo_2:
                intent.putExtra("lat", 37.73284)
                        .putExtra("lng", -122.503065)
                        .putExtra("zoom", 15.0f)
                        .putExtra("title", "南澳岛")
                        .putExtra("description", getResources().getText(R.string.description_nanaodao))
                        .putExtra("photo", R.drawable.nanaodao);
                resId = R.id.card_photo_2;
                break;
            case R.id.show_photo_3:
                intent.putExtra("lat", 36.861897)
                        .putExtra("lng", -111.374438)
                        .putExtra("zoom", 11.0f)
                        .putExtra("title", "前美村")
                        .putExtra("description", getResources().getText(R.string.description_qianmei))
                        .putExtra("photo", R.drawable.qianmei);
                resId = R.id.card_photo_3;
                break;
            case R.id.show_photo_4:
                intent.putExtra("lat", 36.596125)
                        .putExtra("lng", -118.1604282)
                        .putExtra("zoom", 9.0f)
                        .putExtra("title", "礐石风景区")
                        .putExtra("description", getResources().getText(R.string.description_queshi))
                        .putExtra("photo", R.drawable.queshi);
                resId = R.id.card_photo_4;
                break;
        }


        startActivityGingerBread(view, intent, resId);

    }

    private void startActivityGingerBread(View view, Intent intent, int resId) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.putExtra("left", screenLocation[0]).putExtra("top", screenLocation[1]).putExtra("width", view.getWidth()).putExtra("height", view.getHeight());

        startActivity(intent);
        // Override transitions: we don't want the normal window animation in addition to our
        // custom one
        overridePendingTransition(0, 0);

    }
}