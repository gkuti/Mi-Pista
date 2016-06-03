package com.andela.gkuti.mipista;

import android.test.ActivityInstrumentationTestCase2;

import com.andela.gkuti.mipista.view.activity.MainActivity;
import com.andela.gkuti.mipista.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class TrackingTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public TrackingTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testTracking() throws Exception {
        getActivity();
        onView(withText("Mi Pista")).check(matches(isDisplayed()));
        onView(withText("NOT TRACKING")).check(matches(isDisplayed()));
        onView(withId(R.id.action_settings)).perform(click());
        onView(withId(R.id.delay_button)).perform(click());
        onView(withId(R.id.okay)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.tracking_button)).perform(click());
        onView(withText("TRACKING")).check(matches(isDisplayed()));
        Thread.sleep(60000);
        onView(withId(R.id.tracking_button)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.action_history)).perform(click());
        Thread.sleep(2000);
        onView(withText(Date.getDate())).check(matches(isDisplayed()));
        onView(withText("Moleye St Lagos Mainland Lagos Nigeria")).perform(click());
        Thread.sleep(3000);
    }
}