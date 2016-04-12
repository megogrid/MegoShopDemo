package com.megogrid.megoshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.merchandising.handler.MeInappUtility;
import com.megogrid.merchandising.utility.MePreference;


/**
 * Created by divya on 8/2/16.
 */
public class ConnectActivity extends Activity {

    RelativeLayout card1, card2, card3;
    AuthorisedPreference authorisedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_main);
        authorisedPreference = new AuthorisedPreference(ConnectActivity.this);
//        System.out.println("chcking the pref get" + " " + authorisedPreference.getMeUserConfig());
        initView();
    }

    private void initView() {

        card1 = (RelativeLayout) findViewById(R.id.rl_card1);
        card2 = (RelativeLayout) findViewById(R.id.rl_card2);
        card3 = (RelativeLayout) findViewById(R.id.rl_card3);

        card1.setOnClickListener(mClick);
        card2.setOnClickListener(mClick);
        card3.setOnClickListener(mClick);


    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == card1.getId()) {
//                authorisedPreference.clear();
                MePreference.getInstance(ConnectActivity.this).clearData();
                MeInappUtility.getInstance().dropRecreateAllTable(ConnectActivity.this);
//                authorisedPreference.setMeUserConfig("pro1");
                Toast.makeText(ConnectActivity.this, "Please relaunch application", Toast.LENGTH_SHORT).show();
                finish();

            } else if (id == card2.getId()) {
//                authorisedPreference.clear();
                MePreference.getInstance(ConnectActivity.this).clearData();
                MeInappUtility.getInstance().dropRecreateAllTable(ConnectActivity.this);
//                authorisedPreference.setMeUserConfig("pro2");
                Toast.makeText(ConnectActivity.this, "Please relaunch application", Toast.LENGTH_SHORT).show();
                finish();
            } else if (id == card3.getId()) {
//                authorisedPreference.clear();
                MePreference.getInstance(ConnectActivity.this).clearData();
                MeInappUtility.getInstance().dropRecreateAllTable(ConnectActivity.this);
//                authorisedPreference.setMeUserConfig("pro3");
                Toast.makeText(ConnectActivity.this, "Please relaunch application", Toast.LENGTH_SHORT).show();
                finish();
            }
    }
    };
}
