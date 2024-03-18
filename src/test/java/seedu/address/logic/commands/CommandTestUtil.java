package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactList;
import seedu.address.model.Model;
import seedu.address.model.coursemate.ContainsKeywordPredicate;
import seedu.address.model.coursemate.CourseMate;
import seedu.address.testutil.EditCourseMateDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_SKILL_JAVA = "Java";
    public static final String VALID_SKILL_REACT = "React";

    public static final String INVALID_NAME_AMPERSAND = "James&";
    public static final String INVALID_PHONE_ALPHABET = "911a";
    public static final String INVALID_EMAIL_MISSING_AT = "bob!yahoo";
    public static final String INVALID_SKILL_ASTERISK = "hubby*";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + " " + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + " " + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + " " + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + " " + VALID_EMAIL_BOB;
    public static final String SKILL_DESC_REACT = " " + PREFIX_SKILL + " " + VALID_SKILL_REACT;
    public static final String SKILL_DESC_JAVA = " " + PREFIX_SKILL + " " + VALID_SKILL_JAVA;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " " + INVALID_NAME_AMPERSAND;
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + " " + INVALID_PHONE_ALPHABET;
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + " " + INVALID_EMAIL_MISSING_AT;
    public static final String INVALID_SKILL_DESC = " " + PREFIX_SKILL + " " + INVALID_SKILL_ASTERISK;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCourseMateDescriptor DESC_AMY;
    public static final EditCommand.EditCourseMateDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCourseMateDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withSkills(VALID_SKILL_REACT).build();
        DESC_BOB = new EditCourseMateDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_JAVA, VALID_SKILL_REACT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel, boolean showCourseMate) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, showCourseMate);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Confirms that the {@code recentlyProcessedCourseMate} in the model is edited with {@code editedCourseMate}.
     */
    public static void assertRecentlyProcessedCourseMateEdited(Model model, CourseMate editedCourseMate) {
        assertEquals(editedCourseMate, model.getRecentlyProcessedCourseMate());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the contact list, filtered courseMate list and selected courseMate in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ContactList expectedContactList = new ContactList(actualModel.getContactList());
        List<CourseMate> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCourseMateList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedContactList, actualModel.getContactList());
        assertEquals(expectedFilteredList, actualModel.getFilteredCourseMateList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the courseMate at the given {@code targetIndex} in the
     * {@code model}'s contact list.
     */
    public static void showCourseMateAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCourseMateList().size());

        CourseMate courseMate = model.getFilteredCourseMateList().get(targetIndex.getZeroBased());
        final String[] splitName = courseMate.getName().fullName.split("\\s+");
        model.updateFilteredCourseMateList(new ContainsKeywordPredicate(splitName[0]));

        assertEquals(1, model.getFilteredCourseMateList().size());
    }

}
