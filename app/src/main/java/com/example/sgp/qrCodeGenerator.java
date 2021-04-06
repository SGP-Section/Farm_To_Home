package com.example.sgp;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class qrCodeGenerator extends AppCompatActivity {

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        String QRString = "";


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = (Math.min(width, height)) * 3 / 4;
        qrgEncoder = new QRGEncoder(QRString, null, QRGContents.Type.TEXT, dimen);
        try {
            qrCodeIV.setImageBitmap(qrgEncoder.encodeAsBitmap());
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }


    }
}