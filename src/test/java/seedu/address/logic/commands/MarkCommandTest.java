package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;

public class MarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Interview interviewToMark = model.getFilteredInterviewList().get(INDEX_FIRST.getZeroBased());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_INTERVIEW_SUCCESS,
                Messages.formatInterview(interviewToMark));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Interview doneInterview = new Interview(
                interviewToMark.getInterviewApplicant(),
                interviewToMark.getJobRole(),
                interviewToMark.getInterviewTiming(),
                true);
        expectedModel.setInterview(interviewToMark, doneInterview);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicantList().size() + 1);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }
}
