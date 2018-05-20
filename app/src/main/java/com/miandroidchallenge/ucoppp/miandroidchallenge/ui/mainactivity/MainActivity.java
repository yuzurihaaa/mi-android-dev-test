package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.miandroidchallenge.ucoppp.miandroidchallenge.R;
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsFragment;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragment;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.unselecteddetailsfragment.UnselectedDetailsFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements OnDeliveryCallback {

    private static final String DELIVERY_STATE = "delivery_state";

    private boolean isTablet = false;

    private int screenId = R.id.fragment_list_holder;

    private DeliveriesModel delivery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeTitle(R.string.things_to_deliver);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (savedInstanceState != null) {
            delivery = savedInstanceState.getParcelable(DELIVERY_STATE);
        }

        if (isTablet) {
            screenId = R.id.fragment_details_holder;
            replaceFragment(R.id.fragment_list_holder, DeliveryFragment.newInstance());
            if (savedInstanceState != null && delivery != null) {

                replaceFragment(screenId, DeliveryDetailsFragment.newInstance(delivery));
            } else {

                replaceFragment(screenId, UnselectedDetailsFragment.newInstance());
            }
        } else {
            replaceFragment(R.id.fragment_list_holder, DeliveryFragment.newInstance());
            if (savedInstanceState != null && delivery != null) {

                replaceFragmentWithPop(R.id.fragment_list_holder, DeliveryDetailsFragment.newInstance(delivery));
            }
        }

        Button buttonRetry = findViewById(R.id.button_retry);

        buttonRetry.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_list_holder);
            if (fragment instanceof DeliveryFragment) {
                ((DeliveryFragment) fragment).callApi();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DELIVERY_STATE, delivery);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        delivery = savedInstanceState.getParcelable(DELIVERY_STATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_list_holder);
        if (fragment instanceof DeliveryFragment) {
            delivery = null;
        }
    }

    public void changeTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onClickDelivery(@NotNull DeliveriesModel delivery) {
        this.delivery = delivery;
        if (isTablet) {
            replaceFragment(screenId, DeliveryDetailsFragment.newInstance(delivery));
        } else {
            changeTitle(R.string.delivery_details);
            replaceFragmentWithPop(screenId, DeliveryDetailsFragment.newInstance(delivery));
        }

    }

    @Override
    public void onFailCallDelivery() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Sorry!");
        alertDialog.setMessage("We failed to get the data");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }

    private void replaceFragment(int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }

    private void replaceFragmentWithPop(int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit();
    }
}
