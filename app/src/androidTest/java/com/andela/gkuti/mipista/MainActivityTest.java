package com.andela.gkuti.mipista;

import android.test.ActivityInstrumentationTestCase2;

import com.andela.gkuti.mipista.view.activity.MainActivity;
import com.andela.gkuti.mipista.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOnCreate() throws Exception {
        getActivity();
        onView(withText("Mi Pista")).check(matches(isDisplayed()));
        onView(withText("NOT TRACKING")).check(matches(isDisplayed()));
    }

    public void testTrackerButton() throws Exception {
        getActivity();
        onView(withId(R.id.tracking_button)).perform(click());
        onView(withText("TRACKING")).check(matches(isDisplayed()));
        onView(withId(R.id.tracking_button)).perform(click());
        onView(withText("NOT TRACKING")).check(matches(isDisplayed()));
    }

    public void testHistoryButton() throws Exception {
        getActivity();
        onView(withId(R.id.action_history)).perform(click());
        onView(withText(Date.getDate())).check(matches(isDisplayed()));
    }

    public void testSettingsButton() throws Exception {
        getActivity();
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
    }
}