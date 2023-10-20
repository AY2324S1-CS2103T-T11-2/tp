package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.model.interview.Interview;

/**
 * A utility class for Interviews.
 */
public class InterviewUtil {

    /**
     * Returns an add command string for adding the {@code interview}.
     */
    public static String getAddCommand(Index index, Interview interview) {
        return AddInterviewCommand.COMMAND_WORD + " "
                + PREFIX_APPLICANT
                + index.getOneBased() + " "
                + getInterviewDetails(interview);
    }

    /**
     * Returns the part of command string for the given {@code interview}'s details.
     */
    public static String getInterviewDetails(Interview interview) {
        return PREFIX_JOB_ROLE + interview.getJobRole() + " "
                + PREFIX_TIMING + interview.getInterviewTiming();
    }
}
