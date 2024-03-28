package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEMATE;
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
 * Un-confirms that a member is no longer not confirmed to be in a group.
 */
public class UnconfirmMemberCommand extends Command {
    public static final String COMMAND_WORD = "unconfirm-member";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unconfirm that a member has is no longer confirmed to be in a group. "
            + "CourseMates can be specified either by name or by the '#' notation.\n"
            + "Parameters: NAME (group must exist) "
            + "[" + PREFIX_COURSEMATE + " COURSEMATE]\n"
            + "Example: " + COMMAND_WORD + " CS2103T GROUP "
            + PREFIX_COURSEMATE + " #1 "
            + PREFIX_COURSEMATE + " John Doe";
    public static final String MESSAGE_MEMBERS_NOT_IN_GROUP =
            "Some of the specified members are not in the group";
    public static final String MESSAGE_SUCCESSFULLY_CONFIRMED = "Group successfully modified, Name: %1$s\n"
            + "%2$s status of members have been set to not confirmed!";

    private final Set<QueryableCourseMate> queryableCourseMateSet;
    private final Name groupName;

    /**
     * Basic constructor for {@code UnconfirmMemberCommand}.
     * @param queryableCourseMateSet set containing the queryableCourseMate to be added
     */
    public UnconfirmMemberCommand(Name groupName, Set<QueryableCourseMate> queryableCourseMateSet) {
        requireAllNonNull(groupName, queryableCourseMateSet);
        this.groupName = groupName;
        this.queryableCourseMateSet = queryableCourseMateSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<List<CourseMate>> courseMateSet;
        List<QueryableCourseMate> queryableCourseMates = new ArrayList<>();
        for (QueryableCourseMate q : queryableCourseMateSet) {
            queryableCourseMates.add(q);
        }

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
        System.out.println("init Group: " + modifiedGroup.asUnmodifiableObservableList());

        List<CourseMate> courseMateList = new ArrayList<>();
        int index = 0;
        for (List<CourseMate> courseMateUnconfirmList: courseMateSet) {
            //If there are more than 1 matching name
            if (courseMateUnconfirmList.size() > 1) {
                return new SimilarNameCommand(queryableCourseMates.get(index)).execute(model);
            }
            courseMateList.add(courseMateUnconfirmList.get(0));
            index += 1;
        }

        try {
            for (CourseMate courseMate: courseMateList) {
                CourseMate targetCourseMate = modifiedGroup.findCourseMate(courseMate.getName()).get(0);
                CourseMate editedCourseMate = unconfirmCourseMate(courseMate);
                modifiedGroup.setCourseMate(targetCourseMate, editedCourseMate);
            }
        } catch (CourseMateNotFoundException e) {
            throw new CommandException(MESSAGE_MEMBERS_NOT_IN_GROUP, e);
        }

        model.setGroup(toModify, modifiedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(
                String.format(MESSAGE_SUCCESSFULLY_CONFIRMED, groupName,
                        courseMateList.size()), false, false, true);
    }

    private static CourseMate unconfirmCourseMate(CourseMate target) {
        return new CourseMate(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getTelegramHandle(),
                target.getSkills(),
                target.getRating(),
                false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnconfirmMemberCommand)) {
            return false;
        }

        UnconfirmMemberCommand otherUnconfirmMemberCommand = (UnconfirmMemberCommand) other;
        return otherUnconfirmMemberCommand.groupName.equals(groupName)
                && otherUnconfirmMemberCommand.queryableCourseMateSet.equals(queryableCourseMateSet);
    }
}

