package com.example.sgp.QRcode;

import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRcodeGenerator {
    public static void  CreateQR(ImageView qrCodeIV,String S){
        /* WindowManager manager = (WindowManager) getSystemService(dialog.WINDOW_SERVICE);
                        Display display = manager.getDefaultDisplay();
                        Point point = new Point();
                        display.getSize(point);
                        int width = point.x;
                        int height = point.y;
                        int dimen = (Math.min(width, height)) * 3 / 4;*/
        QRGEncoder qrgEncoder = new QRGEncoder(S, null, QRGContents.Type.TEXT, 1080);
        try {
            qrCodeIV.setImageBitmap(qrgEncoder.encodeAsBitmap());
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }
}
