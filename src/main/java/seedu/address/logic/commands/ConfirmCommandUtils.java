package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.coursemate.CourseMate;
import seedu.address.model.coursemate.Name;
import seedu.address.model.coursemate.QueryableCourseMate;
import seedu.address.model.coursemate.exceptions.CourseMateNotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Utility class that abstracts out the execution of (un)confirm-member command.
 */
public abstract class ConfirmCommandUtils extends Command {
    public static final String MESSAGE_MEMBERS_NOT_IN_GROUP =
            "Some of the specified members are not in the group";
    public static final String MESSAGE_SUCCESS = "Group successfully modified, Name: %1$s\n"
            + "%2$s status of members have been confirmed!";

    /**
     * Creates a copy of a target {@code CourseMate} except where
     * the {@code isConfirmed} status is specified by the input.
     */
    private static CourseMate createCourseMateByConfirmationStatus(CourseMate target, boolean isConfirmed) {
        return new CourseMate(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getTelegramHandle(),
                target.getSkills(),
                target.getRating(),
                isConfirmed);
    }

    /**
     * Executes the commands in the context of (un)confirm-member
     */
    public static CommandResult execute(
            Model model, Name groupName, Set<QueryableCourseMate> queryableCourseMateSet,
            boolean isConfirmed, String message_success) throws CommandException {
        requireAllNonNull(model, groupName, queryableCourseMateSet);
        Set<List<CourseMate>> courseMateSet;
        List<QueryableCourseMate> queryableCourseMates = new ArrayList<>(queryableCourseMateSet);

        try {
            courseMateSet = queryableCourseMateSet
                    .stream()
                    .map(model::findCourseMate)
                    .collect(Collectors.toSet());
        } catch (CourseMateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_MEMBERS_DONT_EXIST, e);
        }

        Group toModify;
        try {
            toModify = model.findGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_NAME);
        }

        Group modifiedGroup = new Group(toModify);

        List<CourseMate> courseMateList = new ArrayList<>();
        int index = 0;
        for (List<CourseMate> courseMateConfirmList: courseMateSet) {
            // If there are more than 1 matching name
            if (courseMateConfirmList.size() > 1) {
                return new SimilarNameCommand(queryableCourseMates.get(index)).execute(model);
            }
            courseMateList.add(courseMateConfirmList.get(0));
            index += 1;
        }
        try {
            for (CourseMate courseMate: courseMateList) {
                CourseMate targetCourseMate = modifiedGroup.findCourseMate(courseMate.getName()).get(0);
                CourseMate editedCourseMate = createCourseMateByConfirmationStatus(courseMate, isConfirmed);
                modifiedGroup.setCourseMate(targetCourseMate, editedCourseMate);
            }
        } catch (CourseMateNotFoundException e) {
            throw new CommandException(MESSAGE_MEMBERS_NOT_IN_GROUP, e);
        }

        model.setGroup(toModify, modifiedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(
                String.format(message_success, groupName,
                        courseMateList.size()), false, false, true);
    }
}
