package com.dev.hasarelm.buyingselling.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.dev.hasarelm.buyingselling.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellerHomeActivity extends AppCompatActivity {

    //Ui components
    private BottomNavigationView mBtnBottomNavigation;
    private boolean mBackPressedToExitOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        initView();
        mBtnBottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fragment fragment = new SellerHomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Fragment fragment = new SellerHomeFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment);
                            fragmentTransaction.commit();
                            return true;

                        case R.id.navigation_sms:
                            Fragment frament = new SellerHistoryFragment();
                            FragmentManager fragmentManageer = getSupportFragmentManager();
                            FragmentTransaction fragmentTrsaction = fragmentManageer.beginTransaction();
                            fragmentTrsaction.replace(R.id.container, frament);
                            fragmentTrsaction.commit();
                            return true;
                        case R.id.navigation_notifications:
                            Fragment fragmeffnt = new SellerNotificationFragment();
                            FragmentManager fragmentManahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkansaction = fragmentManahhger.beginTransaction();
                            fragmentTrkkansaction.replace(R.id.container, fragmeffnt);
                            fragmentTrkkansaction.commit();
                            return true;
                    }
                    return false;
                }
            };

    private void initView() {

        mBtnBottomNavigation = findViewById(R.id.bottom_navigation);
    }
}