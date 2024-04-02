# MatchMate User Guide

Matchmate is a **desktop app to create groups among their friends / acquaintances,
as well as to allow students to find balanced groups with diverse skillsets out of their own contact list.** 
It is optimized for Command Line Interface space(CLI) while still having the benefits of a Graphical User Interface (GUI). 

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `matchmate.jar`

1. Copy the file to the folder you want to use as the _home folder_ for your app.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar matchmate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.
   
   * `add John Doe -p 87654321 -e johndoe@example.com -s Leadership -s C++`: Adds a contact named `John Doe` to the contact list.

   * `delete #3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## User Interface

The UI consists of four main components:

![Ui Components](images/ui-structure.png)

1. **Command Box**<br>
   You can type your commands here and press Enter to execute them.
2. **CourseMate List Panel**<br>
   Displays the list of courseMates.
3. **CourseMate Detail Panel**<br>
   Displays the details of a selected courseMate.
4. **Group List Panel**<br>
   Displays the list of groups.

You can use the TAB key to switch between the command box and the coursemate list panel. You can also press ENTER to select a coursemate from the coursemate list panel. The selected coursemate will be displayed in the coursemate detail panel.

In the command box, you can use the UP and DOWN arrow keys to navigate through your command history.

<box type="tip" seamless>

**Example**: If you want to add a few selected coursemates to a group, you can:
1. Select the first coursemate in the coursemate list panel and press ENTER. The coursemate will be displayed in the coursemate detail panel.
2. Type `add-member GROUP_NAME -cm ##` in the command box and press ENTER.
3. Press TAB to switch to the coursemate list panel. Select the next coursemate and press ENTER.
4. Press TAB to switch back to the command box and press UP to retrieve the previous command. Press ENTER to execute the command.
5. Repeat steps 3 and 4 for the remaining coursemates.

This way, you can quickly add multiple coursemates to a group without having to type the coursemate names each time.

</box>

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add NAME`, `NAME` is a parameter which can be used as `add John Doe`.

* Items in square brackets are optional.<br>
  e.g `add NAME [-p PHONE_NUMBER]` can be used as `add John Doe -p 8762318` or as `add John Doe`.

* Items with `...`​ after them can be used multiple times including zero times.<br>
  e.g. `[-s SKILL]...​` can be used as ` ` (i.e. 0 times), `-s C++`, `-s C++ -s Python` etc.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`


### Adding a courseMate: `add`

Adds a contact to the contact list. A name will be required, and all other information will be optional.

Format: `add NAME [-p PHONE_NUMBER] [-e EMAIL] [-t TELEGRAM_HANDLE] [-s SKILL]...​`

Parameters:
- `NAME`: Name of the contact. It can be any string except that it cannot start with the hashtag (#) symbol , and words cannot start with the hyphen (-) character.
- `PHONE_NUMBER` (optional): Phone number of the contact. Accepts strings with numeric characters only.
- `EMAIL` (optional): Email of the contact. Accepts any strings without spaces.
- `TELEGRAM_HANDLE` (optional): Telegram handle of the contact. Accepts strings formed by alphanumeric characters and underscores only, and its length must be between 5 and 32 characters.
- `SKILL` (zero or multiple allowed): Skill(s) of the courseMate. Accepts any strings, except that words cannot start with the hyphen (-) character. 

<box type="tip" seamless>

**Tip:** A courseMate can have any number of skills (including 0)
</box>


Examples:

- `add John Doe`
- `add John Doe -p 87654321 -e johndoe@example.com -t johndoe -s Leadership -s C++`

### Listing all courseMates : `list`

Lists all contacts in the contact list.

Format: `list`

Expected output:
- `Message: Listed all contacts`


### Editing a courseMate : `edit`

Edits an existing contact with a given name. This command supports changing the name, phone number and email.

Format: `edit COURSEMATE [-n NEW_NAME] [-p NEW_PHONE_NUMBER] [-e NEW_EMAIL] [-t NEW_TELEGRAM_HANDLE]`

Parameters:
- `COURSEMATE`: Name of the existing contact or aliases (substrings or in hashtag (#) notation).
- `NEW_NAME` (optional): New name of the contact. It can be any string except that it cannot start with the hashtag (#) symbol, and words cannot start with the hyphen (-) character.
- `NEW_PHONE_NUMBER` (optional): New phone number of the contact. Accepts strings with numeric characters only.
- `NEW_EMAIL` (optional): New email of the contact. Accepts any strings without spaces.
- `NEW_TELEGRAM_HANDLE` (optional): New Telegram handle of the contact. Accepts strings formed by alphanumeric characters and underscores only, and its length must be between 5 and 32 characters.
    
<box type="tip" seamless>

**Tip:**

Instead of supplying a full name to identify an existing contact, you can use the following shortcuts:
- `#1`, `#2`, ..., `#n` (where n is the number of contacts currently displayed in the contact list panel) – `#k` references the k-th contact currently displayed in the contact list panel.


- `##` – References the contact currently displayed in the detailed view panel.


- A substring of the name instead of the full name.
    - In the event of multiple matches (the substring appears in multiple contacts), you will receive the following message: `There are x course mates with similar names.
      Retry the command by specifying the index of the contact in the list, example: #1.` and the list of matching contacts will be displayed in the contact list panel.
    - No side effects will be made by the current command.
    - You should retry the command by finding the contact on the list and using the hashtag notation (`#`) to identify the contact.

  Example:
    - You have the following contacts listed in the contact list panel:

      `#1 Benson`

      `#2 Ben`

      Typing `edit Ben` will display a warning message for having multiple matches.
    - Case 1 Edit Benson:
        - Type  `edit Benson ...` or just its substring `edit Bens ...`
        - Alternatively, you can use the alias `edit #1 ...`
    - Case 2 Edit Ben:
        - Type `edit #2 ...` as you must specify its index in the list.
</box>

Examples:
- `edit John Doe -n Joe Schmo`
- `edit #1 -n Joe Schmo`
- `edit John -p 98765432 -e johndoe@gmail.com`

### Add a skill to a contact : `add-skill`
Adds a list of skills to a contact.

Format: `add-skill COURSENAME [-s SKILL]...`

Parameters:
- `COURSENAME`: Name of the existing coursemate. Accepts aliases (substrings or in hashtag (#) notation).
- `SKILL` (zero or multiple allowed): Skill(s) of the courseMate. Accepts any strings, except that words cannot start with the hyphen (-) character.

Examples:
- `add-skill John Doe -s C++ -s Leadership`
- `add-skill ##`

  This command does nothing but it will be executed successfully.
  
### Delete a skill from a contact  : `delete-skill`
Deletes a list of skills from a contact.

Format: `delete-skill COURSEMATE [-s SKILL]...`

Parameters:
- `COURSEMATE`: Name of the existing coursemate. Accepts aliases (substrings or in hashtag (#) notation).
- `SKILL`: (zero or multiple allowed): Skill(s) of the courseMate. These must be existing skills that the contact contains.

Examples:
- `delete-skill John Doe -s C++ -s Leadership`
- `delete-skill ##`

    This command does nothing but it will be executed successfully.

### Search contacts with a keyword: `find`

Searches contacts that include a matching keyword, in their name or skills. More relevant fields can be supported in the future.

To match a contact’s skill, the keyword should match exactly with the skill. To match a contact’s name, the keyword can be a case-insensitive substring of the name.

Format: `find KEYWORD`

Parameters:
- `KEYWORD`: The keyword of the search. Accepts any strings.

Examples:
- `find John`
- `find C++`
- `find CS2103T G18`

### Deleting a contact : `delete`

Deletes a contact with a given name.

Format: `delete COURSEMATE`

Parameters:
- `COURSEMATE`: Name of the existing coursemate. Accepts aliases (substrings or in hashtag (#) notation).

Examples:
- `delete ##`
- `delete John`

### Create group project: `create-group`

Creates a group project. You can specify the coursemates in the group when creating the group, or choose to add them later.

Format: `create-group GROUP_NAME [-t TELEGRAM_CHAT_URL] [-cm COURSEMATE]...`

Parameters:
- `GROUP_NAME`: The group name. Accepts any strings, except that words cannot start with the hyphen (-) character.
- `TELEGRAM_CHAT_URL` (optional): The URL of the Telegram chat for the group. Accepts strings that start with `https://t.me/` and followed by alphanumeric characters and the special characters `+`, `-` and `_`.
- `COURSEMATE` (zero or multiple allowed): Name of the existing coursemates to be added to the group. Accepts aliases (substrings or in hashtag (#) notation).

Examples:
- `create-group CS2103T G18`
- `create-group CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`
- `create-group CS2103T G18 -cm John -cm #2`

### Add coursemates to group: `add-member`
Adds some team members to an existing group.

Format: `add-member GROUP_NAME [-cm COURSEMATE]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `COURSEMATE`: Name of the existing coursemates to be added to the group. Accepts aliases (substrings or in hashtag (#) notation).

Examples:
- `add-member CS2103T G18 -cm Ivan -cm #1`
- `add-member CS2103T G18 -cm ##`

<box type="tip" seamless>

**Tip:** Different from coursemate names, group names must be an exact match.
</box>

### Delete coursemates from group: `delete-member`

Deletes some team members from an existing group.

Format: `delete-member GROUP_NAME [-cm COURSEMATE]...`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `COURSEMATE`: Name of the existing coursemates to be deleted from the group. Accepts aliases (substrings or in hashtag (#) notation).

Examples:
- `delete-member CS2103T G18 -cm Ivan -cm #1`
- `delete-member CS2103T G18 -cm ##`

### Edit the telegram chat URL of a group: `edit-tg-chat-url`

Edits the telegram chat URL of a group.

Format: `edit-tg-chat-url GROUP_NAME [-t NEW_TELEGRAM_CHAT_URL]`

Parameters:
- `GROUP_NAME`: Name of the existing group.
- `NEW_TELEGRAM_CHAT_URL`: New URL of the Telegram chat for the group. Accepts strings that start with `https://t.me/` and followed by alphanumeric characters and the special characters `+`, `-` and `_`.

Examples:
- `edit-tg-chat-url CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`


### Delete a group: `delete-group`
Deletes a group.

Format: `delete-group GROUP_NAME`

Example: `delete-group CS2103T G18`

Parameters:
- `GROUP_NAME`: Name of the existing group.


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Save and load the state of app

Load the saved data when starting the app. Save the state of coursemates and groups every time it is updated via adding or editing. This should run automatically on start and subsequently after a command that alters the state of the app (e.g. add, edit).

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

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add coursemate**    | `add NAME [-p PHONE_NUMBER] [-e EMAIL] [-t TELEGRAM_HANDLE] [-s SKILL]...​` <br> e.g., `add John Doe -p 87654321 -e johndoe@example.com -t johndoe -s Leadership -s C++`
**Add group member**    |   `add-member GROUP_NAME [-cm COURSEMATE]...` <br> e.g., `add-member CS2103T G18 -cm Ivan -cm ##`
**Add skill**   |   ` add-skill COURSEMATE [-s SKILL]...` <br> e.g., `add-skill John Doe -s C++ -s Leadership`
**Clear**  | `clear`
**Create group**    |   `create-group GROUP_NAME [-t TELEGRAM_CHAT_URL] [-cm COURSEMATE]...` <br> e.g., `create-group CS2103T G18 -cm John -cm #2`
**Delete coursemate**  | `delete COURSEMATE` <br> e.g., `delete John`
**Delete group**    | `delete-group GROUP_NAME` <br> e.g., `delete-group CS2103T G18`
**Delete group member**    |   `delete-member GROUP_NAME [-cm COURSEMATE]...` <br> e.g., `delete-member CS2103T G18 -cm Ivan -cm #1`
**Delete skill** | `delete-skill COURSEMATE [-s SKILL]...` <br> e.g., `delete-skill John Doe -s C++ -s Leadership `
**Edit coursemate**   | `edit COURSEMATE [-n NEW_NAME] [-p NEW_PHONE_NUMBER] [-e NEW_EMAIL] [-t NEW_TELEGRAM_HANDLE]​`<br> e.g.,`edit John -p 98765432 -e johndoe@gmail.com -t johndoe1234`
**Edit group telegram chat URL**    | `edit-tg-chat-url GROUP_NAME [-t NEW_TELEGRAM_CHAT_URL]` <br> e.g., `edit-tg-chat-url CS2103T G18 -t https://t.me/+WDTg34uuUlH8Ml2d`
**Find**   | `find KEYWORD`<br> e.g., `find John`
**List**   | `list`
**Help**   | `help`
