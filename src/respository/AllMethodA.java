package src.respository;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;
import src.model.Course;
import src.service.AllFunction;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static src.model.Course.courses;

class CourseManager implements AllFunction {
    // Initialize Random object
    public static final String FILE_PATH = "D:\\RUPP\\CSTAD-Learning\\homework\\src\\Student.csv";
    @Override
    public  void addCourseFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        Random random = new Random();
        int id = random.nextInt(100000);

        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Number of Instructors: ");
        int instructorCount = Integer.parseInt(scanner.nextLine());
        String[] instructorNames = new String[instructorCount];
        for (int i = 0; i < instructorCount; i++) {
            System.out.print("Enter Instructor " + (i + 1) + " Name: ");
            instructorNames[i] = scanner.nextLine();
        }

        System.out.print("Enter Number of Requirements: ");
        int requirementCount = Integer.parseInt(scanner.nextLine());
        String[] requirements = new String[requirementCount];
        for (int i = 0; i < requirementCount; i++) {
            System.out.print("Enter Requirement " + (i + 1) + ": ");
            requirements[i] = scanner.nextLine();
        }

//        LocalDate startDate = LocalDate.now().minusYears(random.nextInt(10)); // Generate a random date within the last 10 years
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime startDate = currentDate.atTime(currentTime);
        Course newCourse = new Course(id, title, instructorNames, requirements, startDate);
        courses.add(newCourse);
        writeCourseToFile(FILE_PATH);
        courses.clear();
    }

    public void displayCourses(String filePath) {
        Instant startTime = Instant.now();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Course> courseList = reader.lines()
                    .map(this::apply)
                    .filter(Objects::nonNull)
                    .toList();

            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Time to read data: " + (duration.toMillis() / 1000.0) + " seconds");
            System.out.println("Data read successfully from " + filePath);

            // Display courses in a table
            LocalDateTime now = LocalDateTime.now();
            Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE);
            table.addCell("Course ID");
            table.addCell("Title");
            table.addCell("Instructors");
            table.addCell("Requirements");
            table.addCell("Start Date");
            for (Course course : courseList) {
                table.addCell(String.valueOf(course.getId()));
                table.addCell(course.getTitle());
                table.addCell(String.join(", ", course.getInstructorName()));
                table.addCell(String.join(", ", course.getRequirement()));

                // Format the course's start date to a String in "yyyy-MM-dd HH:mm" format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedNow = now.format(formatter);

                // Add the formatted current date and time to the table
                table.addCell(formattedNow);
            }

            System.out.println(table.render());
            for (Course course : courseList) {
                course.setStartDate(now); // Assuming Course class has a setter for startDate
            }

        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void writeCourseToFile(String filePath) {
        long startTime = System.currentTimeMillis();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            for (Course course : courses) {
                // Assuming course.getStartDate() returns a LocalDateTime object
                // Convert LocalDateTime to String with hours, minutes, and seconds
                String formattedStartDate = course.getStartDate().toString();
                writer.write(course.getId() + "/" +
                        course.getTitle() + "/" +
                        String.join(",", course.getInstructorName()) + "/" +
                        String.join(",", course.getRequirement()) + "/" +
                        formattedStartDate + System.lineSeparator());
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time to write data: " + totalTime / 1000.00 + " seconds");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public void clearDataStore() {
        courses.clear();
        try {
            Path filePath = Paths.get(FILE_PATH);
            if (Files.exists(filePath)) {
                Files.deleteIfExists(filePath);
                System.out.println("Data store cleared successfully. Data file has been deleted.");
            } else {
                System.out.println("Data file does not exist. Nothing to delete.");
            }
        } catch (IOException e) {
            System.out.println("Error deleting data file: " + e.getMessage());
        }
    }
    @Override
    public Course apply(String line) {
        String[] parts = line.split("/");
        if (parts.length >= 5) {
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String[] instructorNames = parts[2].split(",");
            String[] requirements = parts[3].split(",");
            LocalDateTime startDate;
            try {
                startDate = LocalDateTime.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (DateTimeException e) {
                System.out.println("Invalid date format in file. Setting date to current date.");
                startDate = LocalDateTime.now();
            }
            return new Course(id, title, instructorNames, requirements, startDate);
        } else {
            return null;
        }
    }
    public List<Course> readCoursesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(this::apply)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public void findCourse() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the course ID to find: ");
        int courseId = scanner.nextInt(); // Read the next integer input
        scanner.nextLine(); // Consume the newline left-over

        List<Course> courseList = readCoursesFromFile(FILE_PATH);

        Optional<Course> foundCourse = courseList.stream()
                .filter(c -> c.getId() == courseId)
                .findFirst();

        if (foundCourse.isPresent()) {
            // Create a table to display the found course
            var table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE);
            table.addCell("Course ID");
            table.addCell("Title");
            table.addCell("Instructors");
            table.addCell("Requirements");
            table.addCell("Start Date");

            // Add the found course to the table
            Course course = foundCourse.get();
            table.addCell(String.valueOf(course.getId()));
            table.addCell(course.getTitle());
            table.addCell(String.join(", ", course.getInstructorName()));
            table.addCell(String.join(", ", course.getRequirement()));
            table.addCell(course.getStartDate().toString()); // Assuming getStartDate() returns LocalDateTime

            // Display the table
            System.out.println("Found course:");
            System.out.println(table.render());
        } else {
            System.out.println("No course found with ID: " + courseId);
        }
    }
    @Override
    public void deleteCourse() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the course ID to delete: ");
        int courseIdToDelete = scanner.nextInt(); // Read the next integer input
        scanner.nextLine(); // Consume the newline left-over

        List<Course> courseList = new ArrayList<>(readCoursesFromFile(FILE_PATH)); // Create a mutable copy

        boolean courseDeleted = false;
        Iterator<Course> iterator = courseList.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getId() == courseIdToDelete) {
                iterator.remove(); // Remove the course from the list
                courseDeleted = true;
                break;
            }
        }

        if (courseDeleted) {
            System.out.println("Course with ID " + courseIdToDelete + " deleted successfully.");

            // Update the file with the modified list of courses
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Course course : courseList) {
                    String formattedStartDate = course.getStartDate().toString();
                    writer.write(course.getId() + "/" +
                            course.getTitle() + "/" +
                            String.join(",", course.getInstructorName()) + "/" +
                            String.join(",", course.getRequirement()) + "/" +
                            formattedStartDate + System.lineSeparator());
                }
                System.out.println("Course list updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
            System.out.println("Courses after deletion:");
            displayCourses(FILE_PATH);
        } else {
            System.out.println("No course found with ID: " + courseIdToDelete);
        }
    }
    @Override
    public void findCourseByTitle(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the course to search: ");
        String title = scanner.nextLine().trim();
        scanner.close();

        List<Course> matchingCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                matchingCourses.add(course);
            }
        }
        if (!matchingCourses.isEmpty()) {
            // Display matching courses in a table
            Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE);
            table.addCell("Course ID");
            table.addCell("Title");
            table.addCell("Instructors");
            table.addCell("Requirements");
            table.addCell("Start Date");
            for (Course course : matchingCourses) {
                table.addCell(String.valueOf(course.getId()));
                table.addCell(course.getTitle());
                table.addCell(String.join(", ", course.getInstructorName()));
                table.addCell(String.join(", ", course.getRequirement()));
                table.addCell(course.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            System.out.println("Matching courses:");
            System.out.println(table.render());
        } else {
            System.out.println("No courses found with the title: " + title);
        }
    }
}
