package student_application_project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import student_application_project.model.Faculties;

public interface FacultyRepo extends MongoRepository<Faculties, String> {

}
