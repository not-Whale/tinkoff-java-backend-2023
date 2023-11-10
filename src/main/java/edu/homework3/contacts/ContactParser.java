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

        if (sortType == SortType.DESC) {
            return Arrays
                .stream(contactsList)
                .map(ContactParser::getContactFromString)
                .sorted(new DescContactsComparator())
                .toArray(Contact[]::new);
        } else {
            return Arrays
                .stream(contactsList)
                .map(ContactParser::getContactFromString)
                .sorted()
                .toList()
                .toArray(Contact[]::new);
        }
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
