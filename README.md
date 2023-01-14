# Slutuppgift Databaser

### av Ellinor Ekmark



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


CRUDInterface {
CRUDInterface : findAll()
CRUDInterface : findById()
CRUDInterface : update()
CRUDInterface : delete()
}



```


## Normalisering

docker exec -i iths-mysql mysql -uiths -piths < normalisering.sql
