package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;

/**
 * Jackson-friendly version of {@link seedu.address.model.interview.Interview}.
 * Adapted from AB3's JsonAdaptedPerson
 */
class JsonAdaptedInterview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";
    public static final String APPLICANT_MISSING = "Applicant";
    public static final String JOB_ROLE_MISSING = "Job role";
    public static final String TIMING_MISSING = "Timing";

    private final JsonAdaptedApplicant applicant;
    private final String jobRole;
    private final String interviewStartTime;
    private final String interviewEndTime;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedInterview} with the given interview details.
     */
    @JsonCreator
    public JsonAdaptedInterview(@JsonProperty("applicant") JsonAdaptedApplicant applicant,
                                @JsonProperty("jobRole") String jobRole,
                                @JsonProperty("interviewStartTime") String interviewStartTime,
                                @JsonProperty("interviewEndTime") String interviewEndTime,
                                @JsonProperty("isDone") boolean isDone) {
        this.applicant = applicant;
        this.jobRole = jobRole;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Interview} into this class for Jackson use.
     */
    public JsonAdaptedInterview(Interview source) {
        applicant = new JsonAdaptedApplicant(source.getInterviewApplicant());
        jobRole = source.getJobRole();
        interviewStartTime = source.getInterviewStartTimeAsString();
        interviewEndTime = source.getInterviewEndTimeAsString();
        isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted applicant object into the model's {@code Interview} object.
     *
     * @throws seedu.address.commons.exceptions.IllegalValueException if there were any data constraints
     *     violated in the adapted applicant.
     */
    public Interview toModelType() throws IllegalValueException {

        if (applicant == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, APPLICANT_MISSING));
        }

        Applicant modelApplicant = applicant.toModelType();

        if (jobRole == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, JOB_ROLE_MISSING));
        }

        if (interviewStartTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TIMING_MISSING));
        }

        if (interviewEndTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TIMING_MISSING));
        }

        return new Interview(modelApplicant, jobRole, interviewStartTime, interviewEndTime, isDone);
    }

}
