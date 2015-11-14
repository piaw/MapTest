package net.piaw.maptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private Button loadImageButton;
    private ImageView targetImage;
    private TextView textTarget;
    private static Bitmap bitmap;
    private static boolean isBitmapReady = false;

    public static boolean isIsBitmapReady() {
        return isBitmapReady;
    }

    public static Bitmap GetBitmap() {
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadImageButton = (Button) findViewById(R.id.loadimage);
        targetImage = (ImageView) findViewById(R.id.targetimage);
        textTarget = (TextView) findViewById(R.id.texttarget);
        loadImageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("main", "onActivity");

        if (resultCode == RESULT_OK){
            Log.d("main", "Getting Result");
            Uri targetUri = data.getData();
            String msg = "Target" + targetUri.toString();
            Log.d("main", msg);
            textTarget.setText(targetUri.toString());
            try {
                Log.d("main", "Fetching bitmap");
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                Log.d("main", "Bitmap ready");
                isBitmapReady = true;
                targetImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 600, 400, false));
                Log.d("main", "Launching intent");
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
