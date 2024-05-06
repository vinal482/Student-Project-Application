package student_application_project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "faculty")
public class Faculties {
//    @Id
//    private int id;

    private String name;
    @Id
    private String email;

    private String Password;

    private List<Course> currentCourseList;
    private List<Course> oldCourseList;
}
