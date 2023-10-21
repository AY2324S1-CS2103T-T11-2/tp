---
layout: page
title: User Guide
---

# Table of Contents

- [Introduction](#Introduction)
- [Quick Start](#QuickStart)
- [GUI Summary](#GUISummary)
- [Features](#Features)
- [Frequently Asked Questions](#FrequentlyAskedQuetions)
- [Known Issues](#KnownIssues)
- [Command Summary](#CommandSummary)
  - [General Command](##GeneralCommand)
  - [Application Management Command](##ApplicationManagementCommand)
  - [Interview Management Command](##InterviewManagementCommand)
- [Glossary](#Glossary)

--------------------------------------------------------------------------------------------------------------------

# Introduction

InterviewHub (IH) is a **desktop app for engineering manager to schedule job interviews and manage applicants**.

It is optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface (GUI)**.

If you can type fast, IH can get your Interview management tasks done faster than traditional GUI apps.

Let's get started by following the [Quick Start](#Quick Start) section!

--------------------------------------------------------------------------------------------------------------------

# Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `InterviewHub.jar` from [here](https://github.com/AY2324S1-CS2103T-T11-2/tp/releases).

3. Copy the file to the folder you want to use as the home folder for InterviewHub.

4. Double-click the file to start the app. The Graphical User Interface(GUI) should appear in a few seconds.

5. To get a better understanding of what you see. Please refer to the [GUI Summary](#GUI Summary) for more details.
--------------------------------------------------------------------------------------------------------------------

# GUI Summary

**TO BE ADDED**

--------------------------------------------------------------------------------------------------------------------
# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `nuke` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
</div>

## Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Adding an applicant: `add-a`

Adds an applicant to the address book.

Format: `add-a n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]`

Examples:
* `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

## Adding an interview: `add-i`

Adds an interview to the address book.

Format: `add-i app/APPLICANT_ID jr/JOB_ROLE time/INTERVIEW_DATETIME`

* List of accepted date formats:
  * Day, Month & Year formats(only DD*MM*YY/YYYY formats accepted):
    * 12/03/2023
    *  12/03/23
    *  12/03/2023 5pm
    *  12/03/2023 1700
    *  12/03/2023 5.03pm
    *  12/03/23 5pm
    *  12-03-2023
    *  12-03-2023 5pm
    *  12-03-23
    *  12-03-23 5pm
    *  12th December 2023
    *  12th December 2023 5pm
  * Day & Month formats
    * 12/03
    *  12/03 5pm
    *  12-03 5pm
    *  12-03
    *  12th December
    *  12th December 5pm
    *  12th Dec
    *  12th Dec 5pm
  * Day formats
    *  Mon/tues/wed/…/sun
    *  Monday/Tuesday/Wednesday/…/Sunday
    *  Today
    *  Tomorrow
    *  Next week
    *  Next month

* Other features:
  * When the user enters the date properly: `added <interview description> at <time>`
  * When the user does not input the date properly: `“Oops! Please enter a valid date String!”`
  * When there is an interview clash: `“Oops! You have an <insert interview object> scheduled at <from date & by date>`

Examples:
* `add-job app/18 j/software-engineer time/2023-10-24 18:00`

## Listing all applicants : `list-a`

Shows a list of all applicants in the address book onto the GUI.

Format: `list-a`

## Listing all interviews : `list-i`

Shows a list of all interviews in the address book onto the GUI.

Format: `list-i`

## Editing an applicant : `edit-a`

Edits an existing applicant in the address book.

Format: `edit-a APPLICANT_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`

* Edits the applicant at the specified `APPLICANT_INDEX`. The index refers to the index number shown in the displayed applicant list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit-a 1 n/John Doe` Edits the name of the 1st person to be `John Doe`.
*  `edit-a 2 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 2nd person to be `91234567` and `johndoe@example.com` respectively

## Editing an interview : `edit-i`

Edit an existing interview in the address book.

Format: `edit-i INTERVIEW_INDEX [app/APPLICANT_ID] [jr/JOB_TITLE] [time/INTERVIEW_DATETIME]`

* Edits the interview at the specified `INTERVIEW_INDEX`. The index refers to the index number shown in the displayed interview list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit-i 1 jr/software-engineer` Edits the job title of the 1st interview to be `software engineer`.
*  `edit-i 2 jr/data-analyst` Edits the job title of the 2nd interview to be `develop software`.

## Deleting an applicant : `delete-a`

Deletes the specified applicant from the address book.

Format: `delete-a INDEX`

* Deletes the applicant at the specified `INDEX`.
* The index refers to the index number shown in the displayed applicant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete-a 1` deletes the 1st applicant in the address book.

## Deleting an interview : `delete-i`

Deletes the specified interview from the address book.

Format: `delete-i INDEX`

* Deletes the inteview at the specified `INDEX`.
* The index refers to the index number shown in the displayed interview list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete-i 1` deletes the 1st inteview in the address book.

## Clearing all applicants : `clear-a`

Clears all applicants from the address book.

Format: `clear-a`

## Clearing all jobs : `clear-i`

Clears all interviews from the address book.

Format: `clear-i`

* Clearing all interview will also clear all applicants.

## Clearing all data : `nuke`

Clears all data from the address book.

Format: `nuke`

* All applicants and interviews will be cleared completely from the address book.

## Finding applicants (and their index in address book) by name: `find-a`

Finds applicants whose names contain any of the given keywords.

Format: `find-a KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

## Finding interview (and their index in address book) by job title: `find-i`

Find interviews which jobs title contain any of the given keywords.

Format: `find-i KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `ANALYST` will match `analyst`
* The order of the keywords does not matter. e.g. `Software Engineer` will match `Engineer Software`
* Only the job title is searched.
* Only full words will be matched e.g. `Analyst` will not match `Analysts`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Software Engineer` will return `Software-Developer`, `System-Engineer`

Examples:
* `find-i software data` returns `Software-Engineer` and `Data-Analyst`.

## Exiting the program : `exit`

Exits the program.

Format: `exit`

## Saving the data

InterviewHub data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## Editing the data file

InterviewHub data are saved automatically as a JSON file `[JAR file location]/data/interviewhub.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, InterviewHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

## Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

# Frequently Asked Questions

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous InterviewHub home folder.

--------------------------------------------------------------------------------------------------------------------

# Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

# Command Summary

## General Command

| Action                                  | Format, Examples                                                                                                                                          |
|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Clear all applicants and interviews** | `nuke`                                                                                                                                                    |
| **Help**                                | `help`                                                                                                                                                    |
| **Exit**                                | `exit`                                                                                                                                                    |

## Applicant Management Command

| Action                     | Format, Examples                                                                                                                                                      |
|----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add applicant**          | `add-a n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear all applicants**   | `clear-a`                                                                                                                                                             |
| **Delete applicant**       | `delete-a INDEX`<br> e.g., `delete-a 3`                                                                                                                               |
| **Edit applicant**         | `edit-a APPLICANT_INDEX [n/NAME] [t/INTERVIEW_DATETIME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`<br> e.g.,`edit-a 2 n/John Doe`                                        |
| **Find applicant by name** | `find-a KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-a John`                                                                                                              |
| **List applicants**        | `list-a`                                                                                                                                                              |

## Interview Management Command

| Action                    | Format, Examples                                                                                                                      |
|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Add interview**         | `add-i app/APPLICANT_INDEX jr/JOB_TITLE time/INTERVIEW_DATETIME` <br> e.g., `add-i app/18 jr/software engineer time/2022-12-12 18:00` |
| **Clear all interviews**  | `clear-i`                                                                                                                             |
| **Delete interview**      | `delete-i INDEX`<br> e.g., `delete-i 3`                                                                                               |
| **Edit interview**        | `edit-i INTERVIEW_INDEX [app/APPLICANT_INDEX] [jr/JOB_TITLE] [time/INTERVIEW_DATETIME]`<br> e.g.,`edit-i 2 jr/software-engineer`      |
| **Find interview by job** | `find-i KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-i software-engineer`                                                                 |
| **List interview**        | `list-i`                                                                                                                              |
