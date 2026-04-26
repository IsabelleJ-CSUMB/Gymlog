package com.example.hw04_gymlog_v300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLogEntry() {
        // Type exercise
        onView(withId(R.id.exerciseInputEditText))
                .perform(typeText("Bench Press"), closeSoftKeyboard());

        // Type weight
        onView(withId(R.id.weightInputEditText))
                .perform(typeText("135"), closeSoftKeyboard());

        // Type reps
        onView(withId(R.id.repInputEditText))
                .perform(typeText("10"), closeSoftKeyboard());

        // Click the Log button
        onView(withId(R.id.logButton)).perform(click());

        // Verify the display TextView contains the expected text
        onView(withId(R.id.logDisplayRecyclerView))
                .check(matches(withText(containsString("Bench Press"))));
    }
}
