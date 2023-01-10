```mermaid

erDiagram    
    School ||--|{ StudentSchool : accepts
    Student || --|{StudentSchool : enrolls
    Student ||--o{StudentHobby : enjoys
    Student || -- o{Phone : has
    PhoneType ||--|{ PhoneType : is
    Hobby ||--||StudentHobby : involves
    Student ||--|{Phone : contact
    Student }o--o{ StudentGrade : has
    StudentGrade ||--|{ Grade : equals
    
    
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
