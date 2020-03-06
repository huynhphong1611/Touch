package huynhpdv.android.touch;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import huynhpdv.android.touch.service.TouchService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mOn;
    private Button mOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOn = findViewById(R.id.buttonOn);
        mOff = findViewById(R.id.buttonOff);
        mOn.setOnClickListener(this);
        mOff.setOnClickListener(this);
        checkPermission();

    }
    //huynh check quyen ghi de len ung dung khac .
    private boolean checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(myIntent, 999);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            checkPermission();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TouchService.class);
        if (v == mOn) {
            //huynh start service
            startService(intent);
            Log.d("huynhDebug", "onCreate: huynhpdv.android.touch.MainActivity start service");
            finish();
        } else if (v == mOff) {
            stopService(intent);
            Log.d("huynhDebug", "onClick: huynhpdv.android.touch.MainActivity stop service");
        }
    }
}
