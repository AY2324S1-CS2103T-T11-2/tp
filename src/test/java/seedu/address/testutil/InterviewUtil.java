package seedu.address.testutil;

import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.model.interview.Interview;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

/**
 * A utility class for Interviews.
 */
public class InterviewUtil {

    /**
     * Returns an add command string for adding the {@code interview}.
     */
    public static String getAddCommand(Interview interview) {
        return AddInterviewCommand.COMMAND_WORD + " " + getInterviewDetails(interview);
    }

    /**
     * Returns the part of command string for the given {@code interview}'s details.
     */
    public static String getInterviewDetails(Interview interview) {
        return PREFIX_APPLICANT + interview.getInterviewApplicant() + " "
                + PREFIX_JOB_ROLE + interview.getJobRole() + " "
                + PREFIX_TIMING + interview.getInterviewTiming();
    }
}