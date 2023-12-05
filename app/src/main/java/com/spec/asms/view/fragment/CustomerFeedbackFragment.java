package com.spec.asms.view.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncFormSubmit;
import com.spec.asms.view.ViewAssignmentActivity;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.MaintainanceVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;

/**
 * A class for display CustomerFeedback Form. It has methods to set values in form's fields.
 * @author jenisha
 *
 */
public class CustomerFeedbackFragment extends Fragment implements OnFocusChangeListener{

    public LinearLayout mContent;
    public signature mSignature;

    public LinearLayout mPopContent;
    public signature mPopSignature;

    public MaintainanceService maintainService;
    public CustomerMasterService custService;
    private CheckBox checkBox;
    private Button btnFeedbackSubmit;
    private Button btnFeedbackPrev;
    private Button btnFeedbackSignClear;
    private Button btnFeedbackSignPopup;
    private TextView lblcheckboxSummery;
    private Button close;
    private TextView lblTesting;
    private TextView lblClamping;
    private TextView lblPainting;
    private TextView lblRccGuard;
    private TextView lblSurakshaTube;
    private TextView lblMeterPerformance;
    private TextView lblConformance;

    private TextView lblSummaryTesting;
    private TextView lblSummaryClamping;
    private TextView lblSummaryPanting;
    private TextView lblSummaryRccGuard;
    private TextView lblSummarySurakshaTube;
    private TextView lblSummaryMeterPerformance;
    private TextView lblSummaryConformance;

    private TextView lblComment;
    private TextView lblReceiptNo;
    private TextView lblOwnerContactNumber;
    private TextView lblOwnerAlternateNumber;
    private TextView lblNoticeNo;
    private TextView lblSignature;
    private TextView lblCustomerFeedbackHeader;
    private TextView errFeedbackSign;
    private TextView lblFeedbackComments;

    private EditText edtFeedbackComment;
    private EditText edtFeedbackReceiptNo;
    private EditText edtFeedbackNoticeNo;
    private Bitmap mBitmap;
    private Bitmap mPopBitmap;

    private EditText edtCustomerNumber, edtAlternateNumber;
    private ImageButton btnEdit;
    private File mypath;

    public boolean isSign = false;
    public boolean isPopSign = false;
    public boolean isPop = false;
    public static boolean isInsert = false;

    private boolean isDatabaseStoringStarted = false;

    public static String tempDir;
    private String uniqueId;
    private String imgString;
    private String imgPopString;
    public String current = null;

    public int count = 1;
    private int id;
    public int insertMaintainanceID;
    public int insertTestingID;
    private int custStatusID;
    public int userID;

    private View mView;
    private View custFdbckView;
    public View mPopView;

    private FragmentManager fragmentManager;

    public static StringBuffer errorMsg = new StringBuffer();

    public ImageView imgFeedBackSign;
    public UserMasterService masterService;

    private String user = Constants.LABEL_BLANK;
    public static final int SIGNATURE_ACTIVITY = 1;
    @SuppressWarnings("unused")
    private String contractorName;
    private String teamName;
    private String noticeNumber=null;
    private String customerNumber="";
    /**
     * A parameterized constructor
     * @param index the business id for which the profile is to be created
     * @return CustomerFeedbackFragment object
     */
    public static CustomerFeedbackFragment newInstance(int id,int custStatusid, String customerNumber){
        CustomerFeedbackFragment custFdbckFragment = new CustomerFeedbackFragment();
        Bundle args = new Bundle();
        args.putInt("userid",id);
        args.putInt("statusid",custStatusid);
        args.putString("customerNumber", customerNumber);
        custFdbckFragment.setArguments(args);
        return custFdbckFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        custFdbckView = inflater.inflate(R.layout.customerfeedback,null);
        ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
        Log.d(Constants.DEBUG,":::CustomerFeedbackFragmentStarted:::");
        fragmentManager = getFragmentManager();

        userID = getArguments().getInt("userid");
        custStatusID = getArguments().getInt("statusid");
        customerNumber = getArguments().getString("customerNumber");
        user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");

        //Store signature to external Directory
        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir_Eng) + "/";
        Log.e("TEMP DIR:-", "DateTime:-"+Utility.getcurrentTimeStamp()+tempDir);
        ContextWrapper cw = new ContextWrapper(getActivity());
        File directory = cw.getDir(getResources().getString(R.string.external_dir_Eng), Context.MODE_PRIVATE);
        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath =  new File(directory,current);

        //Signature Layout
        mContent = (LinearLayout) custFdbckView.findViewById(R.id.feedback_signture_linear_layout);
        mSignature = new signature(getActivity(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mView = mContent;
        checkBox = (CheckBox)custFdbckView.findViewById(R.id.checkBoxSummary);
        btnFeedbackSubmit = (Button)custFdbckView.findViewById(R.id.btnFeedbackSubmit);
        btnFeedbackPrev = (Button)custFdbckView.findViewById(R.id.btnFeedbackPrev);
        btnFeedbackSignClear = (Button)custFdbckView.findViewById(R.id.btnFeedbackSignClear);
        btnFeedbackSignPopup = (Button)custFdbckView.findViewById(R.id.btnFeedbackSignPopup);
        edtFeedbackComment = (EditText)custFdbckView.findViewById(R.id.edtFeedbackComment);
        edtFeedbackComment.setVisibility(View.VISIBLE);
        edtFeedbackReceiptNo = (EditText)custFdbckView.findViewById(R.id.edtFeedbackReceiptNo);
        edtFeedbackNoticeNo = (EditText)custFdbckView.findViewById(R.id.edtFeedbackNoticeNo);
        errFeedbackSign = (TextView)custFdbckView.findViewById(R.id.errFeedbackSign);
        SpannableString str = SpannableString.valueOf(Constants.CUSTFEEDBACK_CHECKBOX_TEXT);
        str.setSpan(new URLSpan(""), 0, Constants.CUSTFEEDBACK_CHECKBOX_TEXT.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);/*blankURL*/
        lblcheckboxSummery = (TextView)custFdbckView.findViewById(R.id.lblcheckBoxSummary);
        lblcheckboxSummery.setText(str);
        checkBox.setSelected(false);
        edtFeedbackComment.setOnFocusChangeListener(this);
        edtFeedbackReceiptNo.setOnFocusChangeListener(this);
        //		edtFeedbackNoticeNo.setOnFocusChangeListener(this);
        edtFeedbackNoticeNo.setEnabled(false);
        lblComment = (TextView)custFdbckView.findViewById(R.id.lblFeedbackComment);
        lblNoticeNo = (TextView)custFdbckView.findViewById(R.id.lblFeedbackNoticeNo);
        lblReceiptNo = (TextView)custFdbckView.findViewById(R.id.lblFeedbackReceiptNo);
        lblOwnerContactNumber = (TextView) custFdbckView.findViewById(R.id.lblCustomerContactNumber);
        lblOwnerAlternateNumber = (TextView) custFdbckView.findViewById(R.id.lblFeedbackAlternateNumber);
        lblSignature = (TextView)custFdbckView.findViewById(R.id.lblFeedbackSign);
        lblCustomerFeedbackHeader = (TextView)custFdbckView.findViewById(R.id.lblfeedbackHeader);
        lblFeedbackComments = (TextView) custFdbckView.findViewById(R.id.sugFeedbackComments);
        imgFeedBackSign = (ImageView)custFdbckView.findViewById(R.id.img_feedback_signture);

        edtCustomerNumber = (EditText) custFdbckView.findViewById(R.id.edtFeedbackContactNumber);
        edtCustomerNumber.setText(customerNumber);

        edtAlternateNumber = (EditText) custFdbckView.findViewById(R.id.edtFeedbackAlternateNumber);
        btnEdit = (ImageButton) custFdbckView.findViewById(R.id.btnEditNumber);
        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("ASMS - Confirm contact number");
                builder.setMessage("Are you sure you want to edit contact number?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        edtCustomerNumber.setEnabled(true);
                        edtCustomerNumber.setFocusable(true);
                        edtCustomerNumber.setFocusableInTouchMode(true);

                        btnEdit.setVisibility(View.GONE);
                        //btnEdit.setEnabled(false);
                    }
                });
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

		/*edtCustomerNumber.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					btnEdit.setVisibility(View.VISIBLE);
					edtCustomerNumber.setEnabled(false);
					return true;
				}
				return false;
			}
		});
*/
        edtCustomerNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    MaintanenceForm.contactNumber = edtCustomerNumber.getText().toString();
                    btnEdit.setVisibility(View.VISIBLE);
                    edtCustomerNumber.setEnabled(false);
                    return true;
                }
                return false;
            }
        });

       /* edtAlternateNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtCustomerNumber.getText().toString().equalsIgnoreCase(s.toString())){
                    Utility.alertDialog(CustomerFeedbackFragment.this.getActivity(), "ASMS", "Owner's Alternate number should not be same as Owner's contact number.");
                    edtAlternateNumber.setText(Constants.BLANK);
                }
            }
        });*/

        if(customerNumber == null || customerNumber.equals(Constants.BLANK)) {
            edtCustomerNumber.setEnabled(true);
            //btnEdit.setEnabled(false);
            btnEdit.setVisibility(View.INVISIBLE);
        }
        maintainService = new MaintainanceService(getActivity());
        custService = new CustomerMasterService(getActivity());
        masterService = new UserMasterService(getActivity());
        changeLanguage(id);
        if((custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")) || user.equals("admin")){
            custFeedbackDisableViews();
        }

        if(Global.isCycleRunning)
            MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
        else
            MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

        lblcheckboxSummery.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog dialog = new Dialog(getActivity(),R.style.AppTheme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.summary);
                close = (Button)dialog.findViewById(R.id.btnSummaryClose);
                lblTesting =(TextView)dialog.findViewById(R.id.lblSummarySugTesting);
                lblClamping =(TextView)dialog.findViewById(R.id.lblSummarySugClamping);
                lblPainting =(TextView)dialog.findViewById(R.id.lblSummarySugPainting);
                lblRccGuard =(TextView)dialog.findViewById(R.id.lblSummarySugRccGuard);
                lblSurakshaTube =(TextView)dialog.findViewById(R.id.lblSummarySugSurakshaTube);
                lblMeterPerformance =(TextView)dialog.findViewById(R.id.lblSummarySugMeterPerformaqnce);
                lblConformance =(TextView)dialog.findViewById(R.id.lblSummarySugConformance);

                lblSummaryTesting = (TextView)dialog.findViewById(R.id.lblSummaryTesting);
                lblSummaryClamping = (TextView)dialog.findViewById(R.id.lblSummaryClamping);
                lblSummaryPanting = (TextView)dialog.findViewById(R.id.lblSummaryPanting);
                lblSummaryRccGuard = (TextView)dialog.findViewById(R.id.lblSummaryRccGuard);
                lblSummarySurakshaTube = (TextView)dialog.findViewById(R.id.lblSummarySurakshaTube);
                lblSummaryMeterPerformance = (TextView)dialog.findViewById(R.id.lblSummaryMeterPerformance);
                lblSummaryConformance = (TextView)dialog.findViewById(R.id.lblSummaryConformance);

                changeLanguageForDialog(id);

                dialog.setOnKeyListener(new Dialog.OnKeyListener() {
                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            Global.summaryDialog = true;
                        }
                        return true;
                    }
                });

                if(TestingVO.getGasLkgDetectionTest() == Constants.LABEL_BLANK )//TestingVO.getInitialPressure()!=Constants.LABEL_BLANK && TestingVO.getFinalPressure()!=Constants.LABEL_BLANK && TestingVO.getPressureDrop()!=Constants.LABEL_BLANK)
                {
                    lblTesting.setText("NO");
                }else if(TestingVO.getGasLkgDetectionTest().equalsIgnoreCase("YES") || TestingVO.getGasLkgDetectionTest().equalsIgnoreCase("NO"))
                {
                    lblTesting.setText("YES");
                }
                //	lblTesting.setText(TestingVO.getGasLkgDetectionTest());

                if(ClampingVO.getIsWorking().equalsIgnoreCase("YES")){
                    lblClamping.setText("YES");
                }else if(ClampingVO.getIsWorking().equalsIgnoreCase("NO")){
                    lblClamping.setText("NO");
                }

                if( RccVO.getIsWorking().equalsIgnoreCase("YES")){
                    lblRccGuard.setText("YES");
                }else if( RccVO.getIsWorking().equalsIgnoreCase("NO")){
                    lblRccGuard.setText("NO");
                }

                if(PaintingVO.getIsPaintingWork().equalsIgnoreCase("YES")){
                    lblPainting.setText("YES");
                }else if(PaintingVO.getIsPaintingWork().equalsIgnoreCase("NO")){
                    lblPainting.setText("NO");
                }

				/*if(SurakshaTubeVO.getIsReplaced().equalsIgnoreCase("YES")){
					lblSurakshaTube.setText("YES");
				}else if(SurakshaTubeVO.getIsReplaced().equalsIgnoreCase("NO")){
					lblSurakshaTube.setText("NO");
				}
				*/
                if(KitchenSurakshaTubeVO.getIsReplaced().equalsIgnoreCase("YES")){
                    lblSurakshaTube.setText("YES");
                }else if(KitchenSurakshaTubeVO.getIsReplaced().equalsIgnoreCase("NO")){
                    lblSurakshaTube.setText("NO");
                }

				/*if(OtherKitchenSurakshaTubeVO.getIsReplaced().equalsIgnoreCase("YES")){
					lblSurakshaTube.setText("YES");
				}else if(OtherKitchenSurakshaTubeVO.getIsReplaced().equalsIgnoreCase("NO")){
					lblSurakshaTube.setText("NO");
				}
				*/

                if(ConformanceDetailVO.getIsNC().equals(getResources().getString(R.string.title_select_option)))
                {
                    lblConformance.setText(Constants.LABEL_BLANK);
                }else{
                    if(ConformanceDetailVO.getIsNC().equalsIgnoreCase("YES"))
                    {
                        lblConformance.setText("NC GIVEN");
                    }else if(ConformanceDetailVO.getIsNC().equalsIgnoreCase("NO"))
                    {
                        lblConformance.setText("NC NOT GIVEN");
                    }
                }
                if(OtherChecksVO.getIsMeter().equals(getResources().getString(R.string.title_select_option))){
                    lblMeterPerformance.setText(Constants.LABEL_BLANK);
                }else
                    lblMeterPerformance.setText(OtherChecksVO.getIsMeter());


                close.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        dialog.dismiss();
                        Global.summaryDialog = true;

                    }

                });
                dialog.show();
            }

        });
        try{
            String number = CustomerFeedbackVO.getNoticeNo();
            if(number.equals(null) || number.equals(Constants.LABEL_BLANK)){

                contractorName = custService.getCustNTeamNames(Constants.JSON_CONTRACTOR).substring(0,2).toUpperCase();

                if(ConformanceDetailVO.getIsNC().equalsIgnoreCase("YES"))
                {
                    teamName = custService.getCustNTeamNames(Constants.JSON_TEAM).substring(0,2).toUpperCase();
                    //			String noticeNumber = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_NOTICE_NUMBER,Constants.BLANK);
                    //			if(noticeNumber.equals(Constants.BLANK)){
                    noticeNumber = Utility.getNoticeNo(getActivity(), teamName);
                    CustomerFeedbackVO.setNoticeNo(noticeNumber);
                    SharedPrefrenceUtil.setPrefrence(getActivity(), Constants.PREF_NOTICE_NUMBER,noticeNumber);
                }else{
                    noticeNumber = Constants.LABEL_BLANK;
                    CustomerFeedbackVO.setNoticeNo(noticeNumber);
                }
                //			}else{
                //				int addNumber = Integer.parseInt(noticeNumber) + 1;
                //				noticeNumber = Utility.getNoticeNumber(getActivity(),String.valueOf(addNumber));
                //				SharedPrefrenceUtil.setPrefrence(getActivity(), Constants.PREF_NOTICE_NUMBER,noticeNumber);
                //			}
                //
                //			if(!(contractorName.equals(Constants.LABEL_BLANK) && teamName.equals(Constants.LABEL_BLANK))){
                //				CustomerFeedbackVO.setNoticeNo(contractorName+teamName+noticeNumber);
                //			}
            }else
            {
                if(ConformanceDetailVO.getIsNC().equalsIgnoreCase("NO"))
                {
                    noticeNumber = Constants.LABEL_BLANK;
                    CustomerFeedbackVO.setNoticeNo(noticeNumber);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            Log.d(Constants.DEBUG,"CustomerFeedbackFragment:"+ Utility.convertExceptionToString(e));
        }


        btnFeedbackSignPopup.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //
                //				 Intent intent = new Intent(getActivity(),SignatureCapture.class);
                //	             startActivityForResult(intent,SIGNATURE_ACTIVITY);
                final Dialog dialog = new Dialog(getActivity(),R.style.AppTheme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.signaturepopup);

                mPopContent = (LinearLayout)dialog.findViewById(R.id.linearLayout);
                mPopSignature = new signature(getActivity(), null);
                mPopSignature.setBackgroundColor(Color.WHITE);
                mPopContent.addView(mPopSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                mPopView = mPopContent;
                isPop = true;
                isPopSign = false;
                Button btnClear = (Button)dialog.findViewById(R.id.popBtnclear);
                btnClear.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        mPopSignature.clear();
                        isPopSign = false;
                    }

                });

                Button btnpopGetSign = (Button)dialog.findViewById(R.id.popBtngetsign);
                btnpopGetSign.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {

                        mPopSignature.savePopSign(mPopView);
                        if(isPopSign)
                        {
                            mContent.setVisibility(View.INVISIBLE);
                            imgFeedBackSign.setVisibility(View.VISIBLE);
                            byte[] imageAsBytes =Base64.decode(imgPopString,Base64.DEFAULT);
                            //Log.e("IMAGE AS BYTE",imageAsBytes.toString());
                            imgFeedBackSign.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        }else
                        {
                            mContent.setVisibility(View.VISIBLE);
                            imgFeedBackSign.setVisibility(View.INVISIBLE);
                        }
                        isPop = false;
                        dialog.dismiss();
                    }

                });
                dialog.show();


            }
        });

        btnFeedbackSignClear.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                mSignature.clear();
                mContent.setVisibility(View.VISIBLE);
                imgFeedBackSign.setVisibility(View.INVISIBLE);
                isSign = false;
                isPopSign = false;
            }
        });

        btnFeedbackSubmit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
//				if(checkBox.isChecked() &&  Global.summaryDialog==true)
//				{
//					Global.summaryDialog = false;
                setValues();
                Global.toValidate = true;
                System.out.println(" STATUS CODE ---- "+MaintainanceVO.getStatusCode());

                if(MaintainanceVO.getStatusId() == masterService.getStatusId("OP")){
                    Utility.alertDialog(getActivity(), "Submit - Error","Please select status other than 'Open'!");

                }else{

                    Utility.saveDateInSharedPref(getActivity(),Constants.PREF_END_DATE);
                    Utility.saveTimeInSharedPref(getActivity(),Constants.PREF_END_TIME);
                    /**
                     * Block for comment Update
                     */
                    if(custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")) // == masterService.getStatusId("COMP")
                    {
                        String where = "maintainanceid = "+MaintainanceVO.getMaintainanceId();
                        ContentValues cv = new ContentValues();
                        cv.put(Constants.CUST_FEED_FIELDS[2],SecurityManager.encrypt(TestingVO.getRemarkLkgDescription()+"\n"+CustomerFeedbackVO.getComments(),Constants.SALT));
                        cv.put(Constants.DB_UPDATEDBY,SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USER_ID, null));
                        cv.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
                        maintainService.updateFeedbackByID(Constants.TBL_DTL_CUSTOMER_FEEDBACK,cv, where);


                        ViewAssignmentActivity newFragment = new ViewAssignmentActivity();
                        FragmentManager fragManager = fragmentManager;
                        FragmentTransaction ft = fragManager.beginTransaction();
                        ft.addToBackStack("ViewAssignmentActivity");
                        ft.replace(R.id.mainFrg, newFragment);
                        ft.commit();


                    }
                    else if(MaintainanceVO.getStatusCode().equals("COMP"))
                    { //if status is completed at first time. at that time check for the check box summary

                        if(checkBox.isChecked() &&  Global.summaryDialog==true)
                        {
                            Global.summaryDialog = false;
                            //	if(!validateFeedback()){
                            MaintanenceForm.validateList.remove(8);
                            MaintanenceForm.validateList.add(8,false);

                            ArrayList<Boolean> tempValidatelist = new ArrayList<Boolean>(MaintanenceForm.validateList);
                            Collections.copy(tempValidatelist,MaintanenceForm.validateList);

                            Collections.sort(tempValidatelist);
                            if(Collections.binarySearch(tempValidatelist, true) >= -1){
                                //unsort the list
                                Utility.alertDialog(getActivity(),"Submit - Error","There are few errors on forms!");
                                MaintanenceForm.sideListAdapter.notifyDataSetChanged();
                            } else if(KitchenSurakshaTubeVO.getIsReplaced().equalsIgnoreCase("No") && isPastDate(KitchenSurakshaTubeVO.getExpirydate())) {
                                Utility.alertDialog(getActivity(),"Submit - Error","Past Date is not allowed in Expiry date of Kitchen Suraksha Tube!");
                            } else if(edtCustomerNumber.getText().toString() == null || edtCustomerNumber.getText().toString().equals(Constants.BLANK)) {
                                Utility.alertDialog(getActivity(), "Submit - Error", "Please enter customer's contact number.");
                            } else if(edtCustomerNumber.getText().toString().length() != 10) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's contact number should be of 10 digits.");
                            } else if(edtCustomerNumber.getText().toString().startsWith("0")) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's contact number should not start with '0'.");
                            } else if(edtCustomerNumber.getText().toString().equalsIgnoreCase("0000000000")) {
                                Utility.alertDialog(getActivity(), "Error", "Invalid customer's contact number.");
                            } else if(edtCustomerNumber.getText().toString().contains("#") || edtCustomerNumber.getText().toString().contains("*") || edtCustomerNumber.getText().toString().contains("+")) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's contact number should not contain special characters(#, *, +, etc...)");
                            }else if(edtFeedbackComment.getText().toString().contains(";")){
                                Utility.alertDialog(getActivity(), "Error", "Comment should not contain special character ;(semicolon)");
                            }


                            else if(edtAlternateNumber.getText().toString() == null || edtAlternateNumber.getText().toString().equals(Constants.BLANK)) {
                                Utility.alertDialog(getActivity(), "Submit - Error", "Please enter customer's alternate contact number.");
                            }else if(edtAlternateNumber.getText().toString().length() != 10) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's alternate contact number should be of 10 digits.");
                            } else if(edtAlternateNumber.getText().toString().startsWith("0")) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's alternate contact number should not start with '0'.");
                            } else if(edtAlternateNumber.getText().toString().equalsIgnoreCase("0000000000")) {
                                Utility.alertDialog(getActivity(), "Error", "Invalid alternate contact number.");
                            } else if(edtAlternateNumber.getText().toString().contains("#") || edtAlternateNumber.getText().toString().contains("*") || edtAlternateNumber.getText().toString().contains("+")) {
                                Utility.alertDialog(getActivity(), "Error", "Customer's alternate contact number should not contain special characters(#, *, +, etc...)");
                            }
                            else {

                                if(!validateFeedback())
                                {
                                    MaintanenceForm.validateList.remove(8);
                                    MaintanenceForm.validateList.add(8,false);
                                    TestingFragment.clearValues();
                                    String status = MaintainanceVO.getStatusCode();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setCancelable(true);
                                    builder.setTitle(Constants.LABLE_TITLE_CONFIRMATION);
                                    builder.setMessage("You have Selected '"+ masterService.getStatus(status)+" '. Please Confirm.");
                                    builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            // Added by Pankit Mistry - to Add support of contact number and alternate contact number
                                            SharedPrefrenceUtil.setPrefrence(CustomerFeedbackFragment.this.getActivity(), MaintainanceVO.getCustomerId()+"c", edtCustomerNumber.getText().toString());
                                            SharedPrefrenceUtil.setPrefrence(CustomerFeedbackFragment.this.getActivity(), MaintainanceVO.getCustomerId()+"a", edtAlternateNumber.getText().toString());
                                            AsyncFormSubmit asyFormSubmit = new AsyncFormSubmit(getActivity(), getFragmentManager());
                                            isDatabaseStoringStarted = true;
                                            asyFormSubmit.execute();
                                        }
                                    });
                                    builder.setNegativeButton(Constants.LABLE_CANCEL,new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                            Global.toValidate = false;
                                            MaintanenceForm.sideListAdapter.notifyDataSetChanged();

                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    MaintanenceForm.validateList.remove(8);
                                    MaintanenceForm.validateList.add(8,true);

                                    ArrayList<Boolean> tempValidatelistnew = new ArrayList<Boolean>(MaintanenceForm.validateList);
                                    Collections.copy(tempValidatelistnew,MaintanenceForm.validateList);

                                    Collections.sort(tempValidatelistnew);
                                    if(Collections.binarySearch(tempValidatelistnew, true) >= -1){
                                        MaintanenceForm.sideListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            //	}else{
                            //		MaintanenceForm.validateList.remove(8);
                            //		MaintanenceForm.validateList.add(8,true);
                            //	}
                            initilizeValues();
                        }else{
                            if(!checkBox.isChecked()){
                                Utility.alertDialog(getActivity(),Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_TITLE,Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_MESSAGE);
                            }else if(Global.summaryDialog == false){
                                Utility.alertDialog(getActivity(),Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_TITLE,Constants.CUSTFEEDBACK_SUMMERYTEXTCONFIRMATION_MESSAGE);
                            }
                        }
                    }
                    else{
                        /**
                         * Status code other than Completed selected
                         */
                        if(checkBox.isChecked() &&  Global.summaryDialog==true)
                        {
                            Global.summaryDialog = false;
                            //if(Utility.isNetAvailable(getActivity())){
                            String status = MaintainanceVO.getStatusCode();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setCancelable(true);
                            builder.setTitle(Constants.LABLE_TITLE_CONFIRMATION);
                            builder.setMessage("You have Selected '"+ masterService.getStatus(status)+" '. Please Confirm.");
                            builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // Added by Pankit Mistry - to Add support of contact number and alternate contact number
                                    SharedPrefrenceUtil.setPrefrence(CustomerFeedbackFragment.this.getActivity(), MaintainanceVO.getCustomerId()+"c", edtCustomerNumber.getText().toString());
                                    SharedPrefrenceUtil.setPrefrence(CustomerFeedbackFragment.this.getActivity(), MaintainanceVO.getCustomerId()+"a", edtAlternateNumber.getText().toString());
                                    AsyncFormSubmit asyFormSubmit = new AsyncFormSubmit(getActivity(), getFragmentManager());
                                    isDatabaseStoringStarted = true;
                                    asyFormSubmit.execute();
                                }
                            });
                            builder.setNegativeButton(Constants.LABLE_CANCEL,new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else{
                            Utility.alertDialog(getActivity(),Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_TITLE,Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_MESSAGE);
                        }
                    }


                    //						}else {
                    //							Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
                    //						}
                }


					/*if(MaintainanceVO.getStatusCode().equals("CT")){ //if status is completed at first time

					if(!validateFeedback()){
						MaintanenceForm.validateList.remove(8);
						MaintanenceForm.validateList.add(8,false);

						AsyncFormSubmit asyFormSubmit = new AsyncFormSubmit(getActivity(), getFragmentManager());
						asyFormSubmit.execute();

					}else{
						MaintanenceForm.validateList.remove(8);
						MaintanenceForm.validateList.add(8,true);
					}
					initilizeValues();

				}else if(custStatusID == 1){ //when customer form open in edit mode
					String where = "maintainanceid = "+MaintainanceVO.getMaintainanceId();
					ContentValues cv = new ContentValues();
					cv.put(Constants.CUST_FEED_FIELDS[2],SecurityManager.encrypt(CustomerFeedbackVO.getComments(),Constants.SALT));
					cv.put(Constants.DB_UPDATEDBY,SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USER_ID, null));
					cv.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
					maintainService.updateFeedbackByID(Constants.TBL_DTL_CUSTOMER_FEEDBACK,cv, where);

					ViewAssignmentActivity newFragment = new ViewAssignmentActivity();
					FragmentManager fragManager = fragmentManager;
					FragmentTransaction ft = fragManager.beginTransaction();
					ft.replace(R.id.mainFrg, newFragment);
					ft.commit();

				}else if(MaintainanceVO.getStatusId() == 4){
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL,"Please select status otherthan Open");

				}else{
					String where ="customerid = '" +MaintainanceVO.getCustomerId()+"'";
					ContentValues cv = new ContentValues();
					cv.put(Constants.CUSTOMER_MASTER_FIELDS[3],MaintainanceVO.getStatusId());
					cv.put(Constants.DB_UPDATEDBY,SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USER_ID, null));
					cv.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
					custService.updateCustomerByID(Constants.TBL_MST_CUSTOMER,cv, where);
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL,"Status Updated Only :"+MaintainanceVO.getStatusCode());
				}*/
                //}
//				else
//				{
//					Utility.alertDialog(getActivity(),Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_TITLE,Constants.CUSTFEEDBACK_CHECKBOXCONFIRMATION_MESSAGE);
//				}
            }
        });

        btnFeedbackPrev.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Utility.changeSelectedForm(8, 9);
                Utility.changeList(8,false);
                ConformanceFragment conformanceFragment = ConformanceFragment.newInstance(userID,custStatusID, customerNumber);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frmLayoutFragment, conformanceFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
        initilizeValues();
        return custFdbckView;
    }

    public boolean isPastDate(String dateString) {

        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Integer.parseInt(dateString.substring(0, 2)) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(dateString.substring(2, dateString.length())));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

		/*if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
			calendar.set(Calendar.DAY_OF_MONTH, 28);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, 30);
		}*/
        Date date = calendar.getTime();
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.HOUR_OF_DAY, 0);
        currentCal.set(Calendar.MINUTE, 0);
        currentCal.set(Calendar.SECOND, 0);
        currentCal.set(Calendar.MILLISECOND, 0);
        currentCal.set(Calendar.DAY_OF_MONTH, 30);
        Date currentDate = currentCal.getTime();
        if(date.before(currentDate)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method to disable all controls when open form in edit mode.
     * @return void
     */

    public void custFeedbackDisableViews(){

        if(user.equals("admin"))
        {
            System.out.println(" -- ADMIN ");
            lblFeedbackComments.setVisibility(View.VISIBLE);
            lblFeedbackComments.setSelected(true);
            edtFeedbackComment.setEnabled(false);
            edtFeedbackComment.setVisibility(View.GONE);
            btnFeedbackSubmit.setVisibility(View.INVISIBLE);


        }else
        {
            edtFeedbackComment.setEnabled(true);
            edtFeedbackComment.setVisibility(View.VISIBLE);
            lblFeedbackComments.setVisibility(View.GONE);

        }
        checkBox.setEnabled(false);
        lblcheckboxSummery.setEnabled(false);
        edtFeedbackReceiptNo.setEnabled(false);
        edtFeedbackNoticeNo.setEnabled(false);
        btnFeedbackSignClear.setVisibility(View.GONE);
        btnFeedbackSignPopup.setVisibility(View.GONE);
        mContent.setVisibility(View.GONE);
        imgFeedBackSign.setVisibility(View.VISIBLE);
        byte[] imageAsBytes = Base64.decode(CustomerFeedbackVO.getSignature(),Base64.DEFAULT);
        //Log.e("IMAGE AS BYTE",imageAsBytes.toString());
        imgFeedBackSign.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
    }

    /**
     * Method used to set values for field and controls at first time submit button click.
     * @return void
     *
     */

    private void setValues(){

        if(custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")){
            if(edtFeedbackComment != null)
            {
                CustomerFeedbackVO.setComments(edtFeedbackComment.getText().toString());
            }

        }else{
            mView.setDrawingCacheEnabled(true);
            mSignature.save(mView);
            if(edtFeedbackComment != null)
            {
                CustomerFeedbackVO.setComments(edtFeedbackComment.getText().toString());
            }
            if(edtFeedbackReceiptNo != null)
            {
                CustomerFeedbackVO.setReceiptNo(edtFeedbackReceiptNo.getText().toString());
            }
            if(edtFeedbackNoticeNo != null)
            {
                CustomerFeedbackVO.setNoticeNo(edtFeedbackNoticeNo.getText().toString());
            }

            if(isPopSign)
            {
                CustomerFeedbackVO.setSignature(imgPopString);
            }else if(isSign)
            {
                CustomerFeedbackVO.setSignature(imgString);
            }
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }



    /**
     * Method used to set values for field and controls.
     * @return void
     *
     */

    private void initilizeValues(){

        imgString = CustomerFeedbackVO.getSignature();
        edtFeedbackComment.setText(CustomerFeedbackVO.getComments());
        if(user.equalsIgnoreCase("admin"))
        {
            lblFeedbackComments.setText(CustomerFeedbackVO.getComments());
        }
        edtFeedbackNoticeNo.setText(CustomerFeedbackVO.getNoticeNo());
        edtFeedbackReceiptNo.setText(CustomerFeedbackVO.getReceiptNo());
    }

    public void onFocusChange(View view, boolean hasFocus) {

        if(view == edtFeedbackComment){
            if(!hasFocus){
                CustomerFeedbackVO.setComments(edtFeedbackComment.getText().toString());
            }
        }else if(view == edtFeedbackReceiptNo){
            if(!hasFocus){
                CustomerFeedbackVO.setReceiptNo(edtFeedbackReceiptNo.getText().toString());
            }
        }else if(view == edtFeedbackNoticeNo){
            if(!hasFocus){
                CustomerFeedbackVO.setNoticeNo(edtFeedbackNoticeNo.getText().toString());
            }
        }
    }


    /**
     * Method used to validate  signature done or not.
     * @return void
     *
     */
    private boolean validateFeedback() {

        boolean isError = false;
        Log.e("IS SIGN", "DateTime:-"+Utility.getcurrentTimeStamp()+""+isSign);
        Log.e("IS POP SIGN", "DateTime:-"+Utility.getcurrentTimeStamp()+""+isPopSign);
        if (!isSign && !isPopSign) {
            isError = true;
            errFeedbackSign.setText(Constants.WARNING_VALIDATE_SIGN);
        }
        return isError;
    }


    /**
     * Method used to get Todays Date
     * @return String : Date as in string
     *
     */
    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000) +  ((c.get(Calendar.MONTH) + 1) * 100) + (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));

        return(String.valueOf(todaysDate));
    }

    /**
     * Method used to get Current Time.
     * @return String : Time as in String
     *
     */
    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +  (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));

        return(String.valueOf(currentTime));
    }

    /**
     * Method used to prepare directory to store signature as Image.
     * @return boolean : true - if directory present , false - if directory is not present.
     *
     */

    private boolean prepareDirectory() {

        try{
            if (makedirs()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_SHORT).show();
            Log.d(Constants.DEBUG,"CustomerFeedbackFragment:"+ Utility.convertExceptionToString(e));
            return false;
        }
    }

    /**
     * Method used to check file is directory or not.
     * @return boolean : true - if directory  , false - if directory is not.
     *
     */

    private boolean makedirs(){

        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()){
            File[] files = tempdir.listFiles();
            for (File file : files){
                if (!file.delete()){
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }


    public void clearValues()
    {
        try
        {
            edtFeedbackComment.setText(Constants.LABEL_BLANK);
            edtFeedbackNoticeNo.setText(Constants.LABEL_BLANK);
            edtFeedbackReceiptNo.setText(Constants.LABEL_BLANK);

        }catch (Exception e) {
            e.printStackTrace();
            Log.d(Constants.DEBUG,"CustomerFeedbackFragment:"+ Utility.convertExceptionToString(e));
        }
    }
    /**
     * Signature class is to capture signture on canvas.
     *
     * @author jenisha
     *
     */
    public class signature extends View{

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs){

            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v) {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null){
                mBitmap = Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);

            try{
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                //				url = Images.Media.insertImage(getActivity().getContentResolver(), mBitmap, "title", null);
                //				Log.v("log_tag","url: " + url);

                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);

                //If you want to convert the image to string use base64 converter
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                final byte[] byteArray = stream.toByteArray();
                imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.v("IMAGE STRING FOR SIGNATURE","sign: " + imgString + " ::}");

                boolean deleted = mypath.delete();

                Log.v("log_tag","deleted: " + mypath.toString() + deleted);

                //				File tempdir = new File(tempDir);
                //				if (tempdir.isDirectory()){
                //
                //					File[] files = tempdir.listFiles();
                //					for (File file : files){
                //						if (file.delete()){
                //							System.out.println("FIle delete from Directory " + file);
                //						}
                //					}
                //				}

            }catch(Exception e){
                Log.v("log_tag", e.toString());
            }
        }

        public void savePopSign(View v) {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mPopBitmap == null){
                mPopBitmap = Bitmap.createBitmap (mPopContent.getWidth(), mPopContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mPopBitmap);

            try{
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mPopBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                //				url = Images.Media.insertImage(getActivity().getContentResolver(), mPopBitmap, "title", null);
                //				Log.v("log_tag","url: " + url);

                //If you want to convert the image to string use base64 converter
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mPopBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                final byte[] byteArray = stream.toByteArray();
                imgPopString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.v("IMAGE STRING FOR Pop SIGNATURE","sign: " + imgPopString + " ::}");

                boolean deleted = mypath.delete();
                Log.v("log_tag","deleted: " + mypath.toString() + deleted);

                File tempdir = new File(tempDir);
                if (tempdir.isDirectory()){
                    File[] files = tempdir.listFiles();
                    for (File file : files){
                        if (file.delete()){
                            System.out.println("FIle delete from Directory " + file);
                        }
                    }
                }

            }catch(Exception e){
                Log.v("log_tag", e.toString());
                Log.d(Constants.DEBUG,"CustomerFeedbackFragment:"+ Utility.convertExceptionToString(e));
            }
        }


        public void clear(){
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas){
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    isSign = true;
                    if(isPop){isPopSign = true;}

                case MotionEvent.ACTION_UP:
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY) {

            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            }else if (historicalX > dirtyRect.right){
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top){
                dirtyRect.top = historicalY;
            }else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY){
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    /**
     * Method used to change label values as per parameter to English and Gujarati.
     * @param id : 1 - English , 2 - Gujarati
     */

    public void changeLanguage(int id){
        id=SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
        switch (id) {

            case Constants.LANGUAGE_ENGLISH:
                lblComment.setText(getResources().getText(R.string.title_Feedback_comment_Eng));
                lblCustomerFeedbackHeader.setText(getResources().getText(R.string.header_Feedback_Eng));
                lblNoticeNo.setText(getResources().getText(R.string.title_Feedback_noticeno_Eng));
                lblReceiptNo.setText(getResources().getText(R.string.title_Feedback_receptno_Eng));
                lblSignature.setText(getResources().getText(R.string.title_Feedback_sign_Eng));
                lblOwnerContactNumber.setText(getResources().getText(R.string.title_Feedback_customer_number_Eng));
                lblOwnerAlternateNumber.setText(getResources().getText(R.string.title_Feedback_alternate_number_Eng));
                break;

            case Constants.LANGUAGE_GUJRATI:
                lblComment.setTypeface(Global.typeface_Guj);
                lblCustomerFeedbackHeader.setTypeface(Global.typeface_Guj);
                lblOwnerAlternateNumber.setTypeface(Global.typeface_Guj);
                lblOwnerContactNumber.setTypeface(Global.typeface_Guj);
                lblNoticeNo.setTypeface(Global.typeface_Guj);
                lblReceiptNo.setTypeface(Global.typeface_Guj);
                lblSignature.setTypeface(Global.typeface_Guj);
                lblComment.setText(getResources().getText(R.string.title_Feedback_comment_Guj));
                lblCustomerFeedbackHeader.setText(getResources().getText(R.string.header_Feedback_Guj));
                lblNoticeNo.setText(getResources().getText(R.string.title_Feedback_noticeno_Guj));
                lblReceiptNo.setText(getResources().getText(R.string.title_Feedback_receptno_Guj));
                lblSignature.setText(getResources().getText(R.string.title_Feedback_sign_Guj));
                lblOwnerContactNumber.setText(getResources().getText(R.string.title_Feedback_customer_number_Guj));
                lblOwnerAlternateNumber.setText(getResources().getText(R.string.title_Feedback_alternate_number_Guj));
                break;

            default:
                break;
        }
    }

    public void changeLanguageForDialog(int id)
    {
        id=SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
        switch (id) {

            case Constants.LANGUAGE_ENGLISH:
                lblSummaryTesting.setText(getResources().getText(R.string.title_summary_testing));
                lblSummaryClamping.setText(getResources().getText(R.string.title_summary_clamping));
                lblSummaryPanting.setText(getResources().getText(R.string.title_summary_painting));
                lblSummaryRccGuard.setText(getResources().getText(R.string.title_summary_rccguard));
                lblSummarySurakshaTube.setText(getResources().getText(R.string.title_summary_surakshatube));
                lblSummaryMeterPerformance.setText(getResources().getText(R.string.title_summary_meterperformance));
                lblSummaryConformance.setText(getResources().getText(R.string.title_summary_conformance));
                break;

            case Constants.LANGUAGE_GUJRATI:
                lblSummaryTesting.setTypeface(Global.typeface_Guj);
                lblSummaryClamping.setTypeface(Global.typeface_Guj);
                lblSummaryPanting.setTypeface(Global.typeface_Guj);
                lblSummaryRccGuard.setTypeface(Global.typeface_Guj);
                lblSummarySurakshaTube.setTypeface(Global.typeface_Guj);
                lblSummaryMeterPerformance.setTypeface(Global.typeface_Guj);
                lblSummaryConformance.setTypeface(Global.typeface_Guj);
                lblSummaryTesting.setText(getResources().getText(R.string.title_summary_testing_Guj));
                lblSummaryClamping.setText(getResources().getText(R.string.title_summary_clamping_Guj));
                lblSummaryPanting.setText(getResources().getText(R.string.title_summary_painting_Guj));
                lblSummaryRccGuard.setText(getResources().getText(R.string.title_summary_rccguard_Guj));
                lblSummarySurakshaTube.setText(getResources().getText(R.string.title_summary_surakshatube_Guj));
                lblSummaryMeterPerformance.setText(getResources().getText(R.string.title_summary_meterperformance_Guj));
                lblSummaryConformance.setText(getResources().getText(R.string.title_summary_conformance_Guj));
                break;

            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Global.summaryDialog = false;
        if(isDatabaseStoringStarted) {
            isDatabaseStoringStarted = false;
        }else{
            setValues();
        }
    }
}