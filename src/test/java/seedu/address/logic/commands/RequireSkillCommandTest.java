package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCourseMates.getTypicalContactList;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_NAME_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_SKILL_LIST_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_SKILL_LIST_3;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.coursemate.CourseMate;
import seedu.address.model.coursemate.Name;
import seedu.address.model.coursemate.QueryableCourseMate;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.skill.Skill;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RequireSkillCommand}.
 */
public class RequireSkillCommandTest {
    private final Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalGroupList());
    private final Model emptyGroupListModel =
            new ModelManager(getTypicalContactList(), new UserPrefs(), new GroupList());
    private final Set<Skill> skillSet1 = new HashSet<>(SAMPLE_SKILL_LIST_1);
    private final Set<Skill> skillSet3 = new HashSet<>(SAMPLE_SKILL_LIST_3);

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new RequireSkillCommand(null, null));
    }

    @Test
    public void execute_nullParameters_throwsNullPointerException() {
        RequireSkillCommand requireSkillCommand = new RequireSkillCommand(SAMPLE_GROUP_NAME_1, skillSet1);
        assertThrows(NullPointerException.class, () ->
                requireSkillCommand.execute(null));
    }

    @Test
    public void execute_groupNotInList_throwsCommandException() {
        RequireSkillCommand requireSkillCommand = new RequireSkillCommand(SAMPLE_GROUP_NAME_1, skillSet1);
        assertThrows(CommandException.class, () -> requireSkillCommand.execute(emptyGroupListModel));
    }

    @Test
    public void execute_groupInListNoCommonSkills_runsNormally() {
        RequireSkillCommand requireSkillCommand = new RequireSkillCommand(SAMPLE_GROUP_NAME_1, skillSet3);
        assertDoesNotThrow(() -> requireSkillCommand.execute(model));
    }

    @Test
    public void execute_groupInListDuplicateSkills_runsNormally() {
        RequireSkillCommand requireSkillCommand = new RequireSkillCommand(SAMPLE_GROUP_NAME_1, skillSet1);
        assertDoesNotThrow(() -> requireSkillCommand.execute(model));
    }

}
