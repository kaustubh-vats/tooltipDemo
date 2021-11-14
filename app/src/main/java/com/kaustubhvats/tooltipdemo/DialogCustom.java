package com.kaustubhvats.tooltipdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class DialogCustom{
    public Dialog showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_dialog_custom);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
}