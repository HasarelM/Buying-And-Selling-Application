package com.dev.hasarelm.buyingselling.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dev.hasarelm.buyingselling.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SellerHomeActivity extends AppCompatActivity {

    //Ui components
    private BottomNavigationView mBtnBottomNavigation;
    private FloatingActionButton mFabCreateAdd;
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

        mFabCreateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SellerHomeActivity.this,SellerAddActivity.class);
                startActivity(intent);

            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.seller_nav_home:
                            Fragment fragment = new SellerHomeFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment);
                            fragmentTransaction.commit();
                            return true;

                        case R.id.seller_nav_history:
                            Fragment frament = new SellerHistoryFragment();
                            FragmentManager fragmentManageer = getSupportFragmentManager();
                            FragmentTransaction fragmentTrsaction = fragmentManageer.beginTransaction();
                            fragmentTrsaction.replace(R.id.container, frament);
                            fragmentTrsaction.commit();
                            return true;
                        case R.id.seller_nav_notification:
                            Fragment fragmeffnt = new SellerNotificationFragment();
                            FragmentManager fragmentManahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkansaction = fragmentManahhger.beginTransaction();
                            fragmentTrkkansaction.replace(R.id.container, fragmeffnt);
                            fragmentTrkkansaction.commit();
                            return true;

                        case R.id.seller_nav_profile:
                            Fragment fragmefddfnt = new SellerProfileFragment();
                            FragmentManager fragmentMssanahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkddansaction = fragmentMssanahhger.beginTransaction();
                            fragmentTrkkddansaction.replace(R.id.container, fragmefddfnt);
                            fragmentTrkkddansaction.commit();
                            return true;
                    }
                    return false;
                }
            };

    private void initView() {

        mBtnBottomNavigation = findViewById(R.id.bottom_navigation);
        mFabCreateAdd = findViewById(R.id.fragment_fab_home_request_button);
    }
}