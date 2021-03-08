package com.dev.hasarelm.buyingselling.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.R;

public class CustomerProfileFragment extends Fragment {

    private TextView mTvLogOut;
    View rootView;

    public CustomerProfileFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.customer_profile_fragment, container, false);

        mTvLogOut = rootView.findViewById(R.id.log_out_btn);
        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                SharedPreferencesClass.ClearSharedPreference(getContext(),"Customer_user_name");
            }
        });

        return rootView;
    }
}
