package edu.homework3.contacts;

import java.util.Objects;

public class Contact implements Comparable<Contact> {
    private final String name;

    private final String surname;

    public Contact(String name, String surname) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.surname = surname;
    }

    public Contact(String name) {
        this(name, null);
    }

    @Override
    public String toString() {
        return this.name + (this.surname == null ? "" : " " + this.surname);
    }

    @Override
    public boolean equals(Object other) {
        return ((other instanceof Contact)
            && ((Contact) other).name.equals(this.name)
            && (((Contact) other).surname == null && this.surname == null
                    || ((Contact) other).surname.equals(this.surname)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public int compareTo(Contact other) {
        if (other == null) {
            return 1;
        }

        String thisCompare = this.surname == null ? this.name : this.surname;
        String otherCompare = other.surname == null ? other.name : other.surname;

        return thisCompare.compareTo(otherCompare);
    }
}
