package se.iths;

public class School {
    String school;
    String city;
    String grade;
    public School(String school, String city) {
        this.school = school;
        this.city = city;
    }

    public School(String school, String city, String grade) {
        this.school = school;
        this.city = city;
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String toString(){
        return getSchool() + " (Grade: "+ getGrade() + ")";
    }




}


