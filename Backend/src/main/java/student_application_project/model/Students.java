package student_application_project.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// import jakarta.validation.OverridesAttribute.List;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Column;

import java.util.ArrayList;
import java.util.List;

// import org.hibernate.validator.constraints.Unique;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Students")
public class Students {
//    @Id
//    private String id;
    @NotNull(message = "Name cannot be null")
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNumber;
    @Id
    @Indexed(unique = true)
    private String email;
    @NotNull(message = "LogedIn cannot be null")
    private boolean loggedIn;

    private String dateOfBirth;
    private String gander;
    private String Program;
    private String instituteName;
    private List<Course> courses;
}
