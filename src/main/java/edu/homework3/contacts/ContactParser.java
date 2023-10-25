package edu.homework3.contacts;

import java.util.Arrays;
import java.util.Comparator;

public class ContactParser {
    private ContactParser() {}

    public static Contact[] parseContacts(String[] contactsList) {
        return parseContacts(contactsList, SortType.ASC);
    }

    public static Contact[] parseContacts(String[] contactsList, SortType sortType) {
        if (contactsList == null) {
            return new Contact[0];
        }

        int n = contactsList.length;
        Contact[] contacts = new Contact[n];

        for (int i = 0; i < n; i++) {
            contacts[i] = getContactFromString(contactsList[i]);
        }

        if (sortType == SortType.ASC) {
            Arrays.sort(contacts);
        }

        if (sortType == SortType.DESC) {
            Arrays.sort(contacts, new DescContactsComparator());
        }

        return contacts;
    }

    private static Contact getContactFromString(String contact) throws IllegalArgumentException {
        if (contact == null || contact.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] nameAndSurname = contact.strip().split(" ");
        return switch (nameAndSurname.length) {
            case 1 -> new Contact(nameAndSurname[0]);
            case 2 -> new Contact(nameAndSurname[0], nameAndSurname[1]);
            default -> throw new IllegalArgumentException();
        };
    }

    private static class DescContactsComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact contact1, Contact contact2) {
            return (-1) * contact1.compareTo(contact2);
        }
    }
}
