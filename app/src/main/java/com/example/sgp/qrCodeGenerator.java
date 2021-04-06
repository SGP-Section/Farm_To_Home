package com.example.sgp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class qrCodeGenerator extends AppCompatActivity{

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
        dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        findViewById(R.id.to_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(qrCodeGenerator.this,qrCodeReader.class);
                startActivity(intent);
            }
        });
        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataEdt.getText().toString().isEmpty())
                    Toast.makeText(qrCodeGenerator.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                else {

                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen =(Math.min(width, height)) * 3 / 4;
                    qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT,dimen);
                    try {
                        qrCodeIV.setImageBitmap(qrgEncoder.encodeAsBitmap());
                    } catch (WriterException e) {
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });
    }
}