package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.Interview;

/**
 * Parses input arguments and creates a new AddCommand object
 * Adapted from AB3's AddCommandParser class
 */
public class AddInterviewCommandParser implements Parser<AddInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewCommand
     * and returns an AddInterviewCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform the
     *     expected format
     */
    public AddInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPLICANT, PREFIX_JOB_ROLE, PREFIX_TIMING);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPLICANT, PREFIX_JOB_ROLE, PREFIX_TIMING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_APPLICANT, PREFIX_JOB_ROLE, PREFIX_TIMING);
        String applicant = argMultimap.getValue(PREFIX_APPLICANT).get().trim();
        String jobRole = argMultimap.getValue(PREFIX_JOB_ROLE).get().trim();
        String timing = argMultimap.getValue(PREFIX_TIMING).get().trim();

        Interview interview = new Interview(applicant, jobRole, timing);

        return new AddInterviewCommand(interview);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}