package com.example.fee_management_new.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.fee_management_new.R;


public class TransperentProgressDialog extends Dialog {

    private ImageView iv;

    public TransperentProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);

        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        //  Glide.with(context).load(R.raw.loaad).apply(new RequestOptions().placeholder(R.drawable.common_google_signin_btn_icon_dark)).into(new DrawableImageViewTarget(holder.profileImage));
        Glide.with(context).load(R.raw.ldd).into(imageViewTarget);

        layout.addView(iv, params);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
    }

}

