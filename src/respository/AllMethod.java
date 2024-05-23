package src.respository;

import src.exception.CourseNotFoundException;

public class AllMethod extends CourseManager {
    static CourseManager md = new CourseManager();

    public static void addTofile() {

        md.addCourseFromUserInput();
    }

    public static void showCourse() {

        md.displayCourses(FILE_PATH);
    }

    public static void findById() {

        md.findCourse();
    }

    public static void findByTittle() {

        md.findCourseByTitle();
    }

    public static void deleteById() throws CourseNotFoundException {
        md.deleteCourse();
    }

    public static void clearAll() {

        md.clearDataStore();
    }

    public static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
}
