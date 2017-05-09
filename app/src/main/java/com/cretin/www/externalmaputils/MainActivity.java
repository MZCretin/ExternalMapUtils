package com.cretin.www.externalmaputils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.externalmaputilslibrary.OpenExternalMapAppUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv_showMarker;
    private EditText mEd_ll_marker;
    private EditText mEd_name_marker;
    private EditText mEd_des_marker;
    private TextView mTv_showDirection;
    private EditText mEd_s_ll_direction;
    private EditText mEd_s_name_direction;
    private EditText mEd_d_ll_direction;
    private EditText mEd_d_name_direction;
    private TextView mTv_showNaviApp;
    private EditText mEd_ll_navi_app;
    private EditText mEd_name_navi_app;
    private EditText mEd_des_navi_app;
    private TextView mTv_showNavi;
    private EditText mEd_s_ll_navi;
    private EditText mEd_s_name_navi;
    private EditText mEd_d_ll_navi;
    private EditText mEd_d_name_navi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        initListener();
    }

    //初始化事件
    private void initListener() {
        mTv_showMarker.setOnClickListener(this);
        mTv_showDirection.setOnClickListener(this);
        mTv_showNaviApp.setOnClickListener(this);
        mTv_showNavi.setOnClickListener(this);
    }

    private void bindViews() {
        mTv_showMarker = ( TextView ) findViewById(R.id.tv_showMarker);
        mEd_ll_marker = ( EditText ) findViewById(R.id.ed_ll_marker);
        mEd_name_marker = ( EditText ) findViewById(R.id.ed_name_marker);
        mEd_des_marker = ( EditText ) findViewById(R.id.ed_des_marker);
        mTv_showDirection = ( TextView ) findViewById(R.id.tv_showDirection);
        mEd_s_ll_direction = ( EditText ) findViewById(R.id.ed_s_ll_direction);
        mEd_s_name_direction = ( EditText ) findViewById(R.id.ed_s_name_direction);
        mEd_d_ll_direction = ( EditText ) findViewById(R.id.ed_d_ll_direction);
        mEd_d_name_direction = ( EditText ) findViewById(R.id.ed_d_name_direction);
        mTv_showNaviApp = ( TextView ) findViewById(R.id.tv_showNaviApp);
        mEd_ll_navi_app = ( EditText ) findViewById(R.id.ed_ll_navi_app);
        mEd_name_navi_app = ( EditText ) findViewById(R.id.ed_name_navi_app);
        mEd_des_navi_app = ( EditText ) findViewById(R.id.ed_des_navi_app);
        mTv_showNavi = ( TextView ) findViewById(R.id.tv_showNavi);
        mEd_s_ll_navi = ( EditText ) findViewById(R.id.ed_s_ll_navi);
        mEd_s_name_navi = ( EditText ) findViewById(R.id.ed_s_name_navi);
        mEd_d_ll_navi = ( EditText ) findViewById(R.id.ed_d_ll_navi);
        mEd_d_name_navi = ( EditText ) findViewById(R.id.ed_d_name_navi);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.tv_showMarker:
                showMarker();
                break;
            case R.id.tv_showDirection:
                showDirection();
                break;
            case R.id.tv_showNavi:
                showBrosserNavi();
                break;
            case R.id.tv_showNaviApp:
                showNaviApp();
                break;
        }
    }

    private void showNaviApp() {
        //113.942501,22.539013 深圳大学 南山区南海大道3688号(近桂庙新村)
        String ll = mEd_ll_navi_app.getText().toString();
        if ( TextUtils.isEmpty(ll) ) {
            Toast.makeText(this, "请填写经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split = ll.split(",");
        if ( split.length < 2 ) {
            Toast.makeText(this, "经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String longitude = split[0];
        String latitude = split[1];
        String name = mEd_name_navi_app.getText().toString();
        if ( TextUtils.isEmpty(name) ) {
            Toast.makeText(this, "请填写地名", Toast.LENGTH_SHORT).show();
            return;
        }
        String des = mEd_des_navi_app.getText().toString();
        if ( TextUtils.isEmpty(des) ) {
            Toast.makeText(this, "请填写地名描述", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenExternalMapAppUtils.openMapNavi(this, longitude, latitude, name, des, "测试DEMO");
    }

    private void showBrosserNavi() {
        //113.942501,22.539013 深圳大学 南山区南海大道3688号(近桂庙新村)
        //113.933012,22.538673  田厦·国际中心 桃园路8号
        String sLL = mEd_s_ll_navi.getText().toString();
        String dLL = mEd_d_ll_navi.getText().toString();
        if ( TextUtils.isEmpty(sLL) ) {
            Toast.makeText(this, "请填写起点经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split = sLL.split(",");
        if ( split.length < 2 ) {
            Toast.makeText(this, "起点经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( TextUtils.isEmpty(dLL) ) {
            Toast.makeText(this, "请填写终点经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split1 = dLL.split(",");
        if ( split1.length < 2 ) {
            Toast.makeText(this, "终点经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String sName = mEd_s_name_navi.getText().toString();
        String dName = mEd_d_name_navi.getText().toString();
        if ( TextUtils.isEmpty(sName) ) {
            Toast.makeText(this, "请填写起点地名", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( TextUtils.isEmpty(dName) ) {
            Toast.makeText(this, "请填写终点地名", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenExternalMapAppUtils.openBrosserNaviMap(this, split[0], split[1], sName,
                split1[0], split1[1], dName, "深圳", "测试DEMO");
    }

    private void showDirection() {
        //113.942501,22.539013 深圳大学 南山区南海大道3688号(近桂庙新村)
        //113.933012,22.538673  田厦·国际中心 桃园路8号
        String sLL = mEd_s_ll_direction.getText().toString();
        String dLL = mEd_d_ll_direction.getText().toString();
        if ( TextUtils.isEmpty(sLL) ) {
            Toast.makeText(this, "请填写起点经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split = sLL.split(",");
        if ( split.length < 2 ) {
            Toast.makeText(this, "起点经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( TextUtils.isEmpty(dLL) ) {
            Toast.makeText(this, "请填写终点经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split1 = dLL.split(",");
        if ( split1.length < 2 ) {
            Toast.makeText(this, "终点经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String sName = mEd_s_name_direction.getText().toString();
        String dName = mEd_d_name_direction.getText().toString();
        if ( TextUtils.isEmpty(sName) ) {
            Toast.makeText(this, "请填写起点地名", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( TextUtils.isEmpty(dName) ) {
            Toast.makeText(this, "请填写终点地名", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenExternalMapAppUtils.openMapDirection(this, split[0], split[1], sName,
                split1[0], split1[1], dName, "测试DEMO");
    }

    private void showMarker() {
        //113.933012,22.538673  田厦·国际中心 桃园路8号
        String ll = mEd_ll_marker.getText().toString();
        if ( TextUtils.isEmpty(ll) ) {
            Toast.makeText(this, "请填写经纬度", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] split = ll.split(",");
        if ( split.length < 2 ) {
            Toast.makeText(this, "经纬度格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String longitude = split[0];
        String latitude = split[1];
        String name = mEd_name_marker.getText().toString();
        if ( TextUtils.isEmpty(name) ) {
            Toast.makeText(this, "请填写地名", Toast.LENGTH_SHORT).show();
            return;
        }
        String des = mEd_des_marker.getText().toString();
        if ( TextUtils.isEmpty(des) ) {
            Toast.makeText(this, "请填写地名描述", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenExternalMapAppUtils.openMapMarker(this, longitude, latitude, name, des, "测试DEMO");
    }
}
