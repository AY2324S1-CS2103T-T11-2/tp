package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;

/**
 * A utility class containing an address book with a list of {@code Applicant} objects
 * and list of {@code Interview} objects to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation
    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Applicant applicant : TypicalApplicants.getTypicalApplicants()) {
            ab.addApplicant(applicant);
        }
        for (Interview interview : TypicalInterviews.getTypicalInterviews()) {
            ab.addInterview(interview);
        }
        return ab;
    }
}
