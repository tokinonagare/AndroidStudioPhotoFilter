package com.tokinonagare.photofilter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView tokinonagareImageView;
    Drawable  tokinonagareFace;
    Bitmap    bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokinonagareImageView = (ImageView) findViewById(R.id.tokinonagareImageView);

        //getDrawable(int) is deprecated.
        //http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
        //The third answer would solve it.


        tokinonagareFace = ResourcesCompat.getDrawable(getResources(), R.drawable.avatar, null);
        bitmapImage = ((BitmapDrawable) tokinonagareFace).getBitmap();
        Bitmap newPhoto = invertImage(bitmapImage);
        tokinonagareImageView.setImageBitmap(newPhoto);


//        Drawable[] layers = new Drawable[2];
//        layers[0] = ResourcesCompat.getDrawable(getResources(), R.drawable.avatar, null);
//        layers[1] = ResourcesCompat.getDrawable(getResources(), R.drawable.dirty, null);
//        LayerDrawable layerDrawable = new LayerDrawable(layers);
//        tokinonagareImageView.setImageDrawable(layerDrawable);

        //Save the image to the users device
        MediaStore.Images.Media.insertImage(getContentResolver(), newPhoto, "title", "description");

    }

    //Invert a bitmap image
    public static Bitmap invertImage(Bitmap original) {

        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());

        int A, R, G, B;
        int pixelColor;
        int height = original.getHeight();
        int width  = original.getWidth();

        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++){
                pixelColor = original.getPixel(x,y);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);

                finalImage.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return finalImage;

    }

}
