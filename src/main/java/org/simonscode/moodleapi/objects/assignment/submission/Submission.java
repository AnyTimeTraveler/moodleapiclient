package org.simonscode.moodleapi.objects.assignment.submission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    private long id;
    private long userid;
    private long groupid;
    private long assignment;
    private int attemptnumber;
    private long timecreated;
    private long timemodified;
    private int latest;
    private String status;
    // plugins: []
}
