package com.reactnativefingerprintuareu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import com.gemalto.wsq.WSQEncoder;
import java.io.ByteArrayOutputStream;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class FingerprintUareuModule extends ReactContextBaseJavaModule {
    public static final String NAME = "FingerPrintUareu";
    ReactApplicationContext context;
    byte[] img;
    Bitmap bm;
    String convertBase64 = null;
    private static final String E_FAILED_TO_SHOW_FINGER_SCAN = "E_FAILED_TO_SHOW_FINGER_SCAN";
    private Promise mFingerPromise;
    private Fingerprint fingerprint;

    public FingerprintUareuModule(ReactApplicationContext reactContext) {
      super(reactContext);
      context = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


  @ReactMethod
  public void startScan(final Promise promise) {
    fingerprint = new Fingerprint();
    mFingerPromise = promise;

    fingerprint.scan(context, printHandler, updateHandler);
  }

  Handler updateHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
      WritableMap map = Arguments.createMap();
      int status = msg.getData().getInt("status");
      switch (status) {
        case Status.INITIALISED:
          Toast.makeText(context,"Configurando Lector", Toast.LENGTH_SHORT).show();
          break;
        case Status.SCANNER_POWERED_ON:
          Toast.makeText(context,"Lector Encendido", Toast.LENGTH_SHORT).show();
          break;
        case Status.READY_TO_SCAN:
          Toast.makeText(context,"Listo para Escanear Huella", Toast.LENGTH_SHORT).show();
          break;
        case Status.FINGER_DETECTED:
          Toast.makeText(context,"Huella Detectada", Toast.LENGTH_SHORT).show();
          break;
        case Status.RECEIVING_IMAGE:
          Toast.makeText(context,"Imagen Recibida", Toast.LENGTH_SHORT).show();
          break;
        case Status.FINGER_LIFTED:
          Toast.makeText(context,"Se ha quitado el dedo del lector", Toast.LENGTH_SHORT).show();
          break;
        case Status.SCANNER_POWERED_OFF:
          Toast.makeText(context,"Lector Apagado", Toast.LENGTH_SHORT).show();
          break;
        case Status.SUCCESS:
          Toast.makeText(context,"Huella Digital Capturada Exitosamente", Toast.LENGTH_SHORT).show();
          break;
        case Status.ERROR:
          Toast.makeText(context,"Error: " +msg.getData().getString("errorMessage"), Toast.LENGTH_SHORT).show();
          break;
        default:
          Toast.makeText(context,"Error: " +msg.getData().getString("errorMessage"), Toast.LENGTH_SHORT).show();
          break;
      }
    }
  };


  Handler printHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {

      int status = msg.getData().getInt("status");
      WritableMap map = Arguments.createMap();

      if (status == Status.SUCCESS) {

        img = msg.getData().getByteArray("img");
        bm = BitmapFactory.decodeByteArray(img, 0, img.length);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();

        /// ### encoded variable contain the base64 of the fingerprint ### ///
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        /// ### wsqData is optional data for codification in WSQ ### ///
        byte[] wsqData = new WSQEncoder(bm)
          .setBitrate(WSQEncoder.BITRATE_5_TO_1)
          .encode();
        convertBase64 = Base64.encodeToString(wsqData, Base64.DEFAULT);


        map.putString("encoded", encoded);
        map.putString("convertBase64", convertBase64);

        mFingerPromise.resolve(map);
      }
      else {
        mFingerPromise.reject(E_FAILED_TO_SHOW_FINGER_SCAN, "Failed Scan");
      }
    }
  };

}
