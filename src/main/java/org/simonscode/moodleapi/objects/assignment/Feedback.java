package org.simonscode.moodleapi.objects.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feedback {
    private Grade grade;
    private String gradefordisplay;
    private long gradeddate;
    // "plugins": []

    @Data
    private static class Grade {
        private long id;
        private long assignment;
        private long userid;
        private long attemptnumber;
        private long timecreated;
        private long timemodified;
        private long grader;
        private double grade;
    }
}
