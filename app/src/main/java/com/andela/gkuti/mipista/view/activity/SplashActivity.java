package com.andela.gkuti.mipista.view.activity;

import android.content.Intent;

import com.andela.gkuti.mipista.R;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * SplashActivity class
 */
public class SplashActivity extends AwesomeSplash {
    /**
     * initilizes the splash screen
     */
    @Override
    public void initSplash(ConfigSplash configSplash) {
        getSupportActionBar().setTitle("");
        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);
        configSplash.setLogoSplash(R.drawable.ic_shoe);
        configSplash.setAnimLogoSplashDuration(2000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);
        configSplash.setTitleSplash("Mi Pista");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("Secrets.ttf");
    }

    /**
     * called when the splash screen has finished
     */
    @Override
    public void animationsFinished() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}