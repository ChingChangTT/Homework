package src.service;

import src.exception.CourseNotFoundException;
import src.model.Course;

import java.io.IOException;

public interface AllFunction {
    void addCourseFromUserInput() throws IOException;

    Course apply(String line);

    void findCourse();

    void deleteCourse() throws CourseNotFoundException;

    void findCourseByTitle();

}
