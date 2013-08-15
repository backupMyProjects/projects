package com.twgan.activity;

import java.io.File;

import com.twgan.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.*;
import android.view.*;
import android.widget.*;

public class TakePhotoIntentActivity extends Activity {

    private static final String TAG = "TakePhotoIntentActivity";
    private static final int REQ_CODE = 1;
    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.iv = (ImageView) this.findViewById(R.id.iv);
    }

    public void startCamera(View v) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 自訂照片位置
        it.putExtra(MediaStore.EXTRA_OUTPUT, this.customizedFilePath());
        this.startActivityForResult(it, TakePhotoIntentActivity.REQ_CODE);
    }

    private Uri customizedFilePath() {
        String fileName = System.currentTimeMillis() + ".jpg";
        File sdDir = Environment.getExternalStorageDirectory();
        File theDir = new File(sdDir, "cw1210");
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        File picFile = new File(theDir, fileName);
        Uri uri = Uri.fromFile(picFile);
        Log.d(TAG, "指定照片位置：" + uri.getPath());
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQ_CODE) {
            Log.d(TAG, "不是拍照");
            return;
        }
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "拍照失敗");
            return;
        }
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        // 尺寸永遠是小號的 320:240
        // 但是存在相機或記憶卡裡的尺寸卻是大號的 2592:1944
        Log.d(TAG, "照片尺寸：" + bm.getWidth() + "/" + bm.getHeight());
        // this.iv.setImageBitmap(bm);
        Uri uri = data.getData();
        Log.d(TAG, "照片：" + uri.getPath());
        this.iv.setImageURI(uri);
    }
}