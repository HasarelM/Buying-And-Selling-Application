package com.dev.hasarelm.buyingselling.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.CustomerRegisterModel;
import com.dev.hasarelm.buyingselling.Model.DistrictsModel;
import com.dev.hasarelm.buyingselling.Model.districtTypes;
import com.dev.hasarelm.buyingselling.Model.register;
import com.dev.hasarelm.buyingselling.R;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtFname,mEtLname,mEtEmail,mEtPassword,mEtC_Password,mEtMobileNo,metAddress,mEtStreet,mEtCity;
    private Button mBtnBuyerRegister;
    private ImageView mImgBackArrow;
    private SearchableSpinner mSpDistrict;

    private String message;
    private ArrayList<register> mRegister;
    private CustomerRegisterModel mCustomerRegisterModel;
    private List<String> mSelectDistrict = new ArrayList<String>();
    private List<String> mSelectVehicleType = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        initView();

        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myPd_ring.dismiss();
                // show popup
            }
        }, 3000);

        getDistricts();
    }

    private void initView() {

        mEtFname = findViewById(R.id.activity_customer_register_et_fName);
        mEtLname = findViewById(R.id.activity_customer_register_et_lName);
        mEtEmail = findViewById(R.id.activity_customer_register_et_email);
        mEtPassword = findViewById(R.id.activity_customer_register_et_password);
        mEtC_Password = findViewById(R.id.activity_customer_register_et_c_password);
        mEtMobileNo = findViewById(R.id.activity_customer_register_et_mobile_no);
        metAddress = findViewById(R.id.activity_customer_register_et_address);
        mEtStreet = findViewById(R.id.activity_customer_register_et_street);
        mEtCity = findViewById(R.id.activity_customer_register_et_city);
        mSpDistrict = findViewById(R.id.activity_customer_register_sp_district);
        mImgBackArrow = findViewById(R.id.customer_back_arrow);
        mImgBackArrow.setOnClickListener(this);
        mBtnBuyerRegister = findViewById(R.id.activity_customer_register_btn_register);
        mBtnBuyerRegister.setOnClickListener(this);
    }


    private void getDistricts() {

        try {

            Endpoints endPoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<DistrictsModel> call = endPoints.getDistrict(VLF_BASE_URL + "districts");
            call.enqueue(new Callback<DistrictsModel>() {
                @Override
                public void onResponse(Call<DistrictsModel> call, Response<DistrictsModel> response) {
                    if (response.code() == 200) {

                        DistrictsModel districtsModel = response.body();
                        ArrayList<districtTypes> districtsArrayList = districtsModel.getDistrictTypes();

                        for (districtTypes DS : districtsArrayList)//loda all district
                        {
                            String Description = DS.getName();
                            mSelectDistrict.add(Description);
                        }

                        mSelectDistrict.add(0,"Select District");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(CustomerRegisterActivity.this, android.R.layout.simple_spinner_item, mSelectDistrict);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpDistrict.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<DistrictsModel> call, Throwable t) {

                }
            });

        } catch (Exception ff) {
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.activity_customer_register_btn_register:
                customerUserRegister();
                break;
            case R.id.customer_back_arrow:
                previouseActivity();
            default:
                break;
        }
    }

    private void previouseActivity() {
        Intent intent = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private ArrayList<register> userRegister() {

        ArrayList<register> riderRegisterModels = new ArrayList<>();
        register obj = new register();
        ArrayList<register> objLst = new ArrayList<>();

        if (validation()) {

            String email = mEtEmail.getText().toString();
            String name = mEtFname.getText().toString();
            String lName = mEtLname.getText().toString();
            String address = metAddress.getText().toString();
            String street = mEtStreet.getText().toString();
            String city = mEtCity.getText().toString();
            String phone = mEtMobileNo.getText().toString();
            String c_password = mEtC_Password.getText().toString();
            String password = mEtPassword.getText().toString();
            String district_type = mSpDistrict.getSelectedItemPosition()+ "";

            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"EMAIL",email);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"NAME",name);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"LNAME",lName);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"ADDRESS",address);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"STREET",street);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"CITY",city);
            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this,"PHONE",phone);


            obj.setName(name+"");
            obj.setLast_name(lName+"");
            obj.setAdd_line_1(address+"");
            obj.setAdd_line_2(street+"");
            obj.setAdd_line_3(city+"");
            obj.setPhone(phone+"");
            obj.setEmail(email+"");
            obj.setDistrict_id(district_type+"");
            obj.setPassword(password+"");
            obj.setPassword_confirmation(c_password+"");
            obj.setType("2");
            objLst.add(obj);

        }

        return objLst;
    }


    private void customerUserRegister() {

        if (validation()){

            final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);

            try {

                String Json_Body = new Gson().toJson(userRegister());
                JSONArray jsonArray = new JSONArray(Json_Body);
                JSONObject jsonObject = new JSONObject();
                if (jsonArray.length()>0){
                    jsonObject = jsonArray.getJSONObject(0);
                }

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
                Endpoints apiService = RetrofitClient.getLoginClient().create(Endpoints.class);
                Call<CustomerRegisterModel> call_customer = apiService.customerRegister(VLF_BASE_URL + "register", body);
                call_customer.enqueue(new Callback<CustomerRegisterModel>() {
                    @Override
                    public void onResponse(Call<CustomerRegisterModel> call, Response<CustomerRegisterModel> response) {

                        if (response.code() == 200) {
                            myPd_ring.dismiss();
                            Toast.makeText(CustomerRegisterActivity.this, "customer "+message,Toast.LENGTH_LONG).show();
                            message = response.body().getMessage();
                            mCustomerRegisterModel = response.body();
                            mRegister = response.body().getRegister();

                            int  district=0;
                            String name ="";
                            String mob ="";
                            String lname ="";
                            String address ="";
                            String city ="";
                            String street = "";

                            for (register rs : mRegister){

                                district = Integer.parseInt(rs.getDistrict_id());
                                name = rs.getName().toString().trim();
                                mob = rs.getPhone().toString().trim();
                                address = rs.getAdd_line_1().toString().trim();
                                city = rs.getAdd_line_3().toString().trim();
                                street = rs.getAdd_line_2().toString().trim();
                            }

                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_Name", name+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_Mobile", mob+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_Lname", lname+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_Address", address+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_City", city+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "C_Street", street+"");
                            SharedPreferencesClass.setLocalSharedPreference(CustomerRegisterActivity.this, "district", district+"");

                            Intent intent = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerRegisterModel> call, Throwable t) {
                        myPd_ring.dismiss();
                        Toast.makeText(CustomerRegisterActivity.this, ""+message,Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception gg) {
            }
        }

    }

    private boolean validation() {
        final String NameF = mEtFname.getText().toString();
        final String NameL = mEtLname.getText().toString();
        final String contact = mEtMobileNo.getText().toString();
        final String email = mEtEmail.getText().toString();
        final String address = metAddress.getText().toString();
        final String street = mEtStreet.getText().toString();
        final String city = mEtCity.getText().toString();
        String password = mEtPassword.getText().toString();
        String c_password = mEtC_Password.getText().toString();

        if (NameF.length() == 0) {
            mEtFname.requestFocus();
            mEtFname.setError("First name cannot be blank");
            return false;
        } else if (!NameF.matches("[a-zA-Z ]+")) {
            mEtFname.requestFocus();
            mEtFname.setError("enter only alphabetical character");
            return false;
        }else if (NameL.length() == 0) {
            mEtLname.requestFocus();
            mEtLname.setError("Last name cannot be blank");
            return false;
        } else if (!NameL.matches("[a-zA-Z ]+")) {
            mEtLname.requestFocus();
            mEtLname.setError("enter only alphabetical character");
            return false;
        } else if (email.length() == 0) {
            mEtEmail.requestFocus();
            mEtEmail.setError("Email cannot be blank");
            return false;
        } else if (address.length() == 0) {
            metAddress.requestFocus();
            metAddress.setError("Address cannot be blank");
            return false;
        } else if (city.length() == 0) {
            mEtCity.requestFocus();
            mEtCity.setError("City cannot be blank");
            return false;
        } else if (street.length() == 0) {
            mEtStreet.requestFocus();
            mEtStreet.setError("Street cannot be blank");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            mEtEmail.requestFocus();
            mEtEmail.setError("invalid email address");
            return false;
        }else if (contact.length() < 10) {
            mEtMobileNo.requestFocus();
            mEtMobileNo.setError("invalid mobile number");
            return false;
        }  else if (password.length() <7) {
            mEtPassword.requestFocus();
            mEtPassword.setError("Password should be more than 7");
            return false;
        } else if (c_password.length() <7) {
            mEtC_Password.requestFocus();
            mEtC_Password.setError("C_Password should be more than 7");
            return false;
        } else if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(c_password)) {
            if (password.equals(c_password)) {

            } else {
                mEtC_Password.requestFocus();
                mEtC_Password.setError("Passwords do not match");
                return false;
            }
        } else {

        }

        return true;
    }
}