package com.spec.asms.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;

public class SignatureCapture extends Activity{

	private Button btnSignClear,btnSignDone;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.signaturepopup);
        

    }
	
	
}
