package se.iths;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Student {


    final private int id;
    String firstName;
    String lastName;
    Collection<School> schools = new ArrayList<>() {
    };
    Collection<String> hobbies = new ArrayList<>();
    Collection<PhoneNumber> numbers = new ArrayList<>();

    public Student(int studentId, String firstName, String lastName) {
        this.id = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSchool(School school) {
        schools.add(school);
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public void addPhoneNumber(PhoneNumber number) {
        numbers.add(number);
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<School> getSchools() {
        if (schools.isEmpty()) {
            return null;
        } else {
            return schools;
        }
    }

    public void setSchools(Collection<School> schools) {
        this.schools = schools;
    }

    public Collection<String> getHobbies() {
        if (hobbies.isEmpty()) {
            return null;
        } else {
            return hobbies;
        }
    }

    public void setHobbies(Collection<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Collection<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(Collection<PhoneNumber> numbers) {
        this.numbers = numbers;
    }

    public String toString() {
        return getId() + ": " + getFirstName() + " " + getLastName() + "\nSchool: " + getSchools() + "\nContact: " + getNumbers() + "\nHobbies: " + getHobbies();
    }
}

