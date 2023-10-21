package seedu.address.model.interview;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.applicant.Applicant;

/**
 * Represents an Interview in the address book.
 */
public class Interview {

    /** TODO Change from 'String' to 'Applicant' once Applicant is on master*/
    private final Applicant applicant;
    private final String jobRole;
    /** TODO Change from 'String' to proper 'Date/Time' once natural DT is implemented*/
    private final String interviewTiming;
    private final boolean isDone;

    /**
     * Default constructor for Interview object.
     */
    public Interview(Applicant app, String role, String timing) {
        requireAllNonNull(app, role, timing);
        applicant = app;
        jobRole = role;
        interviewTiming = timing;
        isDone = false;
    }

    /**
     * Alternative constructor for creating Interview object from storage.
     */
    public Interview(Applicant app, String role, String timing, boolean isDone) {
        requireAllNonNull(app, role, timing, isDone);
        applicant = app;
        jobRole = role;
        interviewTiming = timing;
        this.isDone = isDone;
    }

    /**
     * Returns true if both Interviews have the same Applicant & Timing or if both Interviews are the same object
     * Adapted from AB3's Person.isSamePerson() method
     */
    public boolean isNotValidOrNewInterview(Interview otherInterview) {
        if (otherInterview == this) {
            return true;
        }

        return otherInterview != null
                && otherInterview.getInterviewTiming().equals(getInterviewTiming())
                && otherInterview.getInterviewApplicant().equals(getInterviewApplicant());
    }

    /* TODO Update return type from String to Applicant */
    public Applicant getInterviewApplicant() {
        return applicant;
    }

    public String getJobRole() {
        return jobRole;
    }


    public String getInterviewTiming() {
        return interviewTiming;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns true if both interviews have the same identity and data fields.
     * This defines a stronger notion of equality between two interviews
     * Mostly used for testing purposes
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Interview)) {
            return false;
        }

        Interview otherInterview = (Interview) other;
        return applicant.equals(otherInterview.applicant)
                && jobRole.equals(otherInterview.jobRole)
                && interviewTiming.equals(otherInterview.interviewTiming)
                && isDone == otherInterview.isDone;
    }
}
