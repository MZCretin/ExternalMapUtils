package com.cretin.www.externalmaputilslibrary.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.cretin.www.externalmaputilslibrary.R;

/**
 * Created by cretin on 16/3/4.
 */
public class SelectPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private OnPopWindowClickListener listener;

    public SelectPopupWindow(Activity context, OnPopWindowClickListener listener, boolean showGaode, boolean showBaidu) {
        initView(context, listener, showGaode, showBaidu);
    }

    private void initView(Activity context, OnPopWindowClickListener listener, boolean showGaode, boolean showBaidu) {
        this.listener = listener;
        //设置按钮监听
        initViewSelectedPic(context, showGaode, showBaidu);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.dialog_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = ( int ) event.getY();
                if ( event.getAction() == MotionEvent.ACTION_UP ) {
                    if ( y < height ) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initViewSelectedPic(Activity context, boolean showGaode, boolean showBaidu) {
        Button btnGaode, btnBaidu, btnCancel;
        LayoutInflater inflater = ( LayoutInflater ) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_popwindow_dialog_select_type, null);
        if ( showGaode ) {
            btnGaode = ( Button ) mMenuView.findViewById(R.id.btn_select_gaode);
            mMenuView.findViewById(R.id.ll_gaode).setVisibility(View.VISIBLE);
            btnGaode.setOnClickListener(this);
        }
        if ( showBaidu ) {
            btnBaidu = ( Button ) mMenuView.findViewById(R.id.btn_select_baidu);
            mMenuView.findViewById(R.id.ll_baidu).setVisibility(View.VISIBLE);
            btnBaidu.setOnClickListener(this);
        }

        btnCancel = ( Button ) mMenuView.findViewById(R.id.btn_select_pic_cancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onPopWindowClickListener(v);
        dismiss();
    }


    public interface OnPopWindowClickListener {
        void onPopWindowClickListener(View view);
    }

    public interface OnPswClickListener {
        void onPswClickListener(String psw, boolean complete);
    }
}
