package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCourseMates.getTypicalContactList;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_4;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_NAME_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_NAME_4;
import static seedu.address.testutil.TypicalGroups.SAMPLE_QUERYABLE_SET_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_QUERYABLE_SET_2;
import static seedu.address.testutil.TypicalGroups.SAMPLE_QUERYABLE_SET_4;
import static seedu.address.testutil.TypicalGroups.SAMPLE_UNQUERYABLE_SET_1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactList;
import seedu.address.model.GroupList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coursemate.ContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the model) and unit tests for {@code ConfirmMemberCommand}.
 */
public class ConfirmMemberCommandTest {
    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalGroupList());
    private final Model emptyGroupListModel =
            new ModelManager(getTypicalContactList(), new UserPrefs(), new GroupList());

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ConfirmMemberCommand(null, null));
    }

    @Test
    public void execute_nullParameters_throwsNullPointerException() {
        ConfirmMemberCommand confirmMemberCommand = new ConfirmMemberCommand(
                SAMPLE_GROUP_NAME_1, SAMPLE_QUERYABLE_SET_1);
        assertThrows(NullPointerException.class, () -> confirmMemberCommand.execute(null));
    }

    @Test
    public void execute_groupNotInList() {
        ConfirmMemberCommand confirmMemberCommand = new ConfirmMemberCommand(
                SAMPLE_GROUP_NAME_1, SAMPLE_QUERYABLE_SET_1);
        assertThrows(CommandException.class, () -> confirmMemberCommand.execute(emptyGroupListModel));

    }

    @Test
    public void execute_groupInListMemberNotInList_throwsCommandException() {
        ConfirmMemberCommand confirmMemberCommand = new ConfirmMemberCommand(
                SAMPLE_GROUP_NAME_1, SAMPLE_UNQUERYABLE_SET_1);
        assertThrows(CommandException.class, () -> confirmMemberCommand.execute(model));
    }

    @Test
    public void execute_groupInListMemberNotInGroup_throwsCommandException() {
        ConfirmMemberCommand confirmMemberCommand =
                new ConfirmMemberCommand(SAMPLE_GROUP_NAME_1, SAMPLE_QUERYABLE_SET_2);
        assertTrue(model.hasGroup(SAMPLE_GROUP_1));
        assertThrows(CommandException.class, () -> confirmMemberCommand.execute(model));
    }

    @Test
    public void execute_groupInListMemberInGroup_runsNormally() {
        ConfirmMemberCommand confirmMemberCommand =
                new ConfirmMemberCommand(SAMPLE_GROUP_NAME_1, SAMPLE_QUERYABLE_SET_1);
        assertDoesNotThrow(() -> confirmMemberCommand.execute(model));
    }

    @Test
    public void execute_similarCourseMates_runsNormally() {
        ConfirmMemberCommand confirmMemberCommand = new ConfirmMemberCommand(
                SAMPLE_GROUP_NAME_4, SAMPLE_QUERYABLE_SET_4);
        assertTrue(model.hasGroup(SAMPLE_GROUP_4));
        String expectedMessage = String.format(Messages.MESSAGE_SIMILAR_COURSE_MATE_NAME, 4, "a");

        Model expectedModel = new ModelManager(
                new ContactList(model.getContactList()), model.getUserPrefs(), model.getGroupList());
        ContainsKeywordPredicate predicate = new ContainsKeywordPredicate("a");
        expectedModel.updateFilteredCourseMateList(predicate);

        assertCommandSuccess(confirmMemberCommand, model, expectedMessage, expectedModel, true);
    }
}
