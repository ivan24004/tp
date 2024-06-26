package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEMATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

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
import seedu.address.model.group.TelegramChat;
import seedu.address.model.skill.Skill;

/**
 * Creates a group containing multiple unique CourseMates.
 * See {@code UniqueCourseMateList} for a the details of uniqueness.
 */
public class CreateGroupCommand extends Command {
    public static final String COMMAND_WORD = "create-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group containing any number of CourseMates, "
            + "CourseMates can be specified either by name or by the '#' notation.\n"
            + "Parameters: GROUP_NAME (cannot be empty, must be unique, and must be only alphanumeric) "
            + "[" + PREFIX_TELEGRAM + " TELEGRAM_CHAT_URL" + "] "
            + "[" + PREFIX_COURSEMATE + " COURSEMATE" + "]... "
            + "[" + PREFIX_SKILL + " SKILL" + "]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " CS2103T GROUP "
            + PREFIX_COURSEMATE + " #1 "
            + PREFIX_COURSEMATE + " John Doe "
            + PREFIX_SKILL + " C++ "
            + PREFIX_SKILL + " Java "
            + PREFIX_TELEGRAM + " https://t.me/+3Jh9eXVeRh7qoaIN";

    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the group list.";

    public static final String MESSAGE_GROUP_CREATED = "A group with name: %s was created.";

    public static final String MESSAGE_MEMBERS_DONT_EXIST = "Some of the included members could not be found.";

    private final Name groupName;
    private final Set<QueryableCourseMate> queryableCourseMateSet;
    private final Set<Skill> skillSet;
    private final TelegramChat telegramChat; // can be null

    /**
     * Basic constructor for {@code CreateGroupCommand}.
     * Creates the details for a group to be created.
     * @param groupName name of the group
     * @param queryableCourseMateSet set containing the queryableCourseMate in the group
     */
    public CreateGroupCommand(Name groupName,
                              Set<QueryableCourseMate> queryableCourseMateSet,
                              Set<Skill> skillSet, TelegramChat telegramChat) {
        requireAllNonNull(groupName, queryableCourseMateSet, skillSet);
        this.groupName = groupName;
        this.queryableCourseMateSet = queryableCourseMateSet;
        this.skillSet = skillSet;
        this.telegramChat = telegramChat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<QueryableCourseMate> queryableCourseMateList = new ArrayList<>(queryableCourseMateSet);
        List<List<CourseMate>> courseMateChoicesList;
        try {
            courseMateChoicesList = queryableCourseMateList
                    .stream()
                    .map(model::findCourseMate)
                    .collect(Collectors.toList());
        } catch (CourseMateNotFoundException e) {
            throw new CommandException(MESSAGE_MEMBERS_DONT_EXIST);
        }

        Set<Skill> newSkills = model.getNewSkills(skillSet);

        List<CourseMate> courseMateList = new ArrayList<>();
        int index = 0;
        for (List<CourseMate> courseMateAddList: courseMateChoicesList) {
            //If there are more than 1 matching name
            if (courseMateAddList.size() > 1) {
                return new SimilarNameCommand(queryableCourseMateList.get(index)).execute(model);
            }
            courseMateList.add(courseMateAddList.get(0));
            index += 1;
        }

        Group toAdd = new Group(groupName, courseMateList, skillSet, telegramChat);
        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        model.addGroup(toAdd);

        return new CommandResult(Messages.messageNewSkill(newSkills)
                + String.format(MESSAGE_GROUP_CREATED, groupName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CreateGroupCommand)) {
            return false;
        }

        CreateGroupCommand otherCreateGroupCommand = (CreateGroupCommand) other;
        return otherCreateGroupCommand.groupName.equals(groupName)
                && otherCreateGroupCommand.queryableCourseMateSet.equals(queryableCourseMateSet);
    }
}
