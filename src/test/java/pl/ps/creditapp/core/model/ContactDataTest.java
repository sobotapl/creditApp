package pl.ps.creditapp.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDataTest {

    @Test
    @DisplayName("should set Optional.empty correspondence address, when home address is the same")
    public void test1(){
        //given & when
        ContactData contactData = ContactData.Builder
                .create()
                .withCorrespondenceAddress(new Address("Wrocławska", "Wrocław", "50-500","Dolnyslask","24/5"))
                .withHomeAddress(new Address("Wrocławska", "Wrocław", "50-500","Dolnyslask","24/5"))
                .build();

        //then
        assertTrue(contactData.getCorrespondenceAddress().isEmpty());
    }

    @Test
    @DisplayName("should set Optional.of correspondence address, when home address is NOT the same")
    public void test2(){
        //given
        final Address correspondenceAddress = new Address("Komandorska", "Krakow", "50-500", "Dolnyslask", "24/5");
        final Address homeAddress = new Address("Wrocławska", "Wrocław", "50-500", "Dolnyslask", "24/5");

        //when
        ContactData contactData = ContactData.Builder
                .create()
                .withCorrespondenceAddress(correspondenceAddress)
                .withHomeAddress(homeAddress)
                .build();

        //then
        assertTrue(contactData.getCorrespondenceAddress().isPresent());
        assertEquals(correspondenceAddress,contactData.getCorrespondenceAddress().get());
    }
}