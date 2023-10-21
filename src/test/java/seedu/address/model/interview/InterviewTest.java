package seedu.address.model.interview;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalInterviews.STANDARD_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.STANDARD_INTERVIEW_2;
import static seedu.address.testutil.TypicalInterviews.STANDARD_INTERVIEW_DIFFERENT_APPLICANT;
import static seedu.address.testutil.TypicalInterviews.STANDARD_INTERVIEW_DIFFERENT_ROLE;
import static seedu.address.testutil.TypicalInterviews.STANDARD_INTERVIEW_DIFFERENT_TIME;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Interview object methods
 * Adapted from AB3 PersonTest class
 */
public class InterviewTest {

    @Test
    public void isNotValidOrNewInterview() {
        // same object -> returns true
        assertTrue(STANDARD_INTERVIEW.isNotValidOrNewInterview(STANDARD_INTERVIEW));

        // null -> returns false
        assertFalse(STANDARD_INTERVIEW.isNotValidOrNewInterview(null));

        // same Applicant and time, all other attributes different -> returns true
        assertTrue(STANDARD_INTERVIEW.isNotValidOrNewInterview(STANDARD_INTERVIEW_DIFFERENT_ROLE));

        // different Applicant, all other attributes same -> returns false
        assertFalse(STANDARD_INTERVIEW.isNotValidOrNewInterview(STANDARD_INTERVIEW_DIFFERENT_APPLICANT));

        // different Time, all other attributes same -> return false
        assertFalse(STANDARD_INTERVIEW.isNotValidOrNewInterview(STANDARD_INTERVIEW_DIFFERENT_TIME));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Interview standardInterviewCopy = new Interview(STANDARD_INTERVIEW.getInterviewApplicant(),
                STANDARD_INTERVIEW.getJobRole(), STANDARD_INTERVIEW.getInterviewTiming(),
                STANDARD_INTERVIEW.isDone());
        assertTrue(STANDARD_INTERVIEW.equals(standardInterviewCopy));

        // same object -> returns true
        assertTrue(STANDARD_INTERVIEW.equals(STANDARD_INTERVIEW));

        // null -> returns false
        assertFalse(STANDARD_INTERVIEW.equals(null));

        // different type -> returns false
        assertFalse(STANDARD_INTERVIEW.equals(5));

        // different person -> returns false
        assertFalse(STANDARD_INTERVIEW.equals(STANDARD_INTERVIEW_2));

        // different Applicant -> returns false
        Interview editedStandardinterview = new Interview(STANDARD_INTERVIEW_2.getInterviewApplicant(),
                STANDARD_INTERVIEW.getJobRole(), STANDARD_INTERVIEW.getInterviewTiming());
        assertFalse(STANDARD_INTERVIEW.equals(editedStandardinterview));

        // different job role -> returns false
        editedStandardinterview = new Interview(STANDARD_INTERVIEW.getInterviewApplicant(),
                STANDARD_INTERVIEW_2.getJobRole(), STANDARD_INTERVIEW.getInterviewTiming());
        assertFalse(STANDARD_INTERVIEW.equals(editedStandardinterview));

        // different Timing -> returns false
        editedStandardinterview = new Interview(STANDARD_INTERVIEW.getInterviewApplicant(),
                STANDARD_INTERVIEW.getJobRole(), STANDARD_INTERVIEW_2.getInterviewTiming());
        assertFalse(STANDARD_INTERVIEW.equals(editedStandardinterview));
    }
}
