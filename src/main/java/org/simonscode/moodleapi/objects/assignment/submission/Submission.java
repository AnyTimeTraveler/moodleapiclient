package org.simonscode.moodleapi.objects.assignment.submission;

import lombok.Data;

@Data
public class Submission {
    private long id;
    private long userid;
    private int attemptnumber;
    private long timecreated;
    private long timemodified;
    private String status;
}
