# Slutuppgift Databaser

### av Ellinor Ekmark

## Beskrivning

Uppgiften gick ut på att använda en mysql databas i docker, och normalisera data med hjälp av ett script i databasen, samt skapa en javaapplikation som kan kommunicera med databasen. Javaapplikationen ska ha ORM (Object Relational Mapping) och kunna skapa objekt från databasen, samt ha grundläggande CRUD (Create, Read, Update, Delete) funktion. Som en bonus gjorde jag ett försök att implementera ett DAO pattern.

## Gör detta först

Skapa en docker med en mysql databas. Användarnamn iths och lösenord iths. 
i mysql måste du se till att iths har rätt behörigheter med 
Grant file on *.* for 'iths'@'%';

filen denormalized-data.csv måste ligga i docker maskinen på /var/lib/mysql-files/denormalized-data.csv

sedan kan normaliseringen köras genom att skriva i bash

docker exec -i iths-mysql mysql -uiths -piths < normalisering.sql

Nu borde databasen ha normaliserats.
Detta behöver göras innan vi kör javaapplikationen.


###Att köra javaapplikationen
skriv i bash: gradle run

observera att det finns vissa metoder i applikationen som inte demonstreras fullt ut genom att endast köra programmet. 

## E-R Diagram


```mermaid

erDiagram    
    Hobby ||--|{ StudentHobby : involves
    PhoneType ||--o{ Phone : is
    Student ||--o{StudentHobby : enjoys
    Student || --|{StudentSchool : enrolls
    Student || -- o{Phone : has
    School ||--|{ StudentSchool : accepts
    Student ||--o{ StudentGrade : has
    School ||--|{ StudentGrade : grades
    Grade ||--|{ StudentGrade : equals
    
    
    StudentSchool {
        int StudentId
        int SchoolId
    }
    
    Student {
        int StudentId
        string FirstName
        string LastName
    }
    
    School {
        int SchoolId
        string Name
        string City
    }

    StudentHobby {
        int StudentId
        int HobbyId
    }

    Hobby {
        int HobbyId
        string Hobby
    }

    Phone {
        int PhoneId
        int StudentId
	int TypeId
        string Number
    }

    PhoneType {
	int TypeId
	String Type
    }

    Grade {
	int GradeId
	String GradeName
    }

    StudentGrade {
	int StudentId
	int GradeId
	int SchoolId
    }

```
## UML class diagram
please note I have not included the standard setters and getters.

```mermaid
classDiagram

CRUDInterface <|-- StudentDAO
CRUDInterface <|-- SchoolDAO
CRUDInterface <|-- PhoneNumberDAO
Student <-- StudentDAO
School <-- SchoolDAO
PhoneNumber <-- PhoneNumberDAO
StudentDAO <-- PhoneNumberDAO
StudentDAO <-- SchoolDAO


class CRUDInterface {
CRUDInterface : findAll()
CRUDInterface : findById()
CRUDInterface : update()
CRUDInterface : delete()
}
class StudentDAO {
StudentDAO : SchoolDAO
StudentDAO : PhoneNumberDAO
StudentDAO : create()
StudentDAO : checkIfStudentExists()


}
class SchoolDAO {
SchoolDAO : findByStudentId()

}
class PhoneNumberDAO {
PhoneNumberDAO : findByStudentId()
}
class Student {
Student : int id
Student : String firstName
Student : String lastName
Student : Collection<String> hobbies
Student : Collection<PhoneNumber> numbers
Student : Collection<School> schools
Student : addHobby()
Student : addNumber()
Student : addSchool()
}

class School {
School : String school
School : String city
School : String grade
}

class PhoneNumber {
PhoneNumber : int phoneId
PhoneNumber : int studentId
PhoneNumber : String type
PhoneNumber : String number
}


```


## Normalisering

docker exec -i iths-mysql mysql -uiths -piths < normalisering.sql
