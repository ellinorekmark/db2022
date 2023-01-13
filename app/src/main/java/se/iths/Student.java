package se.iths;

import java.util.ArrayList;

public class Student {
    final private int studentId;
    String firstName;
    String lastName;
    ArrayList<School> schools = new ArrayList<>();
    ArrayList<String> hobbies = new ArrayList<>();
    ArrayList<PhoneNumber> numbers = new ArrayList<>();

    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSchool(School school){
        schools.add(school);
    }
    public void addHobby(String hobby){
        hobbies.add(hobby);
    }
    public void addPhoneNumber(PhoneNumber number){
        numbers.add(number);
    }
}
