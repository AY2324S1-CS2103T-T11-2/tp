package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void testInit() {
        Person person = new PersonBuilder().build();
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder().build();
        Person p3 = new PersonBuilder().build();
        Person p4 = new PersonBuilder().build();
        Person p5 = new PersonBuilder().build();
        Person p6 = new PersonBuilder().build();
        Person p7 = new PersonBuilder().build();
        Person p8 = new PersonBuilder().build();
        Person p9 = new PersonBuilder().build();
        Person p10 = new PersonBuilder().build();
        Person p11 = new PersonBuilder().build();
        Person p12 = new PersonBuilder().build();
        Person p13 = new PersonBuilder().build();
        Person p14 = new PersonBuilder().build();
        Person p15 = new PersonBuilder().build();
        Person p16 = new PersonBuilder().build();
        Person p17 = new PersonBuilder().build();
        Person p18 = new PersonBuilder().build();
        Person p19 = new PersonBuilder().build();
        Person p20 = new PersonBuilder().build();
        Person p21 = new PersonBuilder().build();
        Person p22 = new PersonBuilder().build();
        Person p23 = new PersonBuilder().build();
        Person p24 = new PersonBuilder().build();
        Person p25 = new PersonBuilder().build();
        Person p26 = new PersonBuilder().build();
        Person p27 = new PersonBuilder().build();
        Person p28 = new PersonBuilder().build();
        Person p29 = new PersonBuilder().build();
        Person p30 = new PersonBuilder().build();
        Person p31 = new PersonBuilder().build();
        Person p32 = new PersonBuilder().build();
        assertNotNull(person);
    }

    @Test
    public void testSamePerson() {
        Person person = new PersonBuilder().build();
        assertTrue(person.equals(person));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
