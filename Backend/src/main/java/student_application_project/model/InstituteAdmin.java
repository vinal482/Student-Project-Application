package student_application_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstituteAdmin {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String dateOfBirth;
    private String gander;
    private String instituteName;
    private String password;
    private int numberOfFaculties;
    private int numberOfTAs;
    private List<Pair<String,String >> faculties = new ArrayList<>();
    private List<Pair<String,String >> tas = new ArrayList<>();
}
