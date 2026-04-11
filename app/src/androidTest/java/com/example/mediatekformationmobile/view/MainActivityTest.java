package com.example.mediatekformationmobile.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mediatekformationmobile.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testNavigationApplication() throws InterruptedException {
        // accès aux formations
        onView(withId(R.id.btnFormations)).perform(click());
        Thread.sleep(4000);

        // ouverture détail
        // clic sur une formation connue
        onView(withText("test")).perform(click());
        Thread.sleep(3000);

        // retour liste
        pressBack();
        Thread.sleep(1000);

        // retour menu
        pressBack();
        Thread.sleep(1000);

        // accès favoris
        onView(withId(R.id.btnFavoris)).perform(click());
        Thread.sleep(3000);

        // retour accueil
        pressBack();
        Thread.sleep(1000);
    }
}
