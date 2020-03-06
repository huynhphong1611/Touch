package huynhpdv.android.touch.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import huynhpdv.android.touch.R;

public class TouchService extends Service implements View.OnTouchListener {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private ImageView mTouch;
    private int DOWN_X, DOWN_Y,MOVE_X,MOVE_Y,xparam,yparam;
    private LinearLayout mLinearLayout;
    private boolean mDown, mUp, mMove;
    private int mCountClick;
    public TouchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("huynhDebug", "onCreate: huynhpdv.android.touch.service.TouchService");
        initView();
    }
    private void initView() {
        mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
        mLinearLayout = new LinearLayout(this);
        mView = LayoutInflater.from(this).inflate(R.layout.view_touch, mLinearLayout);
        //huynh dinh nghia params
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.CENTER;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;//trong suot
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;// noi tren all be mat ung dung khac
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// khong bi gioi han boi man hinh|Su duoc nut home
        mWindowManager.addView(mLinearLayout, mLayoutParams);


        mTouch = mView.findViewById(R.id.touch);
        mTouch.setOnTouchListener(this);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mLinearLayout);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("huynhDebug", "onTouch: ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDown = true;
                xparam = mLayoutParams.x;
                yparam = mLayoutParams.y;
                DOWN_X = (int)event.getRawX();
                DOWN_Y = (int)event.getRawY();
            case MotionEvent.ACTION_UP:
                mUp = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove = true;
                MOVE_X = (int) event.getRawX()- DOWN_X ;
                MOVE_Y = (int) event.getRawY()- DOWN_Y ;
                updateView(MOVE_X, MOVE_Y);
                break;

        }
        Log.d("huynhDebug", "onTouch: end");
        onClickTouch();
        return true;
    }
    private void updateView(int x, int y) {
        mLayoutParams.x = x+xparam;
        mLayoutParams.y = y+yparam;
        mWindowManager.updateViewLayout(mLinearLayout, mLayoutParams);
    }

    private void onClickTouch() {
        if (mUp && mDown && !mMove) {
            mCountClick ++;
            Log.d("huynhDebug", "onClickTouch: đã click " + mCountClick);

        }
        mUp = mMove = mDown = false;
    }

}

