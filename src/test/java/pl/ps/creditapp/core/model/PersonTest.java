package pl.ps.creditapp.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    @DisplayName("familyMembers should be sorted by age desc")
    public void test(){
        //given
        final FamilyMember john = new FamilyMember("John", 18);
        final FamilyMember jane = new FamilyMember("Jane", 40);
        final FamilyMember susie = new FamilyMember("Susie", 5);
        List<FamilyMember> familyMembers = Arrays.asList(john,
                jane,
                susie);

        //when
        Person person = NaturalPerson.Builder.create()
                .withFamilyMembers(familyMembers)
                .build();

        //then
        assertNotNull(person.getFamilyMembers());
        assertTrue(person.getFamilyMembers().size() == 3);
        assertEquals(jane,person.getFamilyMembers().get(0));
        assertEquals(john,person.getFamilyMembers().get(1));
        assertEquals(susie,person.getFamilyMembers().get(2));
    }

}