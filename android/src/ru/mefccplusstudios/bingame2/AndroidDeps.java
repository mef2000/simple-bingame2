package ru.mefccplusstudios.bingame2;

import android.widget.Toast;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;

import ru.mefccplusstudios.main.Dependable;

public class AndroidDeps extends Dependable {
    public MainActivity mact;
    //public AndroidDeps(MainActivity mact) {
    //    this.mact = mact;
   // }
    public void attach(MainActivity mact) { this.mact=mact;}
    @Override
    public void showAd(int type) {
        mact.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Ads called.");
                if(mact.reward != null) {
                    mact.reward.setVideoListener(new VideoListener() {
                        @Override
                        public void onVideoCompleted() {
                            Dependable.ADS_VIEWED = true;
                            Dependable.ADS_CLOSETTA = true;
                            mact.prepRewardedVideo();
                        }
                    });
                    mact.reward.showAd();
                } else if(mact.rewprep) {
                    mact.recreward.showAd(new AdDisplayListener() {
                        @Override
                        public void adHidden(Ad ad) {
                            Dependable.ADS_VIEWED = true;
                            Dependable.ADS_CLOSETTA = true;
                            System.out.println("ADS CLOSED");
                        }
                        @Override
                        public void adDisplayed(Ad ad) {
                            //System.out.println("ADS SHOWED");
                            //adsviewed = true;
                            //root.mact.closetta = true;
                        }
                        @Override
                        public void adClicked(Ad ad) {
                            //adsviewed = true;
                            //root.mact.closetta = true;
                            //System.out.println("ADS CLICKED");
                        }
                        @Override
                        public void adNotDisplayed(Ad ad) {
                            mact.rendr.game.ts.clearStage();
                            mact.prepRewardedVideo();
                            //System.out.println("ADS ERROR");
                            Toast.makeText(mact, mact.rendr.game.global.localizer.get("adsproblem"), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    mact.rendr.game.ts.clearStage();
                    mact.prepRewardedVideo();
                    Toast.makeText(mact, mact.rendr.game.global.localizer.get("adsproblem"), Toast.LENGTH_LONG).show();
                }
            }});
    }
}
