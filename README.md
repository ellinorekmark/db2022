```mermaid
    
    School ||--|{ StudentSchool : accepts
    Student ||--|{StudentHobby : has
    StudentHobby ||--|{Hobby : is
    
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

```
