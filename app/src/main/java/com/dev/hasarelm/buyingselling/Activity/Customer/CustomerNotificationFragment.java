package com.dev.hasarelm.buyingselling.Activity.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev.hasarelm.buyingselling.R;

public class CustomerNotificationFragment extends Fragment {

    View rootView;

    public CustomerNotificationFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.customer_notification_fragment, container, false);

        return rootView;
    }
}
