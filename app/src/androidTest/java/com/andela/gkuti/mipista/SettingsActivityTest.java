package com.andela.gkuti.mipista;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {
    public SettingsActivityTest() {
        super(SettingsActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOnCreate() throws Exception {
        getActivity();
        onView(withText("Settings")).check(matches(isDisplayed()));
    }

    public void testDialog() throws Exception {
        getActivity();
        onView(withId(R.id.delay_button)).perform(click());
        onView(withId(R.id.okay)).perform(click());
    }
}