package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class SkillTest {
    @Test
    public void equals() {
        Skill skill1 = new Skill("TEST");
        Skill skill2 = new Skill("test");
        Skill skill3 = new Skill("diFf");

        assertTrue(skill1.equals(skill1));
        assertFalse(skill1.equals(null));
        assertTrue(skill1.equals(skill2));
        assertFalse(skill1.equals(skill3));
        assertFalse(skill3.equals(skill1));
    }

    @Test
    public void addSkillToSet() {
        Skill skill1 = new Skill("TEST");
        Skill skill2 = new Skill("test");
        Skill skill3 = new Skill("diFf");

        Set<Skill> s = new HashSet<>();

        assertTrue(s.isEmpty());

        s.add(skill1);
        assertTrue(s.size() == 1);
        assertTrue(skill1.equals(skill2) && skill2.equals(skill1));
        boolean check = false;
        for (Skill e : s) {
            if (skill2.equals(e)) {
                check = true;
            }
        }
        assertTrue(check);
        assertTrue(s.contains(skill2));
        s.add(skill2);
        assertTrue(s.size() == 1);

        s.add(skill3);
        assertTrue(s.size() == 2);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skill(null));
    }
}
