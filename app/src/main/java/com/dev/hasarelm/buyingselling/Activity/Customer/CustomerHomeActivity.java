package com.dev.hasarelm.buyingselling.Activity.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.dev.hasarelm.buyingselling.Activity.Seller.SellerHistoryFragment;
import com.dev.hasarelm.buyingselling.Activity.Seller.SellerHomeFragment;
import com.dev.hasarelm.buyingselling.Activity.Seller.SellerNotificationFragment;
import com.dev.hasarelm.buyingselling.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerHomeActivity extends AppCompatActivity {

    //Ui components
    private BottomNavigationView mBtnBottomNavigation;
    private boolean mBackPressedToExitOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        initView();
        mBtnBottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fragment fragment = new CustomerHomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.customer_navigation_home:
                            Fragment fragment = new CustomerHomeFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment);
                            fragmentTransaction.commit();
                            return true;

                        case R.id.customer_navigation_sms:
                            Fragment frament = new CustomerHistoryFragment();
                            FragmentManager fragmentManageer = getSupportFragmentManager();
                            FragmentTransaction fragmentTrsaction = fragmentManageer.beginTransaction();
                            fragmentTrsaction.replace(R.id.container, frament);
                            fragmentTrsaction.commit();
                            return true;
                        case R.id.customer_navigation_notifications:
                            Fragment fragmeffnt = new CustomerNotificationFragment();
                            FragmentManager fragmentManahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkansaction = fragmentManahhger.beginTransaction();
                            fragmentTrkkansaction.replace(R.id.container, fragmeffnt);
                            fragmentTrkkansaction.commit();
                            return true;
                        case R.id.customer_navigation_user_profile:
                            Fragment fragmsseffnt = new CustomerProfileFragment();
                            FragmentManager fragmentMallnahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkllansaction = fragmentMallnahhger.beginTransaction();
                            fragmentTrkkllansaction.replace(R.id.container, fragmsseffnt);
                            fragmentTrkkllansaction.commit();
                            return true;
                    }
                    return false;
                }
            };

    private void initView() {

        mBtnBottomNavigation = findViewById(R.id.customer_bottom_navigation);
    }
}