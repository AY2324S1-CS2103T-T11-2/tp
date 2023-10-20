package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interview.Interview;

/**
 * Marks interview as done.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as done the interview identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARK_INTERVIEW_SUCCESS = "Marked Interview: %s as done.";
    public static final String MESSAGE_ALREADY_DONE = "Interview: %s is already done.";
    private final Index targetIndex;

    /**
     * @param targetIndex of the applicant in the filtered applicant list to edit
     */
    public MarkCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToMark = lastShownList.get(targetIndex.getZeroBased());

        if (interviewToMark.isDone()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_DONE,
                    Messages.formatInterview(interviewToMark)));
        }

        Interview markedInterview = new Interview(
                interviewToMark.getInterviewApplicant(),
                interviewToMark.getJobRole(),
                interviewToMark.getInterviewTiming(),
                true
        );
        model.setInterview(interviewToMark, markedInterview);
        model.updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
        return new CommandResult(
                String.format(MESSAGE_MARK_INTERVIEW_SUCCESS, Messages.formatInterview(interviewToMark)));
    }
}
