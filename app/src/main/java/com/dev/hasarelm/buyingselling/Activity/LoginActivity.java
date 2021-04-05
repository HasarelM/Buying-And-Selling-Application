package com.dev.hasarelm.buyingselling.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.hasarelm.buyingselling.Activity.Customer.CustomerHomeActivity;
import com.dev.hasarelm.buyingselling.Activity.Customer.CustomerRegisterActivity;
import com.dev.hasarelm.buyingselling.Activity.Seller.SellerHomeActivity;
import com.dev.hasarelm.buyingselling.Activity.Seller.SellerRegisterActivity;
import com.dev.hasarelm.buyingselling.Common.CommonFunction;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.UserLogin;
import com.dev.hasarelm.buyingselling.Model.login;
import com.dev.hasarelm.buyingselling.Model.register;
import com.dev.hasarelm.buyingselling.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;
import static com.dev.hasarelm.buyingselling.Common.CommonFunction.CustomTost;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // constants
    private static final String TAG = LoginActivity.class.getSimpleName();

    //Ui Components
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnRegister;
    private TextView mTvForgetPassword,mTvClickRegister;
    private RadioButton mRbnCustomer, mRbnSeller;
    private LinearLayout mLinearLayout;
    public static SharedPreferences localSp;

    private ArrayList<login> mLogin;
    private UserLogin mLoginUserModel;
    private String message;
    private String username;
    private String customer;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    public static double GPS_Longitude = 0.00, GPS_Latitude = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        try {
            localSp = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            username = localSp.getString("seller_user_name","");
            customer = localSp.getString("Customer_user_name","");

            if (username.length()==0){

            }else {

                Intent intent = new Intent(LoginActivity.this,SellerHomeActivity.class);
                startActivity(intent);
            }

            if (customer.length()==0){

            }else {

                Intent intent = new Intent(LoginActivity.this,CustomerHomeActivity.class);
                startActivity(intent);
            }

        }catch (Exception h){}


        mRbnCustomer.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked==true)
            {
                mRbnSeller.setChecked(false);
            }
        });
        mRbnSeller.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked==true)
            {
                mRbnCustomer.setChecked(false);
            }
        });
    }

    private void userRegister() {

        if (CommonFunction.checkNetworkConnection(LoginActivity.this)){
            if(mRbnSeller.isChecked()==true || mRbnCustomer.isChecked()==true)
            {
                int FLG = 0;
                if(mRbnCustomer.isChecked()==true)
                {
                    FLG = 1;//buyer
                    Intent intent = new Intent(LoginActivity.this, CustomerRegisterActivity.class);
                    intent.putExtra("NEW_USER_TYPE", FLG);
                    startActivity(intent);
                    finish();
                }else if(mRbnSeller.isChecked()==true)
                {
                    FLG = 2;//Delivery_person
                    Intent intent = new Intent(LoginActivity.this, SellerRegisterActivity.class);
                    intent.putExtra("NEW_USER_TYPE", FLG);
                    startActivity(intent);
                    finish();
                }

            }else
            {
                CustomTost(LoginActivity.this,"Please select Register type");
            }
        }else {

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please turn mobile data on or connect to wifi to proceed!")
                    .show();

        }
/*
        Intent intent = new Intent(LoginActivity.this, CustomerRegisterActivity.class);
        startActivity(intent);*/
    }

    private void forgetPassword() {

        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private ArrayList<register> userLoginToApp() {

        ArrayList<register> riderRegisterModels = new ArrayList<>();
        register obj = new register();
        ArrayList<register> objLst = new ArrayList<>();

        String uName = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        obj.setEmail(uName + "");
        obj.setPassword(password + "");
        objLst.add(obj);

        return objLst;
    }

    private void userLogin() {

        if (CommonFunction.checkNetworkConnection(LoginActivity.this)) {

            if (mEtUsername.getText().toString().length() > 0) {

                if (mEtPassword.getText().toString().length()>0){

                    if (emailVerification(mEtUsername.getText().toString()) == 1) {

                        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);

                        try {
                            String Json_Body = new Gson().toJson(userLoginToApp());
                            JSONArray jsonArray = new JSONArray(Json_Body);
                            JSONObject jsonObject = new JSONObject();
                            if (jsonArray.length() > 0) {
                                jsonObject = jsonArray.getJSONObject(0);
                            }
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                            Endpoints apiService = RetrofitClient.getLoginClient().create(Endpoints.class);
                            Call<UserLogin> call_customer = apiService.userLogin(VLF_BASE_URL + "login", body);
                            call_customer.enqueue(new Callback<UserLogin>() {
                                @Override
                                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                                    try {
                                        if (response.code() == 200) {

                                            myPd_ring.dismiss();
                                            int type = 0;
                                            int userID = 0;
                                            int districtID =0;
                                            message = response.body().getMessage();
                                            mLoginUserModel = response.body();
                                            mLogin = response.body().getLogin();

                                            for (login ls : mLogin) {

                                                type = Integer.parseInt(ls.getType());
                                                userID = Integer.parseInt(ls.getId());
                                                districtID = Integer.parseInt(ls.getDistrict_id());
                                                SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this,"User_ID",userID+"");
                                                SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this,"districtID",districtID+"");

                                                if (type == 1) {

                                                    CustomTost(LoginActivity.this, "Seller Login" );
                                                    Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
                                                    startActivity(intent);
                                                    SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this,"seller_user_name",mEtUsername.getText().toString().trim()+"");

                                                } else if (type == 2) {

                                                    CustomTost(LoginActivity.this, "Customer login");
                                                    Intent intent = new Intent(LoginActivity.this, CustomerHomeActivity.class);
                                                    startActivity(intent);
                                                    SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this,"Customer_user_name",mEtUsername.getText().toString().trim()+"");

                                                }
                                            }
                                        }

                                    }catch (Exception ff){}

                                }

                                @Override
                                public void onFailure(Call<UserLogin> call, Throwable t) {

                                    myPd_ring.dismiss();
                                    CustomTost(LoginActivity.this, "" + message);
                                }
                            });

                        } catch (Exception g) {
                        }

                    } else {
                        CustomTost(LoginActivity.this, "Please select login type");
                    }

                } else {

                    Toast.makeText(this,"Password cannot be blank",Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(this,"User name cannot be blank",Toast.LENGTH_LONG).show();
            }
        }else{

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please turn mobile data on or connect to wifi to proceed!")
                    .show();

        }
    }

    private void initView() {

        mEtUsername = findViewById(R.id.activity_login_et_username);
        mEtPassword = findViewById(R.id.activity_login_et_password);
        mBtnLogin = findViewById(R.id.activity_login_btn_login);
        mTvForgetPassword = findViewById(R.id.activity_login_tv_forgetPassword);
        mBtnRegister = findViewById(R.id.activity_login_btn_register);
        mRbnSeller = findViewById(R.id.activity_login_rbn_seller);
        mRbnCustomer = findViewById(R.id.activity_login_rbn_buyer);
        mLinearLayout = findViewById(R.id.radio_layout);
        mTvClickRegister = findViewById(R.id.text_register);
        mTvClickRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);

        checkPermission();
    }

    public int emailVerification(String email) {

        int result = 0;
        if (email.length() > 0) {
            if (email.matches(emailPattern)) {
                result = 1;
            }
        } else if (email.length() == 0) {
            result = 0;
        }
        return result;

    }

    private void checkPermission() {
        try {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_SMS)) {

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.CALL_PHONE,
                                        android.Manifest.permission.CAMERA,
                                        android.Manifest.permission.READ_PHONE_STATE,
                                        android.Manifest.permission.READ_SMS
                                },
                                PERMISSIONS_MULTIPLE_REQUEST);
                    }

                }
            } else {
                // write your logic code if permission already granted
            }
        }catch (IllegalArgumentException ee)
        {
            CustomTost(LoginActivity.this,ee.toString());
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {

                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarselocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean finelocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean Call_phone = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraPermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean number = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean sms = grantResults[7] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && readExternalFile
                            && coarselocationPermission
                            && finelocationPermission
                            && writeExternalPermission
                            && Call_phone && sms & number) {
                        // write your logic here

                    } else {
                        //CustomTost(LoginActivity.this,"Please grant all permission");
                        //checkPermission();

                    }

                }
                break;
            case 100:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_login_btn_login:
                /*  Intent intent = new Intent(LoginActivity.this, DonaterHomeActivity.class);
        startActivity(intent);*/
                userLogin();
                break;
            case R.id.activity_login_tv_forgetPassword:
                forgetPassword();
                break;
            case R.id.activity_login_btn_register:
                userRegister();
                break;
            case R.id.text_register:
                mLinearLayout.setVisibility(View.VISIBLE);
                mBtnRegister.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}