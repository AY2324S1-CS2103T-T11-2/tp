package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Applicant> filteredApplicants;
    private final FilteredList<Interview> filteredInterviews;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplicants = new FilteredList<>(this.addressBook.getApplicantList());
        filteredInterviews = new FilteredList<>(this.addressBook.getInterviewList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasApplicant(Applicant applicant) {
        requireNonNull(applicant);
        return addressBook.hasApplicant(applicant);
    }

    @Override
    public void deleteApplicant(Applicant target) {
        if (target.hasInterview()) {
            Interview interviewWithTarget = addressBook.findInterviewWithApplicant(target);
            addressBook.removeInterview(interviewWithTarget);
        }

        addressBook.removeApplicant(target);
    }

    @Override
    public void addApplicant(Applicant applicant) {
        addressBook.addApplicant(applicant);
        updateFilteredApplicantList(PREDICATE_SHOW_ALL_APPLICANTS);
    }

    @Override
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        requireAllNonNull(target, editedApplicant);

        if (target.hasInterview()) {
            Interview interviewWithTarget = addressBook.findInterviewWithApplicant(target);
            Interview interviewWithEditedApplicant = new Interview(
                    editedApplicant,
                    interviewWithTarget.getJobRole(),
                    interviewWithTarget.getInterviewTiming()
            );
            addressBook.setInterview(interviewWithTarget, interviewWithEditedApplicant);
        }

        addressBook.setApplicant(target, editedApplicant);
    }

    //=========== AddressBook Interviews ======================================================================
    @Override
    public boolean hasInterview(Interview interview) {
        requireNonNull(interview);
        return addressBook.hasInterview(interview);
    }

    @Override
    public void addInterview(Interview interview) {
        addressBook.addInterview(interview);
        updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
    }

    @Override
    public void deleteInterview(Interview target) {
        addressBook.removeInterview(target);
    }
  
    @Override
    public void setInterview(Interview target, Interview editedInterview) {
        requireAllNonNull(target, editedInterview);

        addressBook.setInterview(target, editedInterview);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Applicant> getFilteredApplicantList() {
        return filteredApplicants;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Interview> getFilteredInterviewList() {
        return filteredInterviews;
    }

    @Override
    public void updateFilteredApplicantList(Predicate<Applicant> predicate) {
        requireNonNull(predicate);
        filteredApplicants.setPredicate(predicate);
    }

    @Override
    public void updateFilteredInterviewList(Predicate<Interview> predicate) {
        requireNonNull(predicate);
        filteredInterviews.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredApplicants.equals(otherModelManager.filteredApplicants)
                && filteredInterviews.equals(otherModelManager.filteredInterviews);
    }

}
