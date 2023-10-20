package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.interview.Interview;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Applicant> PREDICATE_SHOW_ALL_APPLICANTS = unused -> true;
    Predicate<Interview> PREDICATE_SHOW_ALL_INTERVIEWS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a applicant with the same identity as {@code applicant} exists in the address book.
     */
    boolean hasApplicant(Applicant applicant);

    /**
     * Deletes the given applicant.
     * The applicant must exist in the address book.
     */
    void deleteApplicant(Applicant target);

    /**
     * Adds the given applicant.
     * {@code applicant} must not already exist in the address book.
     */
    void addApplicant(Applicant applicant);

    /**
     * Replaces the given applicant {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The applicant identity of {@code editedPerson} must not be the same
     * as another existing applicant in the address book.
     */
    void setApplicant(Applicant target, Applicant editedApplicant);

    /** Returns an unmodifiable view of the filtered applicant list */
    ObservableList<Applicant> getFilteredApplicantList();

    /** Returns an unmodifiable view of the filtered interview list */
    ObservableList<Interview> getFilteredInterviewList();

    /**
     * Updates the filter of the filtered applicant list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredApplicantList(Predicate<Applicant> predicate);

    /**
     * Returns true if an Interview with the same attributes as {@code interview} exists in the address book.
     */
    boolean hasInterview(Interview interview);

    /**
     * Adds the given interview.
     * {@code interview} must not already exist in the address book.
     */
    void addInterview(Interview interview);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteInterview(Interview target);

    /**
     * Replaces the given interview {@code target} with {@code editedInterview}.
     * {@code target} must exist in the address book.
     * The interview identity of {@code interview} must not be the
     * same as another existing interview in the address book.
     */
    void setInterview(Interview target, Interview editedInterview);

    /**
     * Updates the filter of the filtered interview list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInterviewList(Predicate<Interview> predicate);
}
