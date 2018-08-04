package com.example.android.nitaattendence;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class GiveAttendenceQrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView =new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkPermission()){
            }
            else{
                requestPermission();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode , String permission[] , int grantResults[]){

        switch(requestCode){

            case REQUEST_CAMERA:
                if(grantResults.length > 0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(GiveAttendenceQrActivity.this , "PEMISSION GRANTED" , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(GiveAttendenceQrActivity.this , "PEMISSION DENIED" , Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA))

                                displayAlertMessage("You need to allow both permissions" ,
                                        new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA} , REQUEST_CAMERA);
                                                }
                                            }
                                        });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private boolean checkPermission(){

        return (ContextCompat.checkSelfPermission(GiveAttendenceQrActivity.this , CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this , new String[]{CAMERA} , REQUEST_CAMERA);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message , DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(GiveAttendenceQrActivity.this)
                .setMessage(message)
                .setPositiveButton("OK" , listener)
                .setNegativeButton("CANCEL" , null)
                .create()
                .show();

    }

    @Override
    public void handleResult(Result result) {

        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SCAN RESULT");
        builder.setPositiveButton("OK" , new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(GiveAttendenceQrActivity.this , GiveAttendenceActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("scanResultOtp" , scanResult);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        builder.setMessage("QR CODE SCANNED SUCCESSFULLY");
        AlertDialog alert = builder.create();
        alert.show();

    }

}
