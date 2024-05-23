package src.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String title;
    private String[] instructorName;
    private String[] requirement;
    private LocalDateTime startDate;
    public static List<Course> courses = new ArrayList<>();
    public  static String fileName="Temp.txt";
    public static Random random = new Random();

}
