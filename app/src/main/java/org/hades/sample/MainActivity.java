package org.hades.sample;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import orign.orientation.util.DefaultOrientation;
import orign.orientation.util.DefaultOrientationUtil;

public class MainActivity extends Activity {

    SensorManager mySensorManager;
    Sensor sensor;
    Bitmap yuan;
    Bitmap shang;
    Bitmap zuo;
    Bitmap qiuzuo;
    Bitmap qiushang;
    Bitmap qiuzhong;
    MyView mv;

    /**
     *  创建传感器监听器
     */
    private SensorEventListener mel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //传感器发生变化时调用此函数
            float values[] = sensorEvent.values;
            if (DefaultOrientationUtil.defaultOrientation == DefaultOrientation.LANDSCAPE) {
                //初始姿态是横屏
                mv.dx = values[1];
                mv.dy = (-1) * values[0];
                mv.dz = values[2];
            } else {
                //如果是竖屏
                mv.dx = values[0];
                mv.dy = values[1];
                mv.dz = values[2];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏flag
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 设置横屏

        //计算是横屏还是竖屏
        DefaultOrientationUtil.calDefaultOrientation(this);

        //获得SensorManager对象
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取加速度传感器
        sensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        yuan = BitmapFactory.decodeResource(getResources(), R.drawable.yuan);
        shang = BitmapFactory.decodeResource(getResources(), R.drawable.shang);
        zuo = BitmapFactory.decodeResource(getResources(), R.drawable.zuo);
        qiuzuo = BitmapFactory.decodeResource(getResources(), R.drawable.qiuzuo);
        qiushang = BitmapFactory.decodeResource(getResources(), R.drawable.qiushang);
        qiuzhong = BitmapFactory.decodeResource(getResources(), R.drawable.qiuzhong);

        mv = new MyView(this);
        setContentView(mv);

    }

    @Override
    protected void onResume() {
        mySensorManager.registerListener(mel, sensor, SensorManager.SENSOR_DELAY_UI);
        mv.mvdt.pauseFlag = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mySensorManager.unregisterListener(mel);
        mv.mvdt.pauseFlag = true;
        super.onPause();
    }
}
