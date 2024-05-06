package student_application_project.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_application_project.model.Admin;
import student_application_project.model.Faculties;
import student_application_project.model.InstituteAdmin;
import student_application_project.repository.FacultyRepo;
import student_application_project.repository.InstituteAdminRepo;
import student_application_project.repository.SuperAdminRepo;
import student_application_project.repository.TArepo;

@RestController
public class AdminController {
    @Autowired
    private InstituteAdminRepo instituteAdminRepo;

    @Autowired
    private SuperAdminRepo superAdminRepo;

    @Autowired
    private TArepo tArepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @PostMapping("/addFacultyByAdmin")
    public ResponseEntity<?> addFacultyByAdmin(@RequestBody Faculties faculties) {
        try {
            facultyRepo.save(faculties);
            return new ResponseEntity<>(faculties, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addInstituteAdmin")
    public ResponseEntity<?> addInstituteAdmin(@RequestBody InstituteAdmin instituteAdmin) {
        try {
            instituteAdminRepo.save(instituteAdmin);
            return new ResponseEntity<>(instituteAdmin, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
