package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCourseMates.IDA;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_1;
import static seedu.address.testutil.TypicalGroups.SAMPLE_GROUP_NAME_1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupList;

import org.junit.jupiter.api.Test;

import seedu.address.model.coursemate.CourseMate;
import seedu.address.model.coursemate.Phone;
import seedu.address.testutil.CourseMateBuilder;

/**
 * Unit tests for the {@code GroupList} class.
 */
public class GroupListTest {

    private final GroupList groupList = new GroupList();
    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroupList_replacesData() {
        GroupList newData = getTypicalGroupList();
        groupList.resetData(newData);
        assertEquals(newData, groupList);
    }

    @Test
    public void hasGroup_groupInGroupList_returnsTrue() {
        groupList.addGroup(SAMPLE_GROUP_1);
        assertTrue(groupList.hasGroup(SAMPLE_GROUP_1));
    }

    @Test
    public void setCourseMate_courseMateInGroup_courseMateIsEdited() {
        CourseMate oldCourseMate = IDA;
        String newPhone = "12345";
        CourseMate newCourseMate = new CourseMateBuilder(IDA).withPhone(newPhone).build();

        SAMPLE_GROUP_1.add(oldCourseMate);
        groupList.addGroup(SAMPLE_GROUP_1);

        groupList.setCourseMate(oldCourseMate, newCourseMate);

        assertEquals(groupList.findGroup(SAMPLE_GROUP_NAME_1)
                .findCourseMate(newCourseMate.getName())
                .get(0)
                .getPhone(),
                new Phone(newPhone));

        SAMPLE_GROUP_1.setCourseMate(oldCourseMate, newCourseMate);
        groupList.removeGroup(SAMPLE_GROUP_1);
        SAMPLE_GROUP_1.remove(
                SAMPLE_GROUP_1.findCourseMate(IDA.getName()).get(0));
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> groupList.getGroupList().remove(0));
    }
}
