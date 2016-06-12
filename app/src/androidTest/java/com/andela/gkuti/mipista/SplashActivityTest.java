package com.andela.gkuti.mipista;

import android.test.ActivityInstrumentationTestCase2;

import com.andela.gkuti.mipista.view.activity.SplashActivity;

public class SplashActivityTest extends ActivityInstrumentationTestCase2<SplashActivity> {
    public SplashActivityTest() {
        super(SplashActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOnCreate() throws Exception {
        getActivity();
    }
}