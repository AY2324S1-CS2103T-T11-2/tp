package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INTERVIEWS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.JobContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindInterviewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        JobContainsKeywordsPredicate firstPredicate =
                new JobContainsKeywordsPredicate(Collections.singletonList("first"));
        JobContainsKeywordsPredicate secondPredicate =
                new JobContainsKeywordsPredicate(Collections.singletonList("second"));

        FindInterviewCommand findFirstCommand = new FindInterviewCommand(firstPredicate);
        FindInterviewCommand findSecondCommand = new FindInterviewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindInterviewCommand findFirstCommandCopy = new FindInterviewCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noInterviewFound() {
        String expectedMessage = String.format(MESSAGE_INTERVIEWS_LISTED_OVERVIEW, 0);
        JobContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindInterviewCommand command = new FindInterviewCommand(predicate);
        expectedModel.updateFilteredInterviewList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredInterviewList());
    }

    @Test
    public void toStringMethod() {
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindInterviewCommand findInterviewCommand = new FindInterviewCommand(predicate);
        String expected = FindInterviewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findInterviewCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private JobContainsKeywordsPredicate preparePredicate(String userInput) {
        return new JobContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
