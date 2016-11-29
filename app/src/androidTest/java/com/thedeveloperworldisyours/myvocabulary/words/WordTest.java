package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.thedeveloperworldisyours.myvocabulary.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by javierg on 22/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class WordTest {

    private final static String TITLE1 = "TITLE1";

    private final static String DESCRIPTION = "DESCR";

    private final static String TITLE2 = "TITLE2";

    @Rule
    public ActivityTestRule<WordsActivity> mWordsActivityTestRule =
            new ActivityTestRule<WordsActivity>(WordsActivity.class) {

                /**
                 * To avoid a long list of tasks and the need to scroll through the list to find a
                 * task, we call  WordsDataSource#deleteAllWords before each test.
                 */
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    // Doing this in @Before generates a race condition.
                    Injection.provideWordsRepository(InstrumentationRegistry.getTargetContext())
                            .deleteAllWords();
                }
            };

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(ListView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    @Test
    public void clickAddTaskButton_opensAddWordUi() {
        // Click on the add task button
        onView(withId(R.id.words_act_fab_add_task)).perform(click());

        // Check if the add task screen is displayed
        onView(withId(R.id.add_word_frag_title)).check(matches(isDisplayed()));
    }

    @Test
    public void editWord(){
        createWord(TITLE1, DESCRIPTION);

        onView(withText(TITLE1)).perform(click());

        onView(withId(R.id.word_detail_act_fab_edit)).perform(click());

        onView(withId(R.id.add_word_frag_title)).perform(replaceText(TITLE2), closeSoftKeyboard());
        onView(withId(R.id.add_word_frag_description)).perform(replaceText("cha"), closeSoftKeyboard());

        onView(withId(R.id.add_edit_word_act_done)).perform(click());

        onView(withText(TITLE2)).check(matches(isDisplayed()));

        onView(withText(TITLE1)).check(doesNotExist());

    }

    @Test
    public void addWordList() {
        createWord(TITLE1, DESCRIPTION);

        onView(withText(TITLE1)).check(matches(isDisplayed()));
    }

    @Test
    public void markWordAsLearned() {
        viewAllWord();

        createWord(TITLE1, DESCRIPTION);

        onView(withId(R.id.words_list_item_learned)).perform(click());

        clickCheckBoxForWord(TITLE1);

        viewActiveWord();
        onView(withText(TITLE1)).check(matches(not(isDisplayed())));

        viewLearnedWord();
        onView(withText(TITLE1)).check(matches(isDisplayed()));

    }

    @Test
    public void markWordAsActive() {

        viewAllWord();
        createWord(TITLE2, DESCRIPTION);
        clickCheckBoxForWord(TITLE2);
        clickCheckBoxForWord(TITLE2);
        viewAllWord();
        onView(withText(TITLE2)).check(matches(isDisplayed()));

        viewActiveWord();
        onView(withText(TITLE2)).check(matches(isDisplayed()));

        viewLearnedWord();
        onView(withText(TITLE2)).check(matches(not(isDisplayed())));

    }

    @Test
    public void showAllWords() {
        createWord(TITLE2, DESCRIPTION);
        createWord(TITLE1, DESCRIPTION);

        viewAllWord();

        onView(withText(TITLE2)).check(matches(isDisplayed()));
        onView(withText(TITLE1)).check(matches(isDisplayed()));

    }

    @Test
    public void showActiveWords() {
        createWord(TITLE1, DESCRIPTION);
        createWord(TITLE2, DESCRIPTION);

        viewActiveWord();
        onView(withText(TITLE1)).check(matches(isDisplayed()));
        onView(withText(TITLE2)).check(matches(isDisplayed()));

    }

    @Test
    public void showLearnWords() {
        createWord(TITLE1, DESCRIPTION);
        createWord(TITLE2, DESCRIPTION);

        viewAllWord();

        clickCheckBoxForWord(TITLE1);
        clickCheckBoxForWord(TITLE2);

        viewLearnedWord();

        onView(withText(TITLE1)).check(matches(isDisplayed()));
        onView(withText(TITLE2)).check(matches(isDisplayed()));

    }

    @Test
    public void clearLearnedWords() {
        createWord(TITLE1, DESCRIPTION);
        createWord(TITLE2, DESCRIPTION);

        clickCheckBoxForWord(TITLE1);
        clickCheckBoxForWord(TITLE2);

        onView(withText(TITLE1)).check(matches(isDisplayed()));
        onView(withText(TITLE2)).check(matches(isDisplayed()));

        openActionBarOverflowOrOptionsMenu(getTargetContext());
        onView(withText(R.string.menu_clear)).perform(click());

        onView(withText(TITLE1)).check(matches(not(isDisplayed())));
        onView(withText(TITLE2)).check(matches(not(isDisplayed())));
    }

    @Test
    public void createOneWord_deleteWord() {
        createWord(TITLE1, DESCRIPTION);
        viewAllWord();
        onView(withText(TITLE1)).check(matches(isDisplayed()));
        clickCheckBoxForWord(TITLE1);

        viewLearnedWord();

        openActionBarOverflowOrOptionsMenu(getTargetContext());
        onView(withText(R.string.menu_clear)).perform(click());
        viewAllWord();
        onView(withText(TITLE1)).check(matches(not(isDisplayed())));

    }

    @Test
    public void createTwoWord_deleteOnlyOneWord(){
        createWord(TITLE1, DESCRIPTION);
        createWord(TITLE2, DESCRIPTION);

        onView(withText(TITLE1)).check(matches(isDisplayed()));
        onView(withText(TITLE2)).check(matches(isDisplayed()));
        clickCheckBoxForWord(TITLE1);
        viewLearnedWord();
        onView(withText(TITLE1)).check(matches(isDisplayed()));
        openActionBarOverflowOrOptionsMenu(getTargetContext());
        onView(withText(R.string.menu_clear)).perform(click());

        onView(withText(TITLE1)).check(matches(not(isDisplayed())));
        viewAllWord();
        onView(withText(TITLE2)).check(matches((isDisplayed())));

    }

    public void clickCheckBoxForWord(String title) {
        onView(allOf(withId(R.id.words_list_item_learned), hasSibling(withText(title)))).perform(click());
    }


    public void viewAllWord() {
        onView(withId(R.id.menu_filter)).perform(click());

        onView(withId(R.id.words_frag_all_button)).perform(click());
    }

    public void viewActiveWord() {
        onView(withId(R.id.menu_filter)).perform(click());

        onView(withId(R.id.words_frag_active_button)).perform(click());
    }

    public void viewLearnedWord() {
        onView(withId(R.id.menu_filter)).perform(click());

        onView(withId(R.id.words_frag_learned_button)).perform(click());
    }

    public void createWord(String title, String description) {
        onView(withId(R.id.words_act_fab_add_task)).perform(click());

        onView(withId(R.id.add_word_frag_title)).perform(typeText(title));

        onView(withId(R.id.add_word_frag_description)).perform(typeText(description));

        onView(withId(R.id.add_edit_word_act_done)).perform(click());

    }

}
