---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ApplicantListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Applicant` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a applicant).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Applicant` objects (which are contained in a `UniqueApplicantList` object).
* stores the currently 'selected' `Applicant` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Applicant>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Applicant` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Applicant` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Find Interview by Job Title Feature

#### Implementation

The find interview by job title feature allows users to query the list of added interview
for interviews that match the job title via the command `find-i JOB_TITLE`, where `JOB_TITLE` must not
be an empty string.

The `find-i` command is facilitated by the `FindInterviewCommand`, `FindInterviewCommandParser`, `JobContainsKeywordsPredicate` and `ApplicantContainsInterviewPredicate`.
It uses `Model#updateFilteredInterviewList(Predicate<Interview> predicate)` to apply the `JobContainsKeywordsPredicate`
in order to produce a filtered list containing only entries whose job correspond to `JOB_TITLE`.

The find interview command will affect the applicant list. The new applicant list will contain the applicant who have their respective interview 
listed in the interview list. The applicant list index represents an applicant at that index has an interview at the same index in the interview list.
The applicant list uses `Model#updateFilteredApplicantList(Predicate<Applicant> predicate)` to apply the `ApplicantContainsInterviewPredicate`
in order to produce a filtered list containing only entries whose interview correspond to the interview list.

#### Design Consideration

**Aspect: How filter is done:**

* **Alternative 1 (current choice):** Use a predicate object (JobContainsKeywordsPredicate) to handle filtering.
    * Pros: Decrease the coupling effect by separating the predicate and increase extendability.
    * Cons: Difficult to implement as more abstraction is being used.

* **Alternative 2:** Implement filtering logic directly within the FindCourseCommand.
    * Pros: The implementation will be simpler without the consideration of abstraction.
    * Cons: Less abstraction and less extendable for future features.

**Aspect: Case-sensitivity in search:**

* **Alternative 1 (current choice):** Case-insensitive search.
    * Pros: Offers flexibility and speed up the input process without worrying on casing.
    * Cons: A less accurate filters as it will return any matched keyword regardless of casing which might not be intended by the user.

* **Alternative 2:** Case-sensitive search.
    * Pros: A more precise filtering as the string is exact match
    * Cons: Less flexible as the users need to be precise with the input.

**Aspect: Accept multiple keywords:**

* **Alternative 1 (current choice):** Accept multiple keywords.
    * Pros: Offers a stronger filter to search the interview easily.
    * Cons: Requires more care to parse the input correctly in order to not miss any keywords.

* **Alternative 2:** Accept one keyword.
    * Pros: Easier to control the input and filtering.
    * Cons: Less powerful since users can only search job based on one keyword.

### Multiple time formats

#### Implementation
This feature is implemented though the `TimeParser` class. This class contains several public static methods related to manipulating time:
- `TimeParser#parseDate(String date)`  —  Takes in a time String as input, and returns a LocalDateTime which contains the information of the String
  - Accepted time formats examples:
    - Day and time:
      - Format: `<day\> <time\>`
      - Examples:
        - `Tuesday 1630`
        - `Tuesday 4.30PM`
        - `Tuesday 4pm`
        - `Tuesd 4pm`
    - Year, month, day, time of day:
      - Format: `<year, month, day\> <time\>`
      - Examples:
        - `21/12/2024 5pm`
        - `21-12-2024 5pm`
        - `21-12-2024 1730`
        - `21-12-2024 1730pm`
        - `nov 12 1.30pm 2023`(WIP: AM/PM not parsing properly)
        - `2023-12-12 1647`
    - The sequence diagram shown below shows how the API is called by other classes:

      ![parseDateSequenceDiagram.png](images%2FparseDateSequenceDiagram.png)


- `TimeParser#listInterviewClashes(String potentialInterview, UniqueInterviewList interviews)`  — Takes in an interview time in the String format, and checks the given list of interviews that the user has, and lists all clashing interviews.
- `TimeParser#findFreeTime(String day)`  —  Takes in a given day, and if valid, lists out all the free time in that day (i.e. not filled with any interview)
- `TimeParser#findFreeTimeWithinRange(String day, String from, String to)`  —  Takes in a given day, and if valid, lists out all the free time in that day (i.e. not filled with any interview), within the specified window, only if all time Strings are properly formatted
- `TimeParser#sortInterviewsByTimeAscending(UniqueInterviewList interviews)`  —  Takes in a list of interviews, and sorts them in ascending chronological order
- `TimeParser#listTodayInterviews(String day, UniqueInterviewList interviews)`  —  Takes in a given day, and if valid, lists out all the interviews that fall within that day

#### Design considerations:

**Aspect: How `TimeParser#parseDate(String date)` works:**

* **Alternative 1 (current choice):** Have a hardcoded list of time formats that our team deems to be acceptable.
    * Pros:
      * Easy to implement.
    * Cons:
      * May have performance issues in terms of time (i.e. might have to loop through the whole list to find a suitable format)
      * Huge number of time formats available, hence there is a need to update the list of acceptable time formats in future iterations
      * Many errors possible due to the many time fields that the user could format wrongly, which makes implementation difficult

* **Alternative 2 (alternate choice):** Use other time libraries
    * Pros:
        * Might be a better alternative to alternative 1
    * Cons:
        * Will have to overhaul the entire TimeParser class, which might be impractical
        * High risk; not guaranteed to be better after overhaul
        * Developer is not familiar with other time libraries

**Aspect: How `TimeParser#listInterviewClashes(String potentialInterview, UniqueInterviewList interviews)` works:**
* **Alternative 1 (current planned implementation):** Loop over the entire day by hour, and list out any interview that might clash
    * Pros:
        * Very simple to implement
        * Less prone to bugs
    * Cons:
        * Speaking from an algorithmic standpoint, takes linear (i.e. O(n) time), which might hinder performance for days with many interviews
        * Receives a UniqueInterviewList from other classes, which will increase coupling in the codebase

* **Alternative 2 (alternate choice):** search for possible clashes only within the window added
    * Pros:
      * Faster than alternative one in that only a small range of interviews are added

**Aspect: How `TimeParser#findFreeTime(String day)` works:**
* **Alternative 1 (current planned implementation):** Loops over the 24 hours of the given day, and if there is any interview within that hour, the day will be marked as not free
  * Pros:
    * Easy to implement
  * Cons:
    * Hours which have interviews are not included as free time, even though there is a portion of those hours which are free (e.g. if the user has an interview scheduled from 4.30pm on that day, the user has free time from 4pm to 4.30pm)
* **Alternative 2 (alternate implementation):** Filters all the interviews on that day, and marks all pockets of time that are not taken by interviews as free time
  * Pros:
    * More accurate depiction of the free time on that day
  * Cons:
    * More complicated than alternative 1
    * Potentially might perform worse than alternative 1, especially if the user has a lot of interviews that do not fall on the given day (i.e. need to look through the entire list)

**Aspect: How `TimeParser#findFreeTimeWithinRange(String day, String from, String to)` works:**
* **Alternative 1 (current planned implementation):** Loop over the entire day by hour, within the given range, and list out any free time that the user has, by hour
    * Pros:
      * Simple to implement; simply need to loop over the hours within the range
    * Cons:
      * Hours which have interviews are not included as free time, even though there is a portion of those hours which are free (e.g. if the user has an interview scheduled from 4.30pm on that day, the user has free time from 4pm to 4.30pm)

**Aspect: How `TimeParser#sortInterviewsByTimeAscending(UniqueInterviewList interviews)` works:**
* **Alternative 1 (current planned implementation):** Takes in the UniqueInterviewList, and uses the Java LocalDateTime comparator, and sorts the list in ascending order
    * Pros:
      * Easy to implement; Built-in Java library handles the sorting for us
    * Cons:
      * Might increase coupling in the codebase since an object from another object is passed into the API and modified

**Aspect: How `TimeParser#listTodayInterviews(String day, UniqueInterviewList interviews)` works:**
* **Alternative 1 (current planned implementation):** Takes in the UniqueInterviewList, and filters out the interviews for the given day
    * Pros:
      * Easy to implement
    * Cons:
      * Might increase coupling in the codebase since an object from another object is passed into the API and modified

### Find applicant feature

#### Implementation

The find applicant feature allows users to query the list of applicants for applicants 
whose name, phone, email, address and tags match the given arguments. 

This can be done 
via the command `find-a [n/KEYWORDS [MORE_KEYWORDS]...] [p/NUMBER]
[e/KEYWORDS [MORE_KEYWORDS]...] [a/KEYWORDS [MORE_KEYWORDS]...] t/KEYWORDS [MORE_KEYWORDS]...]`.

The find applicant feature is facilitated by `FindApplicantCommand`, `FindApplicantCommandParser`, 
`AddressContainsKeywordsPredicate`, `EmailContainsKeywordsPredicate`, `NameContainsKeywordsPredicate`, 
`PhoneContainsNumberPredicate`, `TagContainsKeywordsPredicate`.
It uses `Model#updateFilteredApplicantList(Predicate<Applicant>)` to apply the predicates 
in order to produce a filtered list containing only the filtered entries.

#### Design Considerations:

Aspect: How find applicant command filters applicants:
* **Alternative 1 (current choice):** Use a predicate for each field
  * Pros: 
    * Decrease coupling and increases extensibility.
    * Easier to maintain
  * Cons:
    * More difficult and tedious to implement
    * More test cases needed for each predicate

* **Alternative 2:** Use one predicate for the entire applicant
  * Pros:
    * Easier to code and less code to write
  * Cons:
    * Harder to maintain
    * More coupling as predicates for different fields are not abstracted out

Aspect: Which find command format
* **Alternative 1 (current choice):** Accepts multiple space or comma separated keywords for each prefix
  * Pros: 
    * Allows filtering using multiple keywords in a single find command
    * User can type the command quickly
  * Cons:
    * Only allows filtering by words and not phrases
* **Alternative 2:** Accepts one keyword for each prefix
  * Pros:
    * Easy to implement
    * User can type the command quickly
  * Cons:
    * Can only find using one keyword for each applicant field in a single find command
* **Alternative 3:** Accepts duplicate prefixes and a keyphrase for each prefix
  * Pros:
    * Allows filtering of multiple keywords or keyphrase in a single find command
    * The most specific filter out of all the alternatives
  * Cons:
    * Most difficult to implement of all alternatives considered
    * The command can be slow to type due to the need to type many prefixes

Aspect: How find command matches the arguments for name
* **Alternative 1 (current choice):** Match keywords to words in the name
  * Pros:
    * Can find a person with only the first name or last name
  * Cons:
    * Less specific than exact matching
* **Alternative 2:** Require exact match between arguments and name
  * Pros:
    * More specific search
  * Cons:
    * Users need to type longer commands to search for an applicant
    * Less flexibility
* **Alternative 3:** Check if argument is substring of the name
  * Pros:
    * Users can find an applicant without typing an entire word
    * More flexibility
  * Cons:
    * Too many applicants might show up in a single find command which defeats
    the purpose of the find command


_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a hiring manager working in Technology/Engineering field
* has a need to manage a significant number of job applicants and interviews
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage applicants and schedule interviews faster than a typical mouse/GUI driven contact and calendar app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                               | I want to …​                             | So that I can…​                                                        |
|----------|-------------------------------------------------------|------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | New user of the app                                   | see usage instructions                   | refer to instructions when I first started to use the App              |
| `* * *`  | Engineering Manager ready for job applicant           | add a new applicant                      | save their contact details into the App                                |
| `* * *`  | Engineering Manager opening a job role                | add a new job role                       | keep track of the job role available for application                   |
| `* * *`  | Engineering Manager ready to start an interview       | add a new interview slot                 | save the interview information into the App                            |
| `* * *`  | Engineering Manager ready for next round of interview | delete an applicant                      | remove their contact details that I no longer need                     |
| `* * *`  | Engineering Manager that finished an interview        | delete an interview                      | remove the interview that has already been completed                   |
| `* * *`  | Busy Engineering Manager                              | find an applicant by name                | locate details of applicants without having to go through the entire list |
| `* * *`  | Busy Engineering Manager                              | find a job role by name                  | easily locate the job role which are still available                   |
| `* *`    | Busy Engineering Manager                              | set reminder of interview                | stay organised and track upcoming interview                            |
| `* *`    | Engineering Manager with sensitive information        | hide private contact details             | protect the privacy of the applicants information in the App           |
| `* *`    | Engineering Manager with many applicants              | sort the applicants by skill             | prioritise and focus on the most promising candidates                  |
| `* *`    | Engineering Manager with many applicants              | rank the applicants                      | keep track of the applicants who have performed well                   |
| `* *`    | Engineering Manager                                   | update an applicant details              | easily update their information on the App                             |
| `* *`    | Engineering Manager                                   | update a job role                        | easily change the details about the job role                           |
| `* *`    | Engineering Manager with limited budget               | track the cost per hire                  | ensure that the company budget is not exceeded                         |
| `* *`    | Team-Oriented Engineering Manager                     | add other interviewer                    | facililate collaboration and delegation of responsibilities            |
| `*`      | Organised Engineering Manager                         | sort applicants by name                     | locate an applicant easily                                             |
| `*`      | Engineering Manager with many contacts                | import contacts from other file          | add multiple contacts into the App smoothly                            |
| `*`      | Meticulous Engineering Manager                        | store the applicant's background         | make a more informed choice to benefit the company                     |
| `*`      | Engineering Manager with multiple rounds of interview | track the progress of each candidate     | maintain a clear overview of our recruitment efforts                   |
| `*`      | New Engineering Manager                               | analyse the performance of the interview | make improvements to my interview processes                            |
| `*`      | Helpful Engineering Manager                           | provide feedback to the applicant        | offer constructive advice to help them improve their skills            |
| `*`      | Long user of the app                                  | provide feedback to the developer        | suggest improvements and enhancements for the app                      |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `InterviewHub` and the **Actor** is the `hiring manager`, unless specified otherwise)

**Use case: UC01 Add an interview**

**MSS**

1. Hiring manager requests to add an interview.
2. InterviewHub adds the interview.

    Use case ends.

**Extensions**
* 1a. One of the user-provided parameters is invalid
    * 1a1. InterviewHub shows an error message.

      Use case resumes at step 1.

**Use case: UC02 List all interview**

**MSS**

1. Hiring manager requests to list all interviews.
2. InterviewHub displays all scheduled interviews.

    Use case ends.

**Extensions**
* 1a. The list is empty
  * 1a1. InterviewHub shows an error message.

    Use case ends.

**Use case: UC03 Delete an Interview**

**MSS**

1. Hiring manager <u> views the list of all interviews (UC02) </u>
2. Hiring manager requests to delete a specific interview
3. InterviewHub deletes the specified interview

    Use case ends.

**Extensions**

* 2a. The provided index is invalid
  * 2a1. InterviewHub shows an error message.

    Use case resumes at step 2.

**Use case: UC04 Add a job role**

**MSS**

1. Hiring manager requests to add a job role.
2. InterviewHub adds the job role.

    Use case ends.

**Extensions**
* 1a. One of the user-provided parameters is invalid
  * 1a1. InterviewHub shows an error message.

    Use case resumes at step 1.

**Use case: UC05 List all job roles**

**MSS**

1. Hiring manager requests to list all job roles.
2. InterviewHub displays all job roles.

    Use case ends.

**Extensions**
* 1a. The list is empty
  * 1a1. InterviewHub shows an error message.

    Use case ends.

**Use case: UC06 List all applicants for a job role**

**MSS**

1. Hiring manager <u> views the list of all job roles (UC05) </u>
2. Hiring manager requests to view all applicants for a specific job role.
3. InterviewHub displays all the applicants for the specific job role.

    Use case ends.

* 2a. The provided index is invalid
  * 2a1. InterviewHub shows an error message.

    Use case resumes at step 2.

**Use case: UC07 Delete a job role**

**MSS**

1. Hiring manager <u> views the list of all job roles (UC02) </u>
2. Hiring manager requests to delete a specific job role
3. InterviewHub deletes the specified job role

    Use case ends.

**Extensions**

* 2a. The provided index is invalid
  * 2a1. InterviewHub shows an error message.

    Use case resumes at step 2.


**Use case: UC08 Add an applicant**

**MSS**

1. Hiring manager requests to add an applicant.
2. InterviewHub adds the applicant.

   Use case ends.

**Extensions**
* 1a. One of the user-provided parameters is invalid
  * 1a1. InterviewHub shows an error message.

    Use case resumes at step 1.

**Use case: UC09 List all applicants**

**MSS**

1. Hiring manager requests to list all applicants.
2. InterviewHub displays all scheduled applicants.

   Use case ends.

**Extensions**
* 1a. The list is empty
  * 1a1. InterviewHub shows an error message.

      Use case ends.

**Use case: UC010 Delete an applicant**

**MSS**

1. Hiring manager <u> views the list of all applicants (UC09) </u>
2. Hiring manager requests to delete a specific applicant
3. InterviewHub deletes the specified applicant

   Use case ends.

**Extensions**

* 2a. The provided index is invalid
    * 2a1. InterviewHub shows an error message.

      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to handle as many applicants as the user is able to manage in their workday/workweek.
3.  The app should be reasonably responsive to the entire set of user requests(i.e. within 1 second at maximum load).
4.  The system should have an interface that is very easy to pick up for our target audience(i.e. Engineering Managers
that have many years of programming experience).

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Applicant**: The applicant applying to a particular job role.
* **Hiring manager**: The manager interviewing the applicant.
This manager is familiar with the technical aspects of the role. Also called engineering manager.
* **MSS**: Main Success Scenario. It describes the most straightforward
interaction in a use case where nothing goes wrong.
* **Extensions**: In a use case, an extension describes an alternative flow of events
that are different from the MSS.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a applicant

1. Deleting a applicant while all applicants are being shown

   1. Prerequisites: List all applicants using the `list` command. Multiple applicants in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No applicant is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
