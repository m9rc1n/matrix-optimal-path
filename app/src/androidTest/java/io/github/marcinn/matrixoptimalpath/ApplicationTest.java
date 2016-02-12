package io.github.marcinn.matrixoptimalpath;

import android.content.Context;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.marcinn.matrixoptimalpath.model.WordCell;
import io.github.marcinn.matrixoptimalpath.view.MainActivity;
import io.github.marcinn.matrixoptimalpath.view.TableAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((DiscreteSeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(DiscreteSeekBar.class);
            }
        };
    }

    @Test
    public void testIfPreferenceFragmentWillShowUp() throws Exception {
        checkPreferenceFragmentMatching(matches(not(isDisplayed())));
        checkIfKeyboardIsClosed();
        checkTableFragmentMatching(matches(isDisplayed()));

        onView(withId(R.id.action_preference)).perform(click());
        checkPreferenceFragmentMatching(matches(isDisplayed()));
        checkTableFragmentMatching(matches(isDisplayed()));

        onView(withId(R.id.action_preference)).perform(click());
        checkIfKeyboardIsClosed();
        checkPreferenceFragmentMatching(matches(not(isDisplayed())));
        checkIfKeyboardIsClosed();
        checkTableFragmentMatching(matches(isDisplayed()));

        onView(withId(R.id.action_preference)).perform(click());

        onView(withId(R.id.action_preference)).perform(click());
        checkPreferenceFragmentMatching(matches(not(isDisplayed())));
        checkIfKeyboardIsClosed();
        checkTableFragmentMatching(matches(isDisplayed()));
    }

    @Test
    public void testIfRecyclerViewIsFilledProperly() throws Exception {
        onView(withId(R.id.action_preference)).perform(click());
        onView(withId(R.id.editText_inputTable)).perform(click()).perform(typeText("a b c d e"));
        WordCell c0 = new WordCell("a", 0);
        WordCell c1 = new WordCell("b", 1);
        WordCell c2 = new WordCell("c", 2);
        WordCell c3 = new WordCell("d", 3);
        WordCell c4 = new WordCell("e", 4);
        onView((withId(R.id.recyclerView_table))).perform(scrollToHolder(new TableViewHolderMatcher())
                .atPosition(0));
        onView(withText(c0.toString())).check(matches(isDisplayed()));
        onView((withId(R.id.recyclerView_table))).perform(scrollToHolder(new TableViewHolderMatcher())
                .atPosition(1));
        onView(withText(c1.toString())).check(matches(isDisplayed()));
        onView((withId(R.id.recyclerView_table))).perform(scrollToHolder(new TableViewHolderMatcher())
                .atPosition(2));
        onView(withText(c2.toString())).check(matches(isDisplayed()));
        onView((withId(R.id.recyclerView_table))).perform(scrollToHolder(new TableViewHolderMatcher())
                .atPosition(3));
        onView(withText(c3.toString())).check(matches(isDisplayed()));
        onView((withId(R.id.recyclerView_table))).perform(scrollToHolder(new TableViewHolderMatcher())
                .atPosition(4));
        onView(withText(c4.toString())).check(matches(isDisplayed()));
    }

    @Test
    public void testWithAddTextButton() throws Exception {
        onView(withId(R.id.action_preference)).perform(click());
        onView(withId(R.id.imageButton_appendText)).perform(click());
        onView(withId(R.id.seekBar_columnsNumber)).perform(setProgress(10));

        WordCell c0 = new WordCell("non", 232);
        onView(withItemText(c0.toString())).check(doesNotExist());
        onView((withId(R.id.recyclerView_table))).perform(scrollToPosition(999));
        onView(withItemText(c0.toString())).check(matches(isDisplayed()));

        onView(withId(R.id.action_preference)).perform(click());
        onView(withId(R.id.action_preference)).perform(click());

        onView(withId(R.id.imageButton_appendText)).perform(click());
        onView(withId(R.id.seekBar_columnsNumber)).perform(setProgress(20));

        WordCell c1 = new WordCell("non", 204);
        onView(withItemText(c1.toString())).check(doesNotExist());
        onView((withId(R.id.recyclerView_table))).perform(scrollToPosition(1999));
        onView(withItemText(c1.toString())).check(matches(isDisplayed()));

    }

    private void checkIfKeyboardIsClosed() {
        InputMethodManager imm = (InputMethodManager) mActivityRule.getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        Assert.assertTrue("Keyboard should be closed in Table Fragment", !imm.isAcceptingText());
    }

    private void checkPreferenceFragmentMatching(ViewAssertion matches) throws Exception {
        onView(withId(R.id.editText_inputTable)).check(matches);
        onView(withId(R.id.seekBar_columnsNumber)).check(matches);
        onView(withId(R.id.textView_columnsNumber)).check(matches);
    }

    private void checkTableFragmentMatching(ViewAssertion matches) throws Exception {
        onView(withId(R.id.textView_statistics)).check(matches);
        onView(withId(R.id.recyclerView_table)).check(matches);
    }

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(isDescendantOfA(withId(R.id.recyclerView_table)), withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    private static class TableViewHolderMatcher extends TypeSafeMatcher<TableAdapter.ViewHolder> {
        private Matcher<View> itemMatcher = any(View.class);

        public TableViewHolderMatcher() { }

        public TableViewHolderMatcher(Matcher<View> itemMatcher) {
            this.itemMatcher = itemMatcher;
        }

        @Override
        public boolean matchesSafely(TableAdapter.ViewHolder viewHolder) {
            return TableAdapter.ViewHolder.class.isAssignableFrom(viewHolder.getClass())
                    && itemMatcher.matches(viewHolder.text);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("is assignable from CustomViewHolder");
        }
    }
}