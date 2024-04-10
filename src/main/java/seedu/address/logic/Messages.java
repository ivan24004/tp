package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.skill.Skill;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_COURSE_MATE_NAME = "The courseMate name or index provided is not found!";
    public static final String MESSAGE_INVALID_COURSE_MATE_DISPLAYED_INDEX = "The courseMate index provided is invalid";
    public static final String MESSAGE_COURSE_MATES_LISTED_OVERVIEW = "%1$d course mate(s) listed!";
    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d group(s) listed!";
    public static final String MESSAGE_INVALID_GROUP_NAME = "The group name provided is not found!";
    public static final String MESSAGE_MEMBERS_DONT_EXIST = "Some of the specified members could not be found.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_SIMILAR_COURSE_MATE_NAME = "There are %1$d course mates with "
            + "name containing %2$s \n"
            + "You can retry by giving the course mate's complete name or use the index of the contact \n"
            + "example: “delete #1” to delete the 1st contact in the current list";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Creates a warning message for newly added skills that are not in the database.
     * @param newSkills - The set of skills that are new to the courseMate list.
     * @return A String containing the warning message.
     */
    public static String messageNewSkill(Set<Skill> newSkills) {
        if (newSkills.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("WARNING: New skills detected. Please verify for accuracy to avoid any unintended actions: ");
        int size = newSkills.size();
        int count = 0;
        for (Skill skill : newSkills) {
            sb.append(skill.toString());
            count++;
            if (count < size) {
                sb.append(", ");
            } else {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
