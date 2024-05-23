package src.service;

import src.model.Course;

import java.io.IOException;

public interface AllFunction {
    void addCourseFromUserInput() throws IOException;

    Course apply(String line);

    void findCourse();

    void deleteCourse() ;

    void findCourseByTitle();

}
