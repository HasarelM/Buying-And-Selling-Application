package com.dev.hasarelm.buyingselling.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Model.CustomerRegisterModel;
import com.dev.hasarelm.buyingselling.Model.register;
import com.dev.hasarelm.buyingselling.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtFname,mEtLname,mEtEmail,mEtPassword,mEtC_Password,mEtMobileNo,metAddress,mEtStreet,mEtCity;
    private Button mBtnBuyerRegister;
    private ImageView mImgBackArrow;

    private String message;
    private ArrayList<register> mRegister;
    private CustomerRegisterModel mCustomerRegisterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        initView();
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
        mImgBackArrow = findViewById(R.id.customer_back_arrow);
        mImgBackArrow.setOnClickListener(this);
        mBtnBuyerRegister = findViewById(R.id.activity_customer_register_btn_register);
        mBtnBuyerRegister.setOnClickListener(this);
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

            obj.setName(name+"");
            obj.setLast_name(lName+"");
            obj.setAdd_line_1(address+"");
            obj.setAdd_line_2(street+"");
            obj.setAdd_line_3(city+"");
            obj.setPhone(phone+"");
            obj.setEmail(email+"");
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
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            mEtEmail.requestFocus();
            mEtEmail.setError("invalid email address");
            return false;
        }else if (contact.length() < 10) {
            mEtMobileNo.requestFocus();
            mEtMobileNo.setError("invalid mobile number");
            return false;
        }  else if (password.length() == 0) {
            mEtPassword.requestFocus();
            mEtPassword.setError("Password cannot be blank");
            return false;
        } else if (c_password.length() == 0) {
            mEtC_Password.requestFocus();
            mEtC_Password.setError("C_Password cannot be blank");
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