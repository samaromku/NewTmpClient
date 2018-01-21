package com.example.andrey.newtmpclient.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.andrey.newtmpclient.entities.ContactOnAddress;

public class ContactsManager {
    private List<ContactOnAddress> contactsList;

    public List<ContactOnAddress> getContactsList() {
        return contactsList;
    }

    public void addContact(ContactOnAddress contact) {
        contactsList.add(contact);
    }

    public void removeEmptyPhonesEmails() {
        for (ContactOnAddress c : contactsList) {
            c.getPhones().removeAll(Arrays.asList("", null));
            c.getEmails().removeAll(Arrays.asList("", null));
        }
    }

    public static final ContactsManager Instance = new ContactsManager();

    private ContactsManager() {
        contactsList = new ArrayList<>();
    }


    public void removeAll() {
        if (contactsList.size() > 0) {
            contactsList.clear();
        }
    }
}
