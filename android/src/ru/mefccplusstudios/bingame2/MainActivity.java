package ru.mefccplusstudios.bingame2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {
    private long tr=0;
    public InviteGDX rendr;
    private boolean debug = false;
    private Banner banka;
    public StartAppAd exitable, recreward;
    public StartAppAd reward = null;
    //public boolean closetta = false;
    public boolean adsprep = false, rewprep = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(debug) StartAppSDK.setTestAdsEnabled(true); else StartAppSDK.init(this, "205732080", false);
        StartAppAd.disableSplash();
        banka = findViewById(R.id.stban1);
        exitable = new StartAppAd(this);
        recreward = new StartAppAd(this);
        recreward.loadAd(new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                rewprep = true;
            }
            @Override
            public void onFailedToReceiveAd(Ad ad) {
                rewprep = false;
            }
        });
        banka.loadAd();
        loadAd();
        prepRewardedVideo();
        rendr = new InviteGDX();
        ((InviteGDX) rendr).attach(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_root, rendr).commit();

    }
    @Override
    public void onBackPressed() {
        if(tr+2000>System.currentTimeMillis()) {
            StartAppAd.onBackPressed(this);
            super.onBackPressed();
        } else {
            Toast toast = Toast.makeText(this, "Please, press back once more to exit...", Toast.LENGTH_LONG);
            toast.show();
            tr = System.currentTimeMillis();
        }
    }
    public void loadAd() {
        exitable.loadAd(new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                adsprep = true;
            }
            @Override
            public void onFailedToReceiveAd(Ad ad) {
                adsprep = false;
            }
        });
    }
    public void prepRewardedVideo() {
        StartAppAd rewardedVideo = new StartAppAd(this);
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                reward = rewardedVideo;
            }
            @Override
            public void onFailedToReceiveAd(Ad ad) {
                reward = null;
            }
        });
    }
    @Override
    public void exit() {}
}