package student_application_project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import student_application_project.model.StudentDetails;

public interface StudentDetailsRepo extends MongoRepository<StudentDetails, String> {
}
