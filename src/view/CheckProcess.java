package src.view;

import src.exception.CourseNotFoundException;
import src.respository.AllMethod;

import java.util.Scanner;

public class CheckProcess extends AllMethod {
    public static void process() throws CourseNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println(repeat("=",100));
            System.out.println("\n--- Course Management Menu ---");
            System.out.println("0. Exit Program");
            System.out.println("1. Add Courses");
            System.out.println("2. Display Course");
            System.out.println("3. Find Course by ID");
            System.out.println("4. Find Course by Title");
            System.out.println("5. Delete Course ");
            System.out.println("6. Clear Data Store");
            System.out.println(repeat("=",100));
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addTofile();
                    break;
                case 2:
                    showCourse();
                    break;
                case 3:
                    findById();
                    break;
                case 4:
                    findByTittle();
                    break;
                case 5:
                    deleteById();
                    break;
                case 6:
                    clearAll();
                    break;
                case 0:
                case 99:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("You input wrong option!");
                    break;
            }
        } while (choice != 0 && choice != 99);
        scanner.close();
    }
}
