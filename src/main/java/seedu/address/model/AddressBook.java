package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.UniqueApplicantList;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.UniqueInterviewList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson and .isNotValidOrNewInterview comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueApplicantList applicants;
    private final UniqueInterviewList interviews;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        applicants = new UniqueApplicantList();
        interviews = new UniqueInterviewList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setApplicants(List<Applicant> applicants) {
        this.applicants.setPersons(applicants);
    }

    /**
     * Replaces the contents of the interview list with {@code interviews}.
     * {@code interviews} must not contain duplicate persons.
     */
    public void setInterviews(List<Interview> interviews) {
        this.interviews.setInterviews(interviews);
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setApplicants(newData.getPersonList());
        setInterviews(newData.getInterviewList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Applicant applicant) {
        requireNonNull(applicant);
        return applicants.contains(applicant);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Applicant p) {
        applicants.add(p);
    }

    /**
     * Replaces the given applicant {@code target} in the list with {@code editedApplicant}.
     * {@code target} must exist in the address book.
     * The applicant identity of {@code editedApplicant} must not be the
     * same as another existing person in the address book.
     */
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        requireNonNull(editedApplicant);

        applicants.setApplicant(target, editedApplicant);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Applicant key) {
        applicants.remove(key);
    }

    //// interview-level operations
    /**
     * Returns true if an interview with the same identity as {@code interview} exists in the address book.
     */
    public boolean hasInterview(Interview interview) {
        requireNonNull(interview);
        return interviews.contains(interview);
    }

    /**
     * Adds an interview to the address book.
     * The interview must not already exist in the address book.
     */
    public void addInterview(Interview i) {
        interviews.add(i);
    }

    /**
     * Replaces the given interview {@code target} in the list with {@code editedInterview}.
     * {@code target} must exist in the address book.
     * The interview identity of {@code editedInterview} must not
     * be the same as another existing person in the address book.
     */
    public void setInterview(Interview target, Interview editedInterview) {
        requireNonNull(editedInterview);

        interviews.setInterview(target, editedInterview);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeInterview(Interview key) {
        interviews.remove(key);
    }

    /**
     * Finds the interview that contains the applicant and returns it.
     * Returns null if the applicant does not have an interview.
     * {@code applicant} must exist in the address book.
     */
    public Interview findInterviewWithApplicant(Applicant applicant) {
        requireNonNull(applicant);

        for (Interview interview : getInterviewList()) {
            if (interview.getInterviewApplicant().equals(applicant)) {
                return interview;
            }
        }
        return null;
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", applicants)
                .toString();
    }

    @Override
    public ObservableList<Applicant> getPersonList() {
        return applicants.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Interview> getInterviewList() {
        return interviews.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return applicants.equals(otherAddressBook.applicants)
                && interviews.equals(otherAddressBook.interviews);
    }

    @Override
    public int hashCode() {
        return applicants.hashCode();
    }

}
