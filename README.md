```mermaid

erDiagram    
    School ||--|{ StudentSchool : accepts
    Student || --|{StudentSchool : enrolls
    Student ||--o{StudentHobby : has
    Student || -- o{Phone : has
    Phone }|--|| PhoneType : is
    Hobby ||--||StudentHobby : involves
    Student ||--|{Phone : contact
    Student }o--o{ Grade : has
    Grade }|--|| GradeType : equals
    
    
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
        int StudentId
        string Type
        string Number
    }

```
