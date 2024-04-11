---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# MatchMate Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

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

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a courseMate).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the contact data i.e., all `CourseMate` objects (which are contained in a `UniqueCourseMateList` object).
* stores the group data i.e., all `Group` objects (which are contained in a `UniqueGroupList` object).
* stores the currently 'selected' `CourseMate` or `Group` objects (e.g., results of a search query) as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<CourseMate>` or `ObservableList<Group>` objects that can be 'observed' e.g. the UI can be bound to these lists so that the UI automatically updates when the data in the lists change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### [UI] Coursemate Detail Panel

#### Implementation

The courseMate detail panel in the UI shows the details of a selected or recently processed courseMate. It is implemented in the `CourseMateDetailPanel` class.

The `CourseMateDetailPanel` class is mutable, the displayed courseMate can be change by calling the `CourseMateDetailPanel#loadCourseMate(CourseMate)` method.

The following shows the sequence diagrams of how the UI component loads the courseMates into the courseMate detail panel dynamically.

1. The user selects a courseMate from the courseMate list panel.

   <puml src="diagrams/CourseMateListSelectSequenceDiagram.puml" alt="CourseMateListSelectSequenceDiagram" />

   The main window implements the functional interface `CourseMateDetailPanel#CourseMateSelectHandler` to reflect the selected courseMate in the courseMate detail panel and the `Logic`, which saves the selected courseMate in the `Model` used for future commands with the `##` notation.

2. The user executes a command that modifies the selected courseMate.

    <puml src="diagrams/ProcessedCourseMateSequenceDiagram.puml" alt="ProcessedCourseMateSequenceDiagram" />

    The `CommandResult` object returned by the `Logic` component contains a flag that indicates if the command involves one processed courseMate. If so, `CourseMateDetailPanel` is updated with the processed courseMate.

Two types of selection in the courseMate list panel are supported: double click and pressing the enter key. This requires two different event handlers to be implemented in the `CourseMateListPanel` class as shown below. These event handlers are instantiated and set as the event handlers for the `ListView` object in the constructor of `CourseMateListPanel`.

<puml src="diagrams/CourseMateListPanelClassDiagram.puml" alt="CourseMateListPanelClassDiagram" />


#### Alternatives Considered

* **Alternative 1:** The courseMate detail panel is immutable, and a new panel is created for each courseMate.
  * Pros: Easier to implement since the other UI components no longer have to keep a reference to the courseMate detail panel.
  * Cons: It makes it unclear that `MainWindow` and `CourseMateDetailPanel` have a one-to-one whole-part relationship.

* **Alternative 2:** Allow the courseMate list panel to directly update the courseMate detail panel.
  * Pros: Simplifies the code as the `MainWindow` no longer has to act as a middleman. The functional interface `CourseMateDetailPanel#CourseMateSelectHandler` can be removed.
  * Cons: The `CourseMateListPanel` and `CourseMateDetailPanel` are more tightly coupled. This increases maintenance costs.

* **Alternative 3:** Give full control to the `Logic` component to update the courseMate detail panel.
  * Pros: We no longer need to include an extra field in the `CommandResult` object to indicate if the command involves one processed courseMate.
  * Cons: The `Logic` component is now responsible for updating the UI, which is not ideal as the `Logic` component should not be aware of the UI's click and press events.

### [Model & Storage] Groups

#### Implementation

A `Group` is a list of unique courseMates with a few extra metadata. Thus, it makes sense for it to inherit from `UniqueCourseMateList`.

A `Group` is given a unique case-sensitive name when constructed. It may also be given a set of unique `Skill` objects, unique member `CourseMate` objects, and an optional `TelegramChat` object.

`CourseMate` objects are kept unique by ensuring no two `CourseMate` objects have the same name, using the `CourseMate::isSameCourseMate` method. `Skill` and `Name` objects are kept unique using their respective `equals` methods.

A `GroupList` is also used to maintain the list of all unique groups created. The model manager maintains a single copy of this `GroupList`.

The `GroupList` object maintains a single copy of a `UniqueGroupList`, this class enforces uniqueness using `Group::isSameGroup` and also maintains an observable copy of the list for the UI.

<puml src="diagrams/GroupClassDiagram.puml" alt="GroupClassDiagram" />

### Alternatives Considered

* **Alternative 1:** `GroupList` is a singleton class.
  * Pros: It makes sense that we only have a single `GroupList` at any one time in normal usage.
  * Cons: As with most singletons, it does make testing more difficult as different tests will have a harder time using different lists of groups.
* **Alternative 2:** `Group` does not inherit `UniqueCourseMateList`.
  * Pros: It isn't exactly clear-cut that `Group` *is a* `UniqueCourseMateList`, since `UniqueCourseMateList` is also used to display info to the UI through the observer pattern, causing potential for unneeded coupling.
  * Cons: It does make enough sense that a `Group` has *most* properties of a `UniqueCourseMateList`, using another class may also result in code duplication.
* **Alternative 3:** `Group` objects are given an ID instead of being kept unique through their names.
  * Pros: It allows for multiple `Group` objects to have the same name, and an ID makes more sense conceptually to differentiate objects than a `Name`.
  * Cons: It is not clear that multiple `Group` objects should be allowed to have the same name, it may also be harder to uniquely refer to groups in the app if you have to use an ID to differentiate.

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

John in a NUS computer science student taking various courses with a group project component. He wishes to create groups among his friends / acquaintances and find balanced groups with diverse skillsets out of his own contact list.

**Value proposition**:

To allow students to find balanced groups with diverse skillsets out of their own contact list.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                         | I want to …​                 | So that I can…​                                                        |
|----------|------------------------------------------------|------------------------------|------------------------------------------------------------------------|
| `* * *`  | student | easily add new courseMates with their information |  |
| `* * *`  | careless user | edit information in the courseMate list | fix typos or inaccurate information about my friends mistakenly inputted into the app |
| `* * *`  | careless user | delete an entry from the courseMate list | remove information mistakenly added to the app |
| `* * *`  | student | add or remove skills of a courseMate | remember the strengths of each courseMate and consider them during team formation |
| `* * *`  | lazy user | search through my list using specific keywords | avoid scrolling through the entire list |
| `* * *`  | student finding group project partners | search for courseMates out of my contact list with a specific skillset | find a partner who is interested in or good at that particular course or subject |
| `* * *`  | student forming group project teams | create a group project within the app and add courseMates to the group | remember who is already in the team |
| `* * *`  | student forming group project teams | remove courseMates from a group | maintain information correctness after some courseMates are mistakenly added to the group |
| `* * *`    | student forming group project teams | set some skills as required in a group | ensure that the group has the necessary skills to complete the project |
| `* * *`    | student forming group project teams | look for courseMate recommendations based on the required skills | find courseMates who can complement the skills of the existing group members |
| `* *`    | user who may not have the best eyesight | change the font size of texts in the app | I can adjust to a size most suited to me |
| `* *`    | clueless student new to using the app | know what skills I should look out for in my friends | |
| `* *`    | lazy user | autocomplete some commands with possible inputs | complete my tasks faster |
| `* *`    | busy user | use the "up" arrow key for the app to display the previous command | save time typing a series of similar commands with common substrings |
| `* *`    | new user | easily find a list of commands and how they are used | start using the app without difficulties |
| `* *`    | student finding group project partners among acquaintances | maintain the contact details of my friends (telegram handles) in the app | easily contact potential groupmates who I don't frequently contact |
| `* *`    | students working in many project teams | maintain the telegram group chat links in the app | easily contact my project groupmates for different projects |
| `* *`    | student finding group project partners | input the courses each of my friends are planning to take or confirmed to take | limit my search to friends taking that specific course only |
| `* *`    | student finding group project partners | mark courseMates as either friends or acquaintances | prioritise creating groups with some friends over acquaintances |
| `* *`    | student forming a group | set some skills as extremely important | prioritise those skills while searching for team members |
| `* *`    | student creating a group | search for possible combinations that match the required types of roles and skills | form project groups that require different kinds of roles or skills per member |
| `* *`    | student forming a group | save a certain filter or search setting with a label | reuse my past search setting when I take courses of similar nature |
| `* *`    | student forming a group | save the set of friends I already contacted with and their respective outcomes | know who else to consider and contact |
| `* *`    | user who primarily used other formats to organize contacts | import data from a file | save the hassle of manually adding to the contact list |
| `*`      | student bidding for tutorials of courses with group projects | maintain each potential partners' availability for different tutorial slots | decide on a common tutorial slot to bid for |
| `*`      | student with past group project experiences | rate and review my group members after completing a project together | remember their skills, work ethics and collaboration styles during the next team formation |
| `*`      | user with colour vision deficiency | customize the app's colour palette | better suit my visual needs and ensure that important elements and information are easily distinguishable |
| `*`      | busy user | create alias commands | run long repetitive commands using a shorter self-made command |



### Use cases

(For all use cases below, the **System** is `MatchMate` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a new contact**

**MSS**

1. User requests to add a new contact along with the data.
2. MatchMate adds the contact to the list.
3. MatchMate shows the updated list of contacts.

    Use case ends.

**Extensions**

* 1a. User inputs invalid or incomplete data. 
    * 1a1. MatchMate shows a message indicating the data is invalid or incomplete.
    
      Use case resumes at step 1.

**Use case: Delete a contact**

**MSS**

1. User requests to delete a contact.
2. MatchMate deletes the contact.

    Use case ends.

**Extensions**

* 1a. User inputs a contact that does not exist.
    * 1a1. MatchMate shows a message indicating that the contact cannot be found.

      Use case resumes at step 1.
* 1b. User inputs a name of which multiple contacts have the specified name as a substring.
  * 1b1. MatchMate filters and lists the contacts that has the name.

    Use case resumes at step 1.

**Use case: List all contacts**

**MSS**

1. User requests to list all contacts.
2. MatchMate shows all contacts.

    Use case ends.

**Use case: Edit a contact**

**MSS**

1. User requests to edit a contact along with the new data.
2. MatchMate adds the contact to the list.
3. MatchMate shows the updated list of contacts.

   Use case ends.

**Extensions**

* 1a. User requests to edit a contact that does not exist.
    * 1a1. MatchMate shows a message indicating that the contact cannot be found.
      
      Use case resumes at step 1.
* 1b. User inputs invalid or incomplete data.
    * 1b1. MatchMate shows a message indicating the data is invalid or incomplete.

      Use case resumes at step 1.
* 1c. User inputs a name of which multiple contacts have the specified name as a substring.
    * 1c1. MatchMate filters and lists the contacts that has the name.

      Use case resumes at step 1.

**Use case: Add skills to a contact**

**MSS**

1. User requests to add skills to a contact.
2. MatchMate appends the skills to the contact.
3. MatchMate shows the updated list of contacts.

    Use case ends.

**Extensions**

* 1a. User inputs incomplete data.
    * 1a1. MatchMate shows a message indicating incomplete data.

      Use case resumes at step 1.

* 1b. User inputs a contact that does not exist.
    * 1b1. MatchMate shows a message indicating that the contact cannot be found.

      Use case resumes at step 1.
  
* 1c. User inputs a skill that does not exist yet.
    * 1c1. MatchMate shows a warning message indicating that the skill is a new entry.

      Use case ends.

* 1d. User inputs a name of which multiple contacts have the specified name as a substring.
    * 1d1. MatchMate filters and lists the contacts that has the name.

      Use case resumes at step 1.

**Use case: Delete skills from a contact**

**MSS**

1. User requests to delete existing skills from a contact.
2. MatchMate removes the specified skills from the contact.
3. MatchMate shows the updated list of contacts.

    Use case ends.

**Extensions**

* 1a. User inputs incomplete data.
    * 1a1. MatchMate shows a message indicating incomplete data.

      Use case resumes at step 1.

* 1b. User inputs a contact that does not exist.
    * 1b1. MatchMate shows a message indicating that the contact cannot be found.

      Use case resumes at step 1.

* 1c. User inputs a skill the contact does not have.
    * 1c1. MatchMate shows a message indicating that the skill cannot be found.

      Use case resumes at step 1.

* 1d. User inputs a name of which multiple contacts have the specified name as a substring.
    * 1d1. MatchMate filters and lists the contacts that has the name.

      Use case resumes at step 1.

**Use case: Filter contacts based on keyword**

**MSS**

1. User requests to find contacts with the specified keyword.
2. MatchMate shows a list of the filtered contacts.

    Use case ends.

**Extensions**

* 1a. No contacts fulfill the filter search.
    * 1a1. MatchMate shows a message indicating no contacts can be found.

      Use case ends.

**Use case: Create a group**

**MSS**

1. User requests to create a group with a specified name.
2. MatchMate acknowledges the creation of the group.

   Use case ends.

**Extensions**

* 1a. User inputs a group name that already exists.
    * 1a1. MatchMate shows a message indicating the group already exists.

      Use case resumes at step 1.

**Use case: Delete a group**

**MSS**

1. User requests to delete a group.
2. MatchMate deletes the group.

   Use case ends.

**Extensions**

* 1a. User inputs a group that does not exist.
    * 1a1. MatchMate shows a message indicating that the group cannot be found.

      Use case resumes at step 1.

**Use case: Add a contact to a group**

**MSS**

1. User requests to add a contact to a group with a specified name or index from the displayed list.
2. MatchMate adds the contact to the group.
3. MatchMate shows the updated list of contacts in the specified group.

   Use case ends.

**Extensions**
  
* 1a. User inputs a name or index no contacts correspond to.
    * 1a1. MatchMate shows a message indicating the contact doesn't exist.

      Use case resumes at step 1.

* 1b. User inputs a group name that doesn't exist.
    * 1b1. MatchMate shows a message indicating the group doesn't exist.

      Use case resumes at step 1.

* 1c. User inputs a name of which multiple contacts have the specified name as a substring.
    * 1c1. MatchMate filters and lists the contacts that has the name.

      Use case resumes at step 1.

* 1d. The contact is already in the group.
    * 1d1. MatchMate shows a message indicating the contact is already in the group.

      Use case resumes at step 1.

### Non-Functional Requirements

1. **Environment**: Should work on any _mainstream_ OS as long as it has Java `11` or above installed.
2. **Performance**: Should respond to user interaction within 3 seconds at most for typical usage (unless it is lagging due to reasons external to the app).
3. **Performance**: Should be able to hold up to 1000 _courseMates_ without a noticeable sluggishness in performance (as specified above) for typical usage.
4. **Resilience**: Should gracefully handle commonly anticipated errors (e.g. incorrect _command_ input) without crashing or losing saved data.
5. **Accessibility**: Should notify the user whether a _command_ is successful or has failed.
6. **Accessibility**: Should be accessible to English speakers with average typing speed.
7. **Accessibility**: Usage of basic _commands_ (e.g. add, edit) should be learnable within a day.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Command**: A user input that will cause the application to perform an operation according to the MatchMate UserGuide
* **Coursemate**: A friend or classmate that you expect to form a _group_ based on certain _skills_ they might have
* **Group**: A grouping/team of _courseMates_ for a course, project, or activity
* **Skill**: Knowledge, ability, or experience that a _courseMate_ has 

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Creating an edit group command**: Currently, the user can only edit the group name of a project group by deleting the group and re-creating it. All information in the group will be cleared after that. We plan to implement an edit group command that allows the user to edit the group name without losing the information in the group, by supplying the original group name and the new group name.

2. **Improved filtering when adding or removing members in a group with similar names**: When trying to add or remove a member from a group using a substring of the member's name, the app currently lists all courseMates with the substring in their name, regardless of whether they are already in the group. This causes the user having to re-try the command using the hashtag notation unnecessarily. We plan to enhance this filtering by excluding members that are already in the group for the `add-member` command, and excluding members that are not in the group for the `remove-member` command.

3. **Provide warnings for `require-skill` and `unrequire-skill` when skill is already required / unrequired**: Currently, the app does not provide any feedback when a user tries to require a skill that is already required, or unrequire a skill that is already unrequired. We plan to provide a warning message in such cases to inform the user that the command has no effect, and suggest the user to check for typos.

4. **Provide warnings for `mark-important` and `unmark-important` when skill is already marked / unmarked as important**: Currently, the app does not provide any feedback when a user tries to mark a skill as important that is already marked as important, or unmark a skill that is already unmarked. We plan to provide a warning message in such cases to inform the user that the command has no effect, and suggest the user to check for typos.

5. **Provide warnings for `add-skill` and `delete-skill` when skill is already added / deleted**: Currently, the app does not provide any feedback when a user tries to add a skill that is already added, or delete a skill that is already deleted. We plan to provide a warning message in such cases to inform the user that the command has no effect, and suggest the user to check for typos.

6. **Allow case-insensitive command names**: Currently, the app only recognizes command names in lowercase. We plan to allow users to enter the command names in any case (e.g. `Add`, `aDd`, `ADD`), and the app will recognize them as the same command.



--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. To quit and shut down the app, either type exit, or click on the x button on the top right corner.

### Adding a courseMate
1. Prerequisite: The MatchMate app has been launched.

2. Test case: `add Ivan -e ivan@gmail.com -p 9427318 -s C++`<br>
   Expected: Successfully adds ivan with the corresponding email, phone number, and skill

3. Test case: `add Benson -e benson@gmail.com`<br>
   Expected: Successfully adds Benson with his email. Phone number and skills are optional fields.

### Editing a courseMate
1. Prerequisites: You have added Benson and Ivan into your contact list, by following the commands in the previous section.

1. Test case: `edit #1 -n Alex`<br>
Expected: Edits the first contact's name to Alex

1. Test case: `edit Ivan -n Ivan Tan`<br>
Expected: Edits Ivan's name to Ivan Tan

1. Test case: `edit Ben -n Benson CS`<br>
Expected: Edits Benson's name to Benson CS. Edit utilizes substring search, and because there's only 1 contact with a Ben substring, thus Benson's name gets updated to Benson CS.

### Adding a skill to a courseMate
1. Prerequisites: You have added Benson and Ivan into your contact list, by following the commands in the previous section.

1. Test case: `add-skill Ivan Tan -s C++`<br>
Expected: Adds C++ skill to Ivan Tan

1. Test case: `add-skill Benson CS -s JS -s SQL`<br>
Expected: Adds JS and SQL skill to Benson, and also gives a warning that one if not more skils added have not been previously added to other contacts.
This error message serves to warn for any potential typos when adding skills

### Deleting a skill from a courseMate
1. Prerequisites: You have added C++ and React skills to Ivan as well as JS and SQL skills to Benson, by following the commands in the previous section.

1. Test case: `delete-skill Ivan Tan -s React`<br>
Expected: React skill is deleted from Ivan Tan

1. Test case: `delete-skill Benson CS -s JS -s SQL` <br>
Expected: JS and SQL skills are deleted from Benson

### Search courseMates with keywords
1. Prerequisites: You are using the preloaded data, and have only added two new contacts, Ivan Tan and Benson CS.

1. Test case: `find-mate Ivan` <br>
Expected: Finds Ivan Tan in the contact list

1. Test case: `find-mate a` <br>
Expected: Finds all courseMates whose names/skills contain a

1. Test case: `find-mate C++` <br>
Expected: Finds all courseMates whose names/skills contain C++

### Deleting a courseMate

1. Prerequisites: List all courseMates using the `list` command. Multiple courseMates in the list.

1. Test case: `delete #1` <br>
   Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

1. Test case: `delete #0` <br>
   Expected: No courseMate is deleted. Error details shown in the status message.

1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Rate a courseMate

1. Prerequisites: You are using the preloaded data, and have only added two new contacts, Ivan Tan and Benson CS.

1. Test case: `rate-mate Ivan Tan -r 4`<br>
Expected: Shows 4 stars out of 5 on Ivan's contact card.

2. Test case: `rate-mate Benson CS -r 6`<br>
Expected: Shows an error message saying that ratings can only be between 0 and 5.

### Creating a group
1. Prerequisites: You are using the preloaded data, and have only added two new contacts, Ivan Tan and Benson CS.

1. Test case: `create-group CS project -cm Ivan -t https://t.me/+WDTg34uuUlH8Ml2d -s C++` <br>
Expected: Creates a group called CS project, with Ivan as one of its group members. Also adds a telegram link for the group, and adds C++ as a skill required in the group.
The skill C++ is marked green as one of its members, Ivan, has C++ as one of his skills.

1. Test case: `create-group ES2660 -cm Benson -s Leadership` <br>
Expected: Creates a group called ES 2660, with Benson as one of its group members. Also adds a Leadership as a skill required in the group.
A warning is also given as leadership is not a skill that has been added anywhere. The skill Leadership is red as its only member, as Benson does not have Leadership skill

### Adding courseMates to a group
1. Prerequisites: You have followed all the steps along and added two new contacts Ivan and Benson, as well as created two new groups "CS project" and "ES2660"

2. Test case: `add-skill Ivan -s Leadership` then `add-member ES2660 -cm Ivan` <br>
Expected: The first command adds a skill called Leadership to Ivan, then add Ivan to the ES2660 group. As a result, the Leadership skill in ES2660 turns to green as one of its group members
has that skill

### Deleting courseMates from a group
1. Prerequisites: You have followed all the steps along and added Ivan and Benson to the corresponding groups "CS project" and "ES2660"

2. Test case: `delete-member ES2660 -cm Ivan` <br>
Expected: Deletes Ivan from the ES2660 group. This causes the skill Leadership to go back to red as it has not been fulfilled.

2. Test case: `delete-member CS project -cm Benson` <br>
Expected: The command fails as Benson is not in the CS project group. Relevant error message is displayed


### Edit group telegram url
1. Prerequisites: You have added the groups "CS project" and "ES2660"

2. Test case: `edit-tg-chat-url ES2660 -t https://t.me/+HWasdo2831Uasodi` <br>
Expected: Creates a telegram group chat link for ES2660

3. Test case: `edit-tg-chat-url ES2660 -t https://+HWasdo2831Uasodi` <br>
Expected: The command fails as the url given is not a telegram link. Relevant error message is displayed

### Require skills in a group
1. Prerequisites: You have added the groups "CS project" and "ES2660"

2. Test case: `require-skill ES2660 -s Presentation` <br>
Expected: Adds a new required skill for ES2660. Also gives a warning as Presentation is a skill that has not been added anywhere before.

### Unrequire skills in a group
1. Prerequisites: You have added the groups "CS project" and "ES2660", as well as added C++ skill for CS project as well as Leadership and Presentation skills for ES2660

2. Test case: `unrequire-skill ES2660 -s Presentation` <br>
Expected: Removes Presentation skill from ES2660

3. Test case: `unrequire-skill CS project -s Java` <br>
   Expected: The command fails as Java is not a required skill in CS project. Relevant error message is displayed


### Mark important skills in a group
1. Prerequisites: You have added the groups "CS project" and "ES2660", and CS project requires C++ skill while ES2660 requires Leadership skill

2. Test case: `mark-important ES2660 -s Leadership` <br>
Expected: Shows an exclamation mark besides the Leadership skill signifying that it is important

2. Test case: `mark-important CS project -s Java` <br>
   Expected: The command fails as Java is not a required skill in CS project. Relevant error message is displayed

### Unmark important skills in a group
1. Prerequisites: You have added the groups "CS project" and "ES2660", and CS project requires C++ skill while ES2660 requires Leadership skill

2. Test case: `unmark-important ES2660 -s Leadership` <br>
   Expected: Removes the exclamation mark besides the Leadership skill

### Suggest courseMates for group
1. Prerequisites: You have added the groups "CS project" and "ES2660", and CS project requires C++ skill while ES2660 requires Leadership skill. 
Ivan is in the CS project group and Benson is in the ES2660 group.

2. Test case: `suggest-mate CS project` <br>
Expected: Gives a message saying "All required skills have already been fulfilled. Consider adding what skills you're looking for?"

3. Test case: `suggest-mate ES2660` <br>
Expected: Shows Ivan Tan as the only courseMate that has the Leadership skill.

### Search groups with keywords
1. Prerequisites: You are using the preloaded data and have added the groups "CS project" and "ES2660"

2. Test case: `find-group CS` <br>
Expected: Shows 3 groups: CS2103T, CS2101, CS Project as 3 of them contains CS in the group name

### Delete a group
1. Prerequisites: You are using the preloaded data and have added the groups "CS project" and "ES2660"

2. Test case: `delete-group ES2660` <br>
Expected: Deletes the group ES2660
