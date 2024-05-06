package student_application_project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import student_application_project.model.SuperAdmin;

public interface SuperAdminRepo extends MongoRepository<SuperAdmin, String> {
}
