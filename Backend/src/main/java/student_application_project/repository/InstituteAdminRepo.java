package student_application_project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import student_application_project.model.InstituteAdmin;

public interface InstituteAdminRepo extends MongoRepository<InstituteAdmin, String> {
}
