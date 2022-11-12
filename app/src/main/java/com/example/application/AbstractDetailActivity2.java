package com.example.application;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public abstract class AbstractDetailActivity2 extends AppCompatActivity {

    public ImageView hero;
    public Bitmap photo;
    public View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        hero = (ImageView) findViewById(R.id.show_photo);
        container = findViewById(R.id.container);

        photo = setupPhoto(getIntent().getIntExtra("photo", R.drawable.lianhuafeng));

        colorize(photo);
        setupText();
        postCreate();
        setupEnterAnimation();
    }

    public abstract void postCreate();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void applyPalette(Palette palette) {
        container.setBackgroundColor(palette.getDarkMutedColor(getColor(R.color.default_dark_muted)));

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setTextColor(palette.getVibrantColor(getColor(R.color.default_vibrant)));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setTextColor(palette.getLightVibrantColor(getColor(R.color.default_light_vibrant)));

    }

    private Bitmap setupPhoto(int resource) {
        Bitmap bitmap = ListViewActivity.sPhotoCache.get(resource);
        hero.setImageBitmap(bitmap);
        return bitmap;
    }

    private void colorize(Bitmap photo) {
        Palette palette = Palette.from(photo).generate();
        applyPalette(palette);
    }

    private void setupText() {
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getIntent().getStringExtra("title"));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(getIntent().getStringExtra("description"));
    }

    @Override
    public void onBackPressed() {
        setupExitAnimation();
    }

    public abstract void setupEnterAnimation();
    public abstract void setupExitAnimation();
}