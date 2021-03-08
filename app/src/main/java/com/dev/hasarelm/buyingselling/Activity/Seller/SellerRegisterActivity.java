package com.dev.hasarelm.buyingselling.Activity.Seller;

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
import com.dev.hasarelm.buyingselling.Model.VehicleTypeModel;
import com.dev.hasarelm.buyingselling.Model.districtTypes;
import com.dev.hasarelm.buyingselling.Model.register;
import com.dev.hasarelm.buyingselling.Model.vehicleTypes;
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

public class SellerRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtFname,mEtLname,mEtEmail,mEtPassword,mEtC_Password,mEtMobileNo,mEtVehicleNo;
    private Button mBtnSellerRegister;
    private ImageView mImgBackArrow;
    private SearchableSpinner mSpDistrict,mSpVehicleType;

    private List<String> mSelectDistrict = new ArrayList<String>();
    private String message;
    private ArrayList<register> mRegister;
    private List<String> mSelectVehicleType = new ArrayList<String>();
    private CustomerRegisterModel mCustomerRegisterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

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
        getVehicleTypes();
    }

    private void initView() {

        mImgBackArrow = findViewById(R.id.seller_arrow);
        mEtLname = findViewById(R.id.activity_seller_register_et_lName);
        mEtFname = findViewById(R.id.activity_seller_register_et_fName);
        mEtEmail = findViewById(R.id.activity_seller_register_et_email);
        mEtMobileNo = findViewById(R.id.activity_seller_register_et_mobile_no);
        mEtPassword = findViewById(R.id.activity_seller_register_et_password);
        mEtC_Password = findViewById(R.id.activity_seller_register_et_c_password);
        mSpDistrict = findViewById(R.id.activity_seller_register_sp_district);
        mEtVehicleNo = findViewById(R.id.activity_seller_register_et_vehicle_no);
        mSpVehicleType = findViewById(R.id.activity_seller_register_sp_vehicle_type);
        mBtnSellerRegister = findViewById(R.id.activity_seller_register_btn_register);
        mBtnSellerRegister.setOnClickListener(this);
        mImgBackArrow.setOnClickListener(this);
    }

    private void getVehicleTypes() {

        try {
            Endpoints apiService = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<VehicleTypeModel> call_customer = apiService.getVehicleType(VLF_BASE_URL + "vehicle-types");
            call_customer.enqueue(new Callback<VehicleTypeModel>() {
                @Override
                public void onResponse(Call<VehicleTypeModel> call, Response<VehicleTypeModel> response) {

                    if (response.code() == 200) {

                        VehicleTypeModel vehicleTypeModel = response.body();
                        ArrayList<vehicleTypes> vehicleTypesArrayList = vehicleTypeModel.getVehicleTypes();

                        for (vehicleTypes Ds : vehicleTypesArrayList) {

                            String description = Ds.getName();
                            mSelectVehicleType.add(description);
                        }

                        mSelectVehicleType.add(0,"Select Vehicle type");
                        ArrayAdapter<String> dataAdapter_type =
                                new ArrayAdapter<String>(SellerRegisterActivity.this
                                        , android.R.layout.simple_spinner_item, mSelectVehicleType);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpVehicleType.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<VehicleTypeModel> call, Throwable t) {

                    Toast.makeText(SellerRegisterActivity.this, "get districts", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception hh) {
        }
    }

    private void getDistricts() {

        try {

            Endpoints endPoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<DistrictsModel> call = endPoints.getDistrict(VLF_BASE_URL + "districts");
            call.enqueue(new Callback<DistrictsModel>() {
                @Override
                public void onResponse(Call<DistrictsModel> call, Response<DistrictsModel> response) {

                    try {

                        if (response.code() == 200) {

                            DistrictsModel districtsModel = response.body();
                            ArrayList<districtTypes> districtTypesArrayList = districtsModel.getDistrictTypes();

                            for (districtTypes DS : districtTypesArrayList)//loda all district
                            {
                                String Description = DS.getName();
                                mSelectDistrict.add(Description);
                            }

                            mSelectDistrict.add(0,"Select District");
                            ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(SellerRegisterActivity.this, android.R.layout.simple_spinner_item, mSelectDistrict);
                            dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpDistrict.setAdapter(dataAdapter_type);
                        }

                    }catch (Exception gg){}

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

            case R.id.activity_seller_register_btn_register:
                sellerRegister();
                break;
            case R.id.seller_arrow:
                previouseActivity();
            default:
                break;
        }
    }

    private void previouseActivity() {

        Intent intent = new Intent(SellerRegisterActivity.this, LoginActivity.class);
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
            String phone = mEtMobileNo.getText().toString();
            String vehicle_no = mEtVehicleNo.getText().toString();
            String c_password = mEtC_Password.getText().toString();
            String password = mEtPassword.getText().toString();
            String vehicle_type = mSpVehicleType.getSelectedItemPosition()+ "";
            String district_type = mSpDistrict.getSelectedItemPosition()+ "";

            obj.setEmail(email+"");
            obj.setName(name+"");
            obj.setLast_name(lName+"");
            obj.setPhone(phone+"");
            obj.setVehicle_no(vehicle_no+"");
            obj.setPassword(password+"");
            obj.setVehicle_type_id(vehicle_type+"");
            obj.setDistrict_id(district_type+"");
            obj.setPassword_confirmation(c_password+"");
            obj.setType("1");
            objLst.add(obj);

            SharedPreferencesClass.setLocalSharedPreference(SellerRegisterActivity.this,"S_email",email);
            SharedPreferencesClass.setLocalSharedPreference(SellerRegisterActivity.this,"S_name",name);
            SharedPreferencesClass.setLocalSharedPreference(SellerRegisterActivity.this,"S_lName",lName);
            SharedPreferencesClass.setLocalSharedPreference(SellerRegisterActivity.this,"S_phone",phone);
            SharedPreferencesClass.setLocalSharedPreference(SellerRegisterActivity.this,"S_vehicle_no",vehicle_no);


        }

        return objLst;
    }


    private void sellerRegister() {

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
                            Toast.makeText(SellerRegisterActivity.this, "customer "+message,Toast.LENGTH_LONG).show();
                            message = response.body().getMessage();
                            mCustomerRegisterModel = response.body();
                            mRegister = response.body().getRegister();
                            Intent intent = new Intent(SellerRegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerRegisterModel> call, Throwable t) {
                        myPd_ring.dismiss();
                        Toast.makeText(SellerRegisterActivity.this, ""+message,Toast.LENGTH_LONG).show();
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
        final String vehicle_no = mEtVehicleNo.getText().toString();
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
        }else if (vehicle_no.length() == 0) {
            mEtVehicleNo.requestFocus();
            mEtVehicleNo.setError("Vehicle no cannot be blank");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            mEtEmail.requestFocus();
            mEtEmail.setError("invalid email address");
            return false;
        }else if (contact.length() < 10) {
            mEtMobileNo.requestFocus();
            mEtMobileNo.setError("invalid mobile number");
            return false;
        }  else if (password.length() < 7) {
            mEtPassword.requestFocus();
            mEtPassword.setError("Password length should be more than 7");
            return false;
        } else if (c_password.length() <7) {
            mEtC_Password.requestFocus();
            mEtC_Password.setError("Password length should be more than 7");
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