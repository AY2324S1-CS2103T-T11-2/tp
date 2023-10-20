package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;

/**
 * Command to handle adding interviews to the address book.
 * Adapted from AB3's "AddCommand" class
 */
public class AddInterviewCommand extends Command {

    public static final String COMMAND_WORD = "add-i";

    /* TODO Update format with intended final format accepted*/
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an interview to the address book. "
            + "Parameters: "
            + PREFIX_APPLICANT + "APPLICANT_ID "
            + PREFIX_JOB_ROLE + "ROLE "
            + PREFIX_TIMING + "TIMING"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPLICANT + "18 "
            + PREFIX_JOB_ROLE + "Junior Software Engineer "
            + PREFIX_TIMING + "2023-10-24 18:00";

    public static final String MESSAGE_SUCCESS = "New interview added: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "Error: This is a duplicate interview";
    public static final String MESSAGE_APPLICANT_HAS_INTERVIEW =
            "Applicant already has an interview scheduled";

    private final Index applicantIndex;
    private final String jobRole;
    private final String timing;

    /**
     * Creates an AddInterviewCommand to add the specified {@code Interview}
     */
    public AddInterviewCommand(Index applicantIndex, String jobRole, String timing) {
        requireAllNonNull(applicantIndex, jobRole, timing);

        this.applicantIndex = applicantIndex;
        this.jobRole = jobRole;
        this.timing = timing;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Applicant> lastShownList = model.getFilteredApplicantList();

        if (applicantIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICANT_DISPLAYED_INDEX);
        }

        Applicant applicant = lastShownList.get(applicantIndex.getZeroBased());

        if (applicant.hasInterview()) {
            throw new CommandException(MESSAGE_APPLICANT_HAS_INTERVIEW);
        }

        Applicant applicantWithInterview = new Applicant(
                applicant.getName(),
                applicant.getPhone(),
                applicant.getEmail(),
                applicant.getAddress(),
                applicant.getTags(),
                true
        );

        Interview toAdd = new Interview(applicantWithInterview, jobRole, timing);

        if (model.hasInterview(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERVIEW);
        }

        model.setApplicant(applicant, applicantWithInterview);
        model.addInterview(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatInterview(toAdd)));
    }

    @Override
    public String toString() {
        return "AddInterviewCommand{"
                + "jobRole='" + jobRole + '\''
                + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInterviewCommand)) {
            return false;
        }

        AddInterviewCommand otherAddICommand = (AddInterviewCommand) other;
        return applicantIndex.equals(otherAddICommand.applicantIndex)
                && jobRole.equals(otherAddICommand.jobRole)
                && timing.equals(otherAddICommand.timing);
    }
}
