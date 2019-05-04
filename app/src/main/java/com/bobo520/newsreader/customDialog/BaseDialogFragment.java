package com.bobo520.newsreader.customDialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bobo520.newsreader.R;


/**
 * Created by 求知自学网 on 2019/5/3 Copyright © Leon. All rights reserved.
 * Functions: 用于用户 选择 相册 拍照的 dialog
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置自定义样式
        if (setDialogStyle() == 0) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.default_dialog_style);
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, setDialogStyle());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 设置进场动画
        if (setWindowAnimationsStyle() != 0) {
            getDialog().getWindow().setWindowAnimations(setWindowAnimationsStyle());
        }
        // return 视图
        View mView = null;
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        } else {
            if (mView != null) {
                ViewGroup mViewGroup = (ViewGroup) mView.getParent();
                if (mViewGroup != null) {
                    mViewGroup.removeView(mView);
                }
            }
        }
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        //#00ffffff
        window.setBackgroundDrawableResource(R.color.white);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(getArguments());
    }

    /**
     * 自定义时添加layout
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 加载数据
     *
     * @param bundle 用这个Bundle对象接收传入时的Bundle对象
     */
    protected abstract void loadData(Bundle bundle);

    /**
     * 创建视图
     *
     * @param context
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T createView(Context context, int resId) {
        return (T) LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * 设置Dialog点击外部区域是否隐藏
     *
     * @param cancel
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 设置Dialog gravity
     *
     * @param gravity
     */
    protected void setGravity(int gravity) {
        if (getDialog() != null) {
            Window mWindow = getDialog().getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.gravity = gravity;
            mWindow.setAttributes(params);
        }
    }

    /**
     * 设置Dialog窗口width
     *
     * @param width
     */
    protected void setDialogWidth(int width) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Dialog窗口height
     *
     * @param height
     */
    protected void setDialogHeight(int height) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height);
    }

    /**
     * 设置Dialog窗口width，height
     *
     * @param width
     * @param height
     */
    protected void setDialogWidthAndHeight(int width, int height) {
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    /**
     * 设置窗口转场动画
     *
     * @return
     */
    protected int setWindowAnimationsStyle() {
        return 0;
    }

    /**
     * 设置弹出框样式
     *
     * @return
     */
    protected int setDialogStyle() {
        return 0;
    }


    /**
     * 显示Dialog
     *
     * @param activity
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, String tag) {
        show(activity, null, tag);
    }

    /**
     * 显示Dialog
     *
     * @param activity
     * @param bundle   要传递给Dialog的Bundle对象
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, Bundle bundle, String tag) {
        if (activity == null && isShowing()){
            return;
        }
        FragmentTransaction mTransaction = activity.getFragmentManager().beginTransaction();
        Fragment mFragment = activity.getFragmentManager().findFragmentByTag(tag);
        if (mFragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mTransaction.remove(mFragment);
        }
        if (bundle != null) {
            setArguments(bundle);
        }
        show(mTransaction, tag);
    }

    /**
     * 是否显示
     *
     * @return false:isHidden  true:isShowing
     */
    protected boolean isShowing() {
        if (this.getDialog() != null) {
            return this.getDialog().isShowing();
        } else {
            return false;
        }
    }

}
