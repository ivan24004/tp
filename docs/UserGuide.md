---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# MatchMate User Guide

MatchMate is a **desktop app for Computer Science students in NUS to create groups among their friends / acquaintances,
as well as to allow students to find balanced groups with diverse skill sets out of their own contact list.** 
It is optimized for Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). 

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `matchmate.jar`

1. Copy the file to the folder you want to use as the _home folder_ for your app.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar matchmate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press <kbd>Enter</kbd> to execute it. e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all courseMates.
   
   * `add John Doe -p 87654321 -e johndoe@example.com -s Leadership -s C++`: Adds a courseMate named `John Doe` to the courseMate list.

   * `delete #3` : Deletes the 3rd courseMate shown in the current list.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## User Interface

The UI consists of four main components:

![Ui Components](images/ui-structure.png)

1. **Command Box**: You can type your commands here and press <kbd>Enter</kbd> to execute them. You can also use the <kbd>:fas-caret-up:</kbd> and <kbd>:fas-caret-down:</kbd> arrow keys to navigate through your command history.
2. **CourseMate List Panel**: Displays the list of courseMates. Press <kbd>Enter</kbd> or double click to select a courseMate from the courseMate list panel.
3. **CourseMate Detail Panel**: Displays the details of a selected courseMate.
4. **Group List Panel**: Displays the list of groups.

You can use the <kbd>Tab</kbd> key to switch between the command box and the courseMate list panel.

<box type="tip" seamless>

<span class="badge rounded-pill bg-secondary">Example</span> If you want to add a few selected courseMates to a group, you can:
1. Select the first courseMate in the courseMate list panel and press <kbd>Enter</kbd>. The courseMate will be displayed in the courseMate detail panel.
2. Type `add-member GROUP_NAME -cm ##` in the command box and press <kbd>Enter</kbd>.
3. Press <kbd>Tab</kbd> to switch to the courseMate list panel. Select the next courseMate and press <kbd>Enter</kbd>.
4. Press <kbd>Tab</kbd> to switch back to the command box and press <kbd>:fas-caret-up:</kbd> to retrieve the previous command. Press <kbd>Enter</kbd> to execute the command.
5. Repeat steps 3 and 4 for the remaining courseMates.

This way, you can quickly add multiple courseMates to a group without having to type the courseMate names each time.

</box>

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add NAME`, `NAME` is a parameter which can be used as `add John Doe`.

* Items in square brackets are optional.<br>
  e.g. `add NAME [-p PHONE_NUMBER]` can be used as `add John Doe -p 8762318` or as `add John Doe`.

* Items with `...`​ after them can be used multiple times including zero times.<br>
  e.g. `[-s SKILL]...​` can be used as ` ` (i.e. 0 times), `-s C++`, `-s C++ -s Python` etc.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
</box>


### Viewing help : `help`

Shows a message that explains how to access the help page.

Format: `help`


### Adding a courseMate: `add`

Adds a courseMate to the courseMate list. A name and an email will be required, and all other information will be optional.

Format: `add NAME -e EMAIL [-p PHONE_NUMBER] [-t TELEGRAM_HANDLE] [-s SKILL]...​`

Parameters:
- `NAME`: Name of the courseMate. The string must only contain alphanumeric characters and spaces. Pick a nickname if the name is not fully alphanumeric.
- `EMAIL`: Email of the courseMate. Refer to the notes below for the acceptable format.
- `PHONE_NUMBER` (optional): Phone number of the courseMate. Accept strings with numeric characters only.
- `TELEGRAM_HANDLE` (optional): Telegram handle of the courseMate. Accept strings formed by alphanumeric characters and underscores only, and its length must be between 5 and 32 characters.
- `SKILL` (zero or multiple allowed): Skill(s) of the courseMate. Accepts any strings, except that words cannot start with the hyphen (-) character. 

<box type="tip" seamless>

**Tip:** A courseMate's name is case-insensitive. Adding a new courseMate with the same string but different capitalization will be rejected.
Consider adding a suffix to the name to differentiate them.

</box>

<box type="tip" seamless>

**Tip:** A courseMate can have any number of skills (including 0). Skill names are also case-insensitive.
</box>

Examples:

- `add John Doe`
- `add John Doe -e johndoe@example.com -p 87654321 -t johndoe -s Leadership -s C++`


<box type="info" seamless>

**Notes about Email format:**<br>

Emails should be of the format `local-part@domain` and adhere to the following constraints:

1. The local-part should only contain alphanumeric characters and the special characters `+`, `_`, `.` and `-`.
   The local-part may not start or end with any special characters.
2. This is followed by a `@` and then a domain name. The domain name is made up of domain labels separated by periods.
   The domain name must:
    - end with a domain label at least 2 characters long;
    - have each domain label start and end with alphanumeric characters;
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.
</box>

<div style="page-break-after: always;"></div>

### Listing all courseMates and groups: `list`

Lists all courseMates and groups in the lists.

Format: `list`

Expected output:
- `Message: Listed all courseMates and groups`


### Editing a courseMate : `edit`

Edits an existing courseMate with a given name. This command supports changing the name, email, phone number and telegram handle.

Format: `edit COURSEMATE [-n NEW_NAME] [-e NEW_EMAIL] [-p NEW_PHONE_NUMBER] [-t NEW_TELEGRAM_HANDLE]`

Parameters:
- `COURSEMATE`: Name of the existing courseMate or aliases (substrings or in hashtag (#) notation).
- `NEW_NAME` (optional): New name of the courseMate.  The string must only contain alphanumeric characters and spaces. Pick a nickname if the name is not fully alphanumeric.
- `NEW_EMAIL` (optional): New email of the courseMate. Refer to the notes above for the acceptable format.
- `NEW_PHONE_NUMBER` (optional): New phone number of the courseMate. Accept strings with numeric characters only.
- `NEW_TELEGRAM_HANDLE` (optional): New Telegram handle of the courseMate. Accept strings formed by alphanumeric characters and underscores only, and its length must be between 5 and 32 characters.
    
<box type="tip" seamless>

**Tip:**

Instead of supplying a full name to identify an existing courseMate, you can use the following shortcuts:
- `#1`, `#2`, ..., `#n` (where n is the number of courseMates currently displayed in the courseMate list panel) – `#k` references the k-th courseMate currently displayed in the courseMate list panel.


- `##` – References the courseMate currently displayed in the detailed view panel.


- A substring of the name instead of the full name.
    - In the event of multiple matches (the substring appears in multiple courseMates), you will receive the following message: `There are ? course mates with name containing ???. You can retry by giving the course mate's complete name or use the index of the contact.` and the list of matching courseMates will be displayed in the courseMate list panel.
    - No side effects will be made by the current command.
    - You should retry the command by finding the courseMate on the list and using the hashtag notation (`#`) to identify the courseMate.
  <br><br>

  <span class="badge rounded-pill bg-secondary">Example</span>
    - You have the following courseMates listed in the courseMate list panel:  
      `#1 Benson`  
      `#2 Ben`  
      Typing `edit Ben` will display a warning message for having multiple matches.
    - Case 1: If you want to edit Benson:
        - Type  `edit Benson ...` or just its substring `edit Bens ...`
        - Alternatively, you can use the alias `edit #1 ...`
    - Case 2: If you want to edit Ben:
        - Type `edit #2 ...` as you must specify its index in the list.
</box>

Examples:
- `edit John Doe -n Joe Schmo`
- `edit #1 -n Joe Schmo`
- `edit John -e johndoe@gmail.com -p 98765432`

<div style="page-break-after: always;"></div>

### Add a skill to a courseMate : `add-skill`
Adds a list of skills to a courseMate. Adding a skill that already exists in the courseMate
will still succeed, but it won't show duplicate skills.


Format: `add-skill COURSEMATE -s SKILL [-s SKILL]...`

Parameters:
- `COURSEMATE`: Name of the existing courseMate. Accept aliases (substrings or in hashtag (#) notation).
- `SKILL` (one or multiple allowed): Skill(s) of the courseMate. Accepts any strings, except that words cannot start with the hyphen (-) character. 

Examples:
- `add-skill John Doe -s C++ -s Leadership`

  
### Delete a skill from a courseMate  : `delete-skill`
Deletes a list of skills from a courseMate.

Format: `delete-skill COURSEMATE -s SKILL [-s SKILL]...`

Parameters:
- `COURSEMATE`: Name of the existing courseMate. Accept aliases (substrings or in hashtag (#) notation).
- `SKILL`: (one or multiple allowed): Skill(s) of the courseMate. These must be existing skills that the courseMate contains.

Examples:
- `delete-skill John Doe -s C++ -s Leadership`

### Search courseMates with keywords: `find-mate`

Finds all courseMates whose names or skills contain the specified keyphrase (case-insensitive).

To match a courseMate’s skill, the keywords should match exactly with the skill. To match a courseMate’s name, the keywords can be a case-insensitive substring of the name.

Format: `find-mate KEYPHRASE`

Parameters:
- `KEYPHRASE`: Combination of keywords to be used for the search. Accepts any strings including spaces.

Examples:
- `find-mate John`
- `find-mate C++`

### Search groups with keywords: `find-group`

Search groups that include all matching keywords in their name.

To match a group's name, the keywords can be a case-insensitive substring of the name.

Format: `find-group KEYPHRASE`

Parameters:
- `KEYPHRASE`: Combination of keywords to be used for the search. Accepts any strings including spaces.

Examples:
- `find-group CS2103T G18`

<div style="page-break-after: always;"></div>

### Deleting a courseMate : `delete`

Deletes a courseMate with a given name.

Format: `delete COURSEMATE`

Parameters:
- `COURSEMATE`: Name of the existing courseMate. Accept aliases (substrings or in hashtag (#) notation).

Examples:
- `delete ##`
- `delete John`

### Create group project: `create-group`

Creates a group project. You can specify the courseMates in the group when creating the group, or choose to add them later.

Format: `create-group GROUP_NAME [-t TELEGRAM_CHAT_URL] [-cm COURSEMATE]... [-s SKILL]...`

Parameters:
- `GROUP_NAME`: The group name. The string must only contain alphanumeric characters and spaces. Pick a nickname if the name is not fully alphanumeric.
- `TELEGRAM_CHAT_URL` (optional): The URL of the Telegram chat for the group. Accept strings that start with `https://t.me/` and followed by alphanumeric characters and the special characters `+`, `-` and `_`.
- `COURSEMATE` (zero or multiple allowed): Name of the existing courseMates to be added to the group. Accept aliases (substrings or in hashtag (#) notation).
- `SKILL` (zero or multiple allowed): Required skill(s) of the group. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `create-group CS2103T G18`
- `create-group CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`
- `create-group CS2103T G18 -cm John -cm #2 -s C++ -s Java`
- `create-group CS2103T G18 -cm John -s C++ -s Java -cm #2 -t https://t.me/+WDTg34uuUlH8Ml2d`

### Add courseMates to group: `add-member`
Adds some team members to an existing group.

Format: `add-member GROUP_NAME -cm COURSEMATE [-cm COURSEMATE]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `COURSEMATE` (one or multiple allowed): Name of the existing courseMates to be added to the group. Accept aliases (substrings or in hashtag (#) notation).

Examples:
- `add-member CS2103T G18 -cm Ivan -cm #1`
- `add-member CS2103T G18 -cm ##`

<box type="tip" seamless>

**Tip:** Different from courseMate names, group names must be an exact match.
</box>

<div style="page-break-after: always;"></div>

### Delete courseMates from group: `delete-member`

Deletes some team members from an existing group.

Format: `delete-member GROUP_NAME -cm COURSEMATE [-cm COURSEMATE]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `COURSEMATE` (one or multiple allowed): Name of the existing courseMates to be deleted from the group. Accept aliases (substrings or in hashtag (#) notation).

Examples:
- `delete-member CS2103T G18 -cm Ivan -cm #1`
- `delete-member CS2103T G18 -cm ##`

### Give rating to your courseMates: `rate-mate`

Rates courseMates by up to five stars.

Format: `rate-mate COURSEMATE -r RATING`

Parameters:
- `COURSEMATE`: Name of the existing courseMate to give the rating to. Accept aliases (substrings or in hashtag (#) notation).
- `RATING`: The rating given, which is a single digit integer between 0 and 5, where 0 signifies that no rating is given.

Examples:
- `rate-mate Bob -r 5`

### Edit the telegram chat URL of a group: `edit-tg-chat-url`

Edits the telegram chat URL of a group.

Format: `edit-tg-chat-url GROUP_NAME -t NEW_TELEGRAM_CHAT_URL`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `NEW_TELEGRAM_CHAT_URL`: New URL of the Telegram chat for the group. Accept strings that start with `https://t.me/` and followed by alphanumeric characters and the special characters `+`, `-` and `_`.

Examples:
- `edit-tg-chat-url CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`

### Require skills in a group: `require-skill`

Add skills that should be required or necessary for the group. You only need one member that possesses the skill for it to be marked as fulfilled. Fulfilled skills are marked in green while unfulfilled ones are marked in red.

Format: `require-skill GROUP_NAME -s SKILL [-s SKILL]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `SKILL` (one or multiple allowed): Required skill(s) of the group. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `require-skill CS2103T G18 -s C++ -s Java`

<div style="page-break-after: always;"></div>

### Unrequire skills in a group: `unrequire-skill`

Mark skills that are no longer required or necessary for the group.

Format: `unrequire-skill GROUP_NAME -s SKILL [-s SKILL]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `SKILL` (one or multiple allowed): Skill(s) to be marked as no longer required in the group. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `unrequire-skill CS2103T G18 -s C++ -s Java`

### Mark important skills in a group: `mark-important`

Mark skills that are considered as important in the group. Important skills are signified with a [!] notation.

Format: `mark-important GROUP_NAME -s SKILL [-s SKILL]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `SKILL` (one or multiple allowed): Skill(s) to be marked as no longer required in the group. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `mark-important CS2103T G18 -s C++ -s Java`
- 
### Unmark important skills in a group: `unmark-important`

Mark skills that are no longer considered as important in the group. 

Format: `unmark-important GROUP_NAME -s SKILL [-s SKILL]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `SKILL` (one or multiple allowed): Skill(s) to be marked as no longer required in the group. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `unmark-important CS2103T G18 -s C++ -s Java`

### Suggest courseMates for group: `suggest-mate`

Searches courseMates that has any of the required skills from the group that is not fulfilled yet. CourseMates already in the group will not be listed.

Format: `suggest-mate GROUP_NAME`

Parameters:
- `GROUP_NAME`: Name of the existing group.

Examples:
- `suggest-mate CS2103T G18`

<div style="page-break-after: always;"></div>


### Delete a group: `delete-group`
Deletes a group.

Format: `delete-group GROUP_NAME`

Example: `delete-group CS2103T G18`

Parameters:
- `GROUP_NAME`: Name of the existing group.


### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Save and load the state of app

Load the saved data when starting the app. Save the state of courseMates and groups every time it is updated via adding or editing. This should run automatically on start and subsequently after a command that alters the state of the app (e.g. add, edit).

### Editing the data file

MatchMate data are saved automatically as a JSON file `[JAR file location]/data/matchmate.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, MatchMate will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the MatchMate to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MatchMate home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add courseMate**    | `add NAME -e EMAIL [-p PHONE_NUMBER] [-t TELEGRAM_HANDLE] [-s SKILL]...​` <br> e.g., `add John Doe -e johndoe@example.com -p 87654321 -t johndoe -s Leadership -s C++`
**Add group member**    |   `add-member GROUP_NAME -cm COURSEMATE [-cm COURSEMATE]...` <br> e.g., `add-member CS2103T G18 -cm Ivan -cm ##`
**Add skill**   |   ` add-skill COURSEMATE -s SKILL [-s SKILL]...` <br> e.g., `add-skill John Doe -s C++ -s Leadership`
**Create group**    |   `create-group GROUP_NAME [-t TELEGRAM_CHAT_URL] [-cm COURSEMATE]... [-s SKILL]...` <br> e.g., `create-group CS2103T G18 -cm John -s C++ -s Java -cm #2 -t https://t.me/+WDTg34uuUlH8Ml2d`
**Delete courseMate**  | `delete COURSEMATE` <br> e.g., `delete John`
**Delete group**    | `delete-group GROUP_NAME` <br> e.g., `delete-group CS2103T G18`
**Delete group member**    |   `delete-member GROUP_NAME -cm COURSEMATE [-cm COURSEMATE]...` <br> e.g., `delete-member CS2103T G18 -cm Ivan -cm #1`
**Delete skill** | `delete-skill COURSEMATE -s SKILL [-s SKILL]...` <br> e.g., `delete-skill John Doe -s C++ -s Leadership `
**Edit courseMate**   | `edit COURSEMATE [-n NEW_NAME] [-e NEW_EMAIL] [-p NEW_PHONE_NUMBER] [-t NEW_TELEGRAM_HANDLE]​`<br> e.g.,`edit John -p 98765432 -e johndoe@gmail.com -t johndoe1234`
**Edit group telegram chat URL**    | `edit-tg-chat-url GROUP_NAME -t NEW_TELEGRAM_CHAT_URL` <br> e.g., `edit-tg-chat-url CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`
**Find courseMate**   | `find-mate KEYPHRASE`<br> e.g., `find John`
**Find group**   | `find-group KEYPHRASE`<br> e.g., `find CS2103T`
**Help**   | `help`
**List**   | `list`
**Mark skill important in group**   | `mark-important GROUP_NAME -s SKILL [-s SKILL]...` <br> e.g., `mark-important CS2103T G18 -s C++ -s Java`
**Require skill in group**    | `require-skill GROUP_NAME -s SKILL [-s SKILL]...` <br> e.g., `require-skill CS2103T G18 -s C++ -s Java`
**Suggest courseMates for group** | `suggest-mate GROUP_NAME` <br> e.g., `suggest-mate CS2103T G18`
**Unmark skill important in group**   | `unmark-important GROUP_NAME -s SKILL [-s SKILL]...` <br> e.g., `unmark-important CS2103T G18 -s C++ -s Java`
**Unrequire skill in group**    | `unrequire-skill GROUP_NAME -s SKILL [-s SKILL]...` <br> e.g., `unrequire-skill CS2103T G18 -s C++ -s Java`
