package student_application_project.controller;

import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.SelectionOperators;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import student_application_project.model.*;
import student_application_project.repository.*;

import javax.management.Notification;
import javax.swing.text.html.Option;

@RestController
public class CourseController {
    @Autowired
    private CourseRepo repo;

    @Autowired
    private TArepo taRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private MaterialRepo materialRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    @Autowired
    private VideoRepo videoRepo;

    @Autowired
    private StudentApplicationProjectRepo studentRepo;

    @Autowired
    private AssignmentSubmissionRepo assignmentSubmissionRepo;

    @Autowired
    private StudentAssignmentSubmissionRepo studentAssignmentSubmissionRepo;

    @Autowired
    private SubmissionFileRepo submissionFilesRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = repo.findAll();
        if (courses.isEmpty()) {
            return new ResponseEntity<>("No Courses available", null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Course>>(courses, null, HttpStatus.OK);
        }
    }

    @GetMapping("/getCourse")
    public ResponseEntity<?> getCourseById(@RequestParam String Id) {
        try {
            Optional<Course> course = repo.findById(Id);
            if(course.isPresent()) {
                Course course1 = course.get();
                return new ResponseEntity<>(course1, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Course is not present", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editCourseDetails")
    public ResponseEntity<?> editCourseDetails(@RequestBody Course course) {
        try {
            Optional<Course> courseOptional = repo.findById(course.getId());
            if(courseOptional.isPresent()) {
                Course course1 = courseOptional.get();
                course1.setName(course.getName());
                course1.setDescription(course.getDescription());
                course1.setCredits(course.getCredits());
                repo.save(course1);
                Optional<Faculties> faculties = facultyRepo.findById(course1.getFacultyEmail());
                if(faculties.isPresent()) {
                    Faculties faculties1 = faculties.get();
                    List<Course> courses = faculties1.getCurrentCourseList();
                    for(int i = 0; i<courses.size(); i++) {
                        if(Objects.equals(courses.get(i).getId(), course1.getId())) {
                            courses.remove(i);
                            break;
                        }
                    }
                    courses.add(course1);
                    faculties1.setCurrentCourseList(courses);
                    facultyRepo.save(faculties1);
                }
                return new ResponseEntity<>(course1, null,HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not found!", null, HttpStatus.OK);
            }
        } catch ( Exception e ) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Course addCourse(@RequestBody Course course) {
        try {
//            course.setNumOfStudents(course.getNumOfStudents() + 1);
            String courseId = createId(course.getFacultyEmail(), course.getName());
            course.setId(courseId);
            repo.save(course);
            return course;
        } catch (Exception e) {
            return course;
        }

    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getSingleCourse(@PathVariable("id") String id) {
        try {
            Optional<Course> courseOptional = repo.findById(id);
            if (courseOptional.isPresent()) {
                return new ResponseEntity<>(courseOptional.get(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Course found with id: " + id, null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/addTAToCourse")
//    public ResponseEntity<?> addTAToCourse(@RequestBody TA ta, @RequestParam String id) {
//        try {
//
//            Optional<Course> courseOptional = repo.findById(id);
//            if(courseOptional.isPresent()){
//                if(ta.getCurrentCourses()==null) {
//                    List<String> currentCoursesTA = new ArrayList<String>();
//                    currentCoursesTA.add(id);
//                    ta.setCurrentCourses(currentCoursesTA);
//                } else {
//                    ta.getCurrentCourses().add(id);
//                    ta.setCurrentCourses(ta.getCurrentCourses());
//                }
//                taRepo.save(ta);
//                Course course = courseOptional.get();
//                List<TA> listOfTAs = new ArrayList<TA>();
//                listOfTAs.add(ta);
//                course.setTas(listOfTAs);
//                repo.save(course);
//                return new ResponseEntity<>(course, null, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("TA not found!", null, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/addCourseAndSetToFaculty")
    public ResponseEntity<?> addCourseAndSetToFaculty(@RequestBody Course course) {
        try {
            course = addCourse(course);
            Optional<Faculties> faculties = facultyRepo.findById(course.getFacultyEmail());
            if(faculties.isPresent()) {
                Faculties faculty = faculties.get();
                List<Course> newCourses = new ArrayList<>();
                if(faculty.getCurrentCourseList() == null) {
                    newCourses.add(course);
                } else {
                    newCourses = faculty.getCurrentCourseList();
                    newCourses.add(course);
                }
                faculty.setCurrentCourseList(newCourses);
                facultyRepo.save(faculty);
                Notifications notifications = new Notifications();
                notifications.setCourseId(course.getId());
                notifications.setCourseName(course.getName());
                notifications.setTitle("Course Creation");
                notifications.setMessage("You have created the course " + course.getName());
                notifications.setReceiverEmail(course.getFacultyEmail());
                notifications.setReceiverName(faculty.getName());
                notifications.setEmail(course.getFacultyEmail());
                notifications.setType(1);
                notificationRepo.save(notifications);
                return new ResponseEntity<>("Successful", null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Fail", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addTopic")
    public ResponseEntity<?> addTopic(@RequestBody Topic topic) {
        try {
            Optional<Course> courseOptional = repo.findById(topic.getCourseId());
            if(courseOptional.isPresent()) {
                Course course = courseOptional.get();
                String topicId = createId(course.getId(), topic.getName());
                topic.setId(topicId);
                List<Topic>topicsList = new ArrayList<>();
                if(course.getTopics() != null) {
                    topicsList = course.getTopics();
                }
                topicsList.add(topic);
                course.setTopics(topicsList);
                topicRepo.save(topic);
                repo.save(course);
                Notifications notifications = new Notifications();
                notifications.setCourseId(course.getId());
                notifications.setCourseName(course.getName());
                notifications.setTitle("Topic Creation");
                notifications.setMessage("You have created the topic " + topic.getName());
                notifications.setReceiverEmail(course.getFacultyEmail());
                notifications.setReceiverName(course.getInstructor());
                notifications.setEmail(course.getFacultyEmail());
                notifications.setType(1);
                notificationRepo.save(notifications);
                for(int i = 0; i<course.getStudents().size(); i++) {
                    Notifications notifications1 = new Notifications();
                    notifications1.setCourseId(course.getId());
                    notifications1.setCourseName(course.getName());
                    notifications1.setTitle("Topic Creation");
                    notifications1.setMessage("A new topic " + topic.getName() + " has been added to the course " + course.getName());
                    notifications1.setReceiverEmail(course.getStudents().get(i).getId());
                    notifications1.setReceiverName(course.getStudents().get(i).getName());
                    notifications1.setEmail(course.getStudents().get(i).getId());
                    notifications1.setType(1);
                    notificationRepo.save(notifications1);
                }
                return new ResponseEntity<>(topic, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not added!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopics")
    public ResponseEntity<?> getTopics(@RequestParam String Id) {
        try {
            Optional<Course> courses = repo.findById(Id);
            if(courses.isPresent()) {
                Course course = courses.get();
                return new ResponseEntity<>(course.getTopics(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopic")
    public ResponseEntity<?> getTopic(@RequestParam String Id) {
        try {
            Optional<Topic> topic = topicRepo.findById(Id);
            if(topic.isPresent()) {
                Topic topic1 = topic.get();
                return new ResponseEntity<>(topic1, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addVideo")
    public ResponseEntity<?> addVideo(@RequestBody Video video) {
        try {
            Optional<Topic> topicOptional = topicRepo.findById(video.getTopicId());
            if(topicOptional.isPresent()) {
                Topic topic = topicOptional.get();
                String videoId = createId(topic.getId(), video.getName());
                video.setId(videoId);
                List<Video>videosList = new ArrayList<>();
                if(topic.getVideos() != null) {
                    videosList = topic.getVideos();
                }
                videosList.add(video);
                topic.setVideos(videosList);
                videoRepo.save(video);
                topicRepo.save(topic);
                Notifications notifications = new Notifications();
                notifications.setCourseId(topic.getCourseId());
                notifications.setCourseName(topic.getName());
                notifications.setTitle("Video Creation");
                notifications.setMessage("You have created the video " + video.getName());
                notifications.setReceiverEmail(topic.getCourseId());
                notifications.setReceiverName(topic.getName());
                notifications.setEmail(topic.getCourseId());
                notifications.setType(1);
                notificationRepo.save(notifications);
                Optional<Course> courseOptional = repo.findById(topic.getCourseId());
                if(courseOptional.isPresent()) {
                    Course course = courseOptional.get();
                    for(int i = 0; i<course.getStudents().size(); i++) {
                        Notifications notifications1 = new Notifications();
                        notifications1.setCourseId(topic.getCourseId());
                        notifications1.setCourseName(topic.getName());
                        notifications1.setTitle("Video Creation");
                        notifications1.setMessage("A new video " + video.getName() + " has been added to the topic " + topic.getName());
                        notifications1.setReceiverEmail(course.getStudents().get(i).getId());
                        notifications1.setReceiverName(course.getStudents().get(i).getName());
                        notifications1.setEmail(course.getStudents().get(i).getId());
                        notifications1.setType(1);
                        notificationRepo.save(notifications1);
                    }
                }
                return new ResponseEntity<>(video, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not added!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getVideos")
    public ResponseEntity<?> getVideos(@RequestParam String Id) {
        try {
            Optional<Topic> topics = topicRepo.findById(Id);
            if(topics.isPresent()) {
                Topic topic = topics.get();
                return new ResponseEntity<>(topic.getVideos(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMaterial")
    public ResponseEntity<?> addMaterial(@RequestBody Material material) {
        try {
            Optional<Topic> topicOptional = topicRepo.findById(material.getTopicId());
            if(topicOptional.isPresent()) {
                Topic topic = topicOptional.get();
                String materialId = createId(topic.getId(), material.getName());
                material.setId(materialId);
                List<Material>materialsList = new ArrayList<>();
                if(topic.getMaterials() != null) {
                    materialsList = topic.getMaterials();
                }
                materialsList.add(material);
                topic.setMaterials(materialsList);
                materialRepo.save(material);
                topicRepo.save(topic);
                Optional<Course> courseOptional = repo.findById(topic.getCourseId());
                if(courseOptional.isPresent()) {
                    Course course = courseOptional.get();
                    for(int i = 0; i<course.getStudents().size(); i++) {
                        Notifications notifications1 = new Notifications();
                        notifications1.setCourseId(topic.getCourseId());
                        notifications1.setCourseName(topic.getName());
                        notifications1.setTitle("Material Creation");
                        notifications1.setMessage("A new material " + material.getName() + " has been added to the topic " + topic.getName());
                        notifications1.setReceiverEmail(course.getStudents().get(i).getId());
                        notifications1.setReceiverName(course.getStudents().get(i).getName());
                        notifications1.setEmail(course.getStudents().get(i).getId());
                        notifications1.setType(1);
                        notificationRepo.save(notifications1);
                    }
                }
                return new ResponseEntity<>(material, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not added!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMaterials")
    public ResponseEntity<?> getMaterials(@RequestParam String Id) {
        try {
            Optional<Topic> topics = topicRepo.findById(Id);
            if(topics.isPresent()) {
                Topic topic = topics.get();
                return new ResponseEntity<>(topic.getMaterials(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String createId(String id, String name) {
        LocalDate today = LocalDate.now(); // Get current date
        LocalTime time = LocalTime.now(); // Get current time

        String date = today.format(DateTimeFormatter.ofPattern("ddMM"));
        String timeStr = time.format(DateTimeFormatter.ofPattern("hhmmss"));
        return id + date + timeStr + name.substring(name.length() - 3);
    }

    @PostMapping("/addAssignment")
    public ResponseEntity<?> addAssignment(@RequestBody Assignment assignment) {
        try {
            Optional<Topic> topicOptional = topicRepo.findById(assignment.getTopicId());
            System.out.println("Topic Optional: " + assignment.getTopicId());
            if(topicOptional.isPresent()) {
                AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
                Topic topic = topicOptional.get();
                String assignmentId = createId(topic.getId(), assignment.getName());
                assignment.setId(assignmentId);
                assignmentSubmission.setAssignmentId(assignmentId);
                assignmentSubmissionRepo.save(assignmentSubmission);
                assignmentRepo.save(assignment);
                assignment.setId(assignmentId);
                List<Assignment>assignmentsList = new ArrayList<>();
                if(topic.getAssignments() != null) {
                    assignmentsList = topic.getAssignments();
                }
                assignmentsList.add(assignment);
                topic.setAssignments(assignmentsList);
                topicRepo.save(topic);
                Optional<Course> courseOptional = repo.findById(topic.getCourseId());
                if(courseOptional.isPresent()) {
                    Course course = courseOptional.get();
                    Notifications notifications = new Notifications();
                    notifications.setCourseId(topic.getCourseId());
                    notifications.setCourseName(topic.getName());
                    notifications.setTitle("Assignment Creation");
                    notifications.setMessage("You have created the assignment " + assignment.getName());
                    notifications.setReceiverEmail(course.getFacultyEmail());
//                    notifications.setReceiverName(topic.getName());
                    notifications.setEmail(course.getFacultyEmail());
                    notifications.setType(1);
                    notificationRepo.save(notifications);
                    for(int i = 0; i<course.getStudents().size(); i++) {
                        Notifications notifications1 = new Notifications();
                        notifications1.setCourseId(topic.getCourseId());
                        notifications1.setCourseName(topic.getName());
                        notifications1.setTitle("Assignment Creation");
                        notifications1.setMessage("A new assignment " + assignment.getName() + " has been added to the topic " + topic.getName());
                        notifications1.setReceiverEmail(course.getStudents().get(i).getId());
                        notifications1.setReceiverName(course.getStudents().get(i).getName());
                        notifications1.setEmail(course.getStudents().get(i).getId());
                        notifications1.setType(1);
                        notificationRepo.save(notifications1);
                    }
                }
                return new ResponseEntity<>(assignment, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not Present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/enrollCourse")
    public ResponseEntity<?> enrollCourse(@RequestParam String courseId, @RequestParam String Id) {
        try {
            Optional<Students> studentsOptional = studentRepo.findById(Id);
            Optional<Course> courseOptional = repo.findById(courseId);
            if(studentsOptional.isPresent() && courseOptional.isPresent()) {
                Students student = studentsOptional.get();
                Course course = courseOptional.get();
                course.setNumOfStudents(course.getNumOfStudents() + 1);
                StudentCustom studentCustom = new StudentCustom();
                studentCustom.setId(Id);
                studentCustom.setName(student.getFirstName() + " " + student.getLastName());
                List<StudentCustom> studentCustomList = new ArrayList<>();
                if(course.getStudents() != null) {
                    studentCustomList = course.getStudents();
                }
                Notifications notifications = new Notifications();
                notifications.setCourseId(courseId);
                notifications.setCourseName(course.getName());
                notifications.setTitle("Enrollment");
                notifications.setMessage("You have been enrolled in the course " + course.getName());
//                notifications.setNotification("You have been enrolled in the course " + course.getName());
                notifications.setReceiverEmail(student.getEmail());
                notifications.setReceiverName(student.getFirstName() + " " + student.getLastName());
                notifications.setEmail(student.getEmail());
                notifications.setType(1);
                notificationRepo.save(notifications);
                studentCustomList.add(studentCustom);
                course.setStudents(studentCustomList);
                repo.save(course);
                List<Course> courses = new ArrayList<>();
                if(student.getCourses() != null)
                    courses = student.getCourses();
                courses.add(course);
                student.setCourses(courses);
                studentRepo.save(student);
                return new ResponseEntity<>("Successful", null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Fail", null, HttpStatus.OK);
            }
        } catch (Exception e ) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAssignments")
    public ResponseEntity<?> getAssignments(@RequestParam String Id) {
        try {
            Optional<Topic> topics = topicRepo.findById(Id);
            if(topics.isPresent()) {
                Topic topic = topics.get();
                return new ResponseEntity<>(topic.getAssignments(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not present!", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStudent")
    public ResponseEntity<?> getStudent(@RequestParam String Id) {
        try {
            Optional<Students> studentsOptional = studentRepo.findById(Id);
            if(studentsOptional.isPresent()) {
                return new ResponseEntity<>(studentsOptional.get(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not found", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCoursesForTA")
    public ResponseEntity<?> getCoursesForTA(@RequestParam String Id) {
        try {
            Optional<TA> taOptional = taRepo.findById(Id);
            if(taOptional.isPresent()) {
                TA ta = taOptional.get();
                List<Course> courses = new ArrayList<>();
                if(ta.getCurrentCourses() != null) {
                    courses = ta.getCurrentCourses();
                }
                return new ResponseEntity<>(courses, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not found", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Assignment submission by student
    @PostMapping("/submitAssignment")
    public ResponseEntity<?> submitAssignment(@RequestBody SubmissionFiles submissionFiles, @RequestParam String assignmentId, @RequestParam String studentId, @RequestParam String assignmentName) {
        try {
            Optional<AssignmentSubmission> assignmentOptional = assignmentSubmissionRepo.findById(assignmentId);
            if(assignmentOptional.isPresent()) {
                AssignmentSubmission assignment = assignmentOptional.get();
                List<StudentAssignmentSubmission> studentAssignmentSubmissions = new ArrayList<>();
                StudentAssignmentSubmission studentAssignmentSubmission1 = new StudentAssignmentSubmission();
                if(assignment.getStudentAssignmentSubmission() != null && !assignment.getStudentAssignmentSubmission().isEmpty()) {
                    System.out.println("StudentAssignmentSubmission is not null");
                    studentAssignmentSubmissions = assignment.getStudentAssignmentSubmission();
                    boolean x = false;
                    for(StudentAssignmentSubmission studentAssignmentSubmission : studentAssignmentSubmissions) {
                        if (Objects.equals(studentAssignmentSubmission.getStudentId(), studentId)) {
                            x = true;
                            break;
                        }
                    }
                    if(!x) {
                        studentAssignmentSubmission1.setAssignmentId(assignmentId);
                        studentAssignmentSubmission1.setStudentId(studentId);
                        studentAssignmentSubmission1.setStudentName("Student");
                        studentAssignmentSubmission1.setSubmissionFiles(new ArrayList<>());
                        studentAssignmentSubmissions.add(studentAssignmentSubmission1);
                    }
                } else {
                    studentAssignmentSubmission1.setAssignmentId(assignmentId);
                    studentAssignmentSubmission1.setStudentId(studentId);
                    studentAssignmentSubmission1.setStudentName("Student");
                    studentAssignmentSubmission1.setSubmissionFiles(new ArrayList<>());
                    studentAssignmentSubmissions.add(studentAssignmentSubmission1);
                }
                System.out.println("StudentAssignmentSubmission is null");
//                    studentAssignmentSubmissions = new ArrayList<>();
//                studentAssignmentSubmissions.add(studentAssignmentSubmission1);
//                studentAssignmentSubmissions.add(submissionFiles);
//                assignment.setSubmissionFiles(studentAssignmentSubmissions);
                for (StudentAssignmentSubmission studentAssignmentSubmission : studentAssignmentSubmissions) {
                    if (Objects.equals(studentAssignmentSubmission.getStudentId(), studentId)) {
                        List<SubmissionFiles> submissionFilesList = new ArrayList<>();
                        if(studentAssignmentSubmission.getSubmissionFiles() != null) {
                            submissionFilesList = studentAssignmentSubmission.getSubmissionFiles();
                        }
                        submissionFilesList.add(submissionFiles);
                        studentAssignmentSubmission.setSubmissionFiles(submissionFilesList);
                        studentAssignmentSubmissionRepo.save(studentAssignmentSubmission);
                        break;
                    }
                }
                assignment.setStudentAssignmentSubmission(studentAssignmentSubmissions);
                assignmentSubmissionRepo.save(assignment);

                Notifications notifications = new Notifications();
//                notifications.setCourseId(assignmentId);
//                notifications.setCourseName(assignmentId);
                notifications.setTitle("Assignment Submission");
                notifications.setMessage("You have submitted the assignment " + assignmentName);
                notifications.setReceiverEmail(studentId);
                notifications.setReceiverName("Student");
                notifications.setEmail(studentId);
                notifications.setType(1);
//                notifications.setEmail(studentId);
                notificationRepo.save(notifications);
                return new ResponseEntity<>(studentAssignmentSubmissions, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data not found", null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSubmissionsByAssignmentId")
    public ResponseEntity<?> getSubmissionsByAssignmentId(@RequestParam String assignmentId) {
        try {
            List<StudentAssignmentSubmission> assignmentOptional = studentAssignmentSubmissionRepo.findByAssignmentId(assignmentId);
            return new ResponseEntity<>(assignmentOptional, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSubmissionFiles")
    public ResponseEntity<?> getSubmissionFiles(@RequestParam String assignmentId, @RequestParam String studentId) {
        try {
            Optional<StudentAssignmentSubmission> studentAssignmentSubmissionOptional = Optional.ofNullable(studentAssignmentSubmissionRepo.findByAssignmentIdAndStudentId(assignmentId, studentId));
            if(studentAssignmentSubmissionOptional.isPresent()) {
                StudentAssignmentSubmission studentAssignmentSubmission = studentAssignmentSubmissionOptional.get();
                return new ResponseEntity<>(studentAssignmentSubmission.getSubmissionFiles(), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
