package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ConfirmMemberCommand;
import seedu.address.model.coursemate.Name;
import seedu.address.model.coursemate.QueryableCourseMate;

public class ConfirmMemberCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmMemberCommand.MESSAGE_USAGE);

    private ConfirmMemberCommandParser parser = new ConfirmMemberCommandParser();

    @Test
    public void parse_invalidArgs_returnsParseException() {
        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing coursemates
        assertParseFailure(parser, "Group 1", MESSAGE_INVALID_FORMAT);

        // missing coursemate name
        assertParseFailure(parser, "Group 1 -cm ", MESSAGE_INVALID_FORMAT);

        // missing prefix
        assertParseFailure(parser, "Group 1 Bob", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseValidArgs_returnsConfirmMemberCommand() {
        Name groupName1 = new Name("group 1");
        Set<QueryableCourseMate> courseMates1 = new HashSet<>(List.of(
                new QueryableCourseMate(new Name("Bob"))));

        ConfirmMemberCommand targetCommand = new ConfirmMemberCommand(groupName1, courseMates1);
        assertParseSuccess(parser, "group 1 -cm Bob", targetCommand);

        Name groupName2 = new Name("group 2");
        Set<QueryableCourseMate> courseMates2 = new HashSet<>(List.of(
                new QueryableCourseMate(new Name("Bob")),
                new QueryableCourseMate(new Name("Alice"))));
        assertParseSuccess(parser, "group 2 -cm Bob -cm Alice",
                new ConfirmMemberCommand(groupName2, courseMates2));
    }
}
