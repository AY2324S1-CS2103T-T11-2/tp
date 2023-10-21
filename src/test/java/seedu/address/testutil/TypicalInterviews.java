package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.interview.Interview;

/**
 * A utility class containing a list of {@code Interview} objects to be used in tests.
 * Adapted from AB3 TypicalPersons class.
 */
public class TypicalInterviews {
    public static final Interview STANDARD_INTERVIEW = new Interview(TypicalApplicants.ALICE,
            "StandardJobRole", "StandardTime");
    public static final Interview STANDARD_INTERVIEW_2 = new Interview(TypicalApplicants.BENSON,
            "StandardJobRole2", "StandardTime2");
    public static final Interview STANDARD_INTERVIEW_3 = new Interview(TypicalApplicants.CARL,
            "StandardJobRole3", "StandardTime3");
    public static final Interview STANDARD_INTERVIEW_4 = new Interview(TypicalApplicants.DANIEL,
            "StandardJobRole4", "StandardTime4");
    public static final Interview STANDARD_INTERVIEW_DIFFERENT_ROLE = new Interview(TypicalApplicants.ALICE,
            "DifferentStandardJobRole", "StandardTime");
    public static final Interview STANDARD_INTERVIEW_DIFFERENT_APPLICANT =
            new Interview(TypicalApplicants.ELLE, "StandardJobRole", "StandardTime");
    public static final Interview STANDARD_INTERVIEW_DIFFERENT_TIME = new Interview(TypicalApplicants.ALICE,
            "StandardJobRole", "DifferentStandardTime");

    /** Prevents instantiation - Code reused and adapted from AB3 TypicalPersons.java */
    private TypicalInterviews() {}

    public static List<Interview> getTypicalInterviews() {
        return new ArrayList<>(Arrays.asList(STANDARD_INTERVIEW, STANDARD_INTERVIEW_2,
                STANDARD_INTERVIEW_3, STANDARD_INTERVIEW_4));
    }
}
