package edu.homework7.person_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockDatabase implements PersonDatabase {
    private final Map<String, Person> idMap = new HashMap<>();

    private final Map<String, List<Person>> nameMap = new HashMap<>();

    private final Map<String, List<Person>> addressMap = new HashMap<>();

    private final Map<String, List<Person>> phoneMap = new HashMap<>();

    private String reverseIdToString(int id) {
        return new StringBuilder(String.valueOf(id)).reverse().toString();
    }

    private String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public int size() {
        return idMap.size();
    }

    @Override
    public synchronized void add(Person person) {
        if (person.name() != null && person.address() != null && person.phoneNumber() != null) {
            // id map add
            idMap.put(reverseIdToString(person.id()), person);

            // name map add
            String nameKey = reverseString(person.name());
            List<Person> nameList = nameMap.getOrDefault(nameKey, new ArrayList<>());
            nameList.add(person);
            nameMap.put(nameKey, nameList);

            // address map add
            String addressKey = reverseString(person.address());
            List<Person> addressList = addressMap.getOrDefault(addressKey, new ArrayList<>());
            addressList.add(person);
            addressMap.put(addressKey, addressList);

            // phone map add
            String phoneKey = reverseString(person.phoneNumber());
            List<Person> phoneList = phoneMap.getOrDefault(phoneKey, new ArrayList<>());
            phoneList.add(person);
            phoneMap.put(phoneKey, phoneList);
        }
    }

    @Override
    public synchronized void delete(int id) {
        Person person = idMap.get(reverseIdToString(id));

        // id map remove
        idMap.remove(reverseIdToString(id));

        // name map remove
        String nameKey = reverseString(person.name());
        List<Person> nameList = nameMap.getOrDefault(nameKey, new ArrayList<>());
        nameList.remove(person);
        nameMap.put(nameKey, nameList);

        // address map remove
        String addressKey = reverseString(person.address());
        List<Person> addressList = addressMap.getOrDefault(addressKey, new ArrayList<>());
        addressList.remove(person);
        addressMap.put(addressKey, addressList);

        // phone map remove
        String phoneKey = reverseString(person.phoneNumber());
        List<Person> phoneList = phoneMap.getOrDefault(phoneKey, new ArrayList<>());
        phoneList.remove(person);
        phoneMap.put(phoneKey, phoneList);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return List.copyOf(nameMap.getOrDefault(reverseString(name), List.of()));
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return List.copyOf(addressMap.getOrDefault(reverseString(address), List.of()));
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return List.copyOf(phoneMap.getOrDefault(reverseString(phone), List.of()));
    }
}
