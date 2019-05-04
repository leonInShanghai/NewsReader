package com.bobo520.newsreader.customDialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by 求知自学网 on 2019/5/3 Copyright © Leon. All rights reserved.
 * Functions: 用户选择 拍照 相册 dialog 封装
 */
public class SelectDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = "SelectDialog";
    private List<String> selects;
    private OnDialogItemClickListener listener;

    public static SelectDialog newInstance(ArrayList<String> selects) {
        SelectDialog dialog = new SelectDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", selects);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_select;
    }

    @Override
    protected void initView(View view) {
        setGravity(Gravity.BOTTOM);
        selects = getArguments().getStringArrayList("data");
        LinearLayout linearLayout = view.findViewById(R.id.select_layout);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        for (int i = 0; i < selects.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(15);
            textView.setText(selects.get(i));
            textView.setBackgroundResource(R.color.white);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 45));
            textView.setLayoutParams(params);
            final int position = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            linearLayout.addView(textView);
            View line = new View(getActivity());
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            line.setLayoutParams(params1);
            line.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_light_gray));
            linearLayout.addView(line);
        }
    }

    @Override
    protected void loadData(Bundle bundle) {

    }

    public void show(Activity activity) {
        show(activity, TAG);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            dismiss();
        }
    }

    public interface OnDialogItemClickListener {
        void onItemClick(int position);
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener listener) {
        this.listener = listener;
    }

}
