package com.spec.asms.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.spec.asms.R;
import com.spec.asms.common.Constants;

import java.util.ArrayList;

public class DialogController {

   /* public static void showAlert(Activity context, String title, String message, String okString,
                                 String cancelString,
                                 final OnDialogActionListener dialogActionListener) {
        if(context != null && !context.isFinishing()) {
            final Dialog dialog = new Dialog(context, R.style.NewDialog2);
            dialog.setContentView(R.layout.dialog_info_with_options);
            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
            TextView txtInfo = (TextView) dialog.findViewById(R.id.txtInfo);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

            txtInfo.setText(message);
            if (title != null && !title.equals(Constants.EMPTY_STRING))
                txtTitle.setText(title);
            else
                txtTitle.setVisibility(View.GONE);
            if (okString != null) {
                btnYes.setText(okString);
            }

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (dialogActionListener != null)
                        dialogActionListener.onPositiveClick();
                }
            });
            dialog.show();

            if (btnNo != null) {
                if (cancelString != null && !cancelString.equals(Constants.EMPTY_STRING)) {
                    btnNo.setText(cancelString);

                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (dialogActionListener != null)
                                dialogActionListener.onNegativeClick();
                        }
                    });
                } else {
                    btnNo.setVisibility(View.GONE);
                }
            }

            dialog.show();
        }
    }*/

    public static void showInfo(Activity context, String message, String okString, final SimpleDialogActionListener listener) {
        if(context != null && !context.isFinishing()) {
            final Dialog dialog = new Dialog(context, R.style.NewDialog2);

            dialog.setContentView(R.layout.dialog_info);
            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
            TextView txtInfo = (TextView) dialog.findViewById(R.id.txtInfo);
            txtInfo.setText(message);
            if (okString != null) {
                btnOk.setText(okString);
            }

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.onPositiveClick();
                }
            });
            dialog.show();
        }
    }
}
