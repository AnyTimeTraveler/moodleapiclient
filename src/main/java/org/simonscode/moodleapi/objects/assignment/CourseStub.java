package org.simonscode.moodleapi.objects.assignment;

import lombok.Data;

@Data
public class CourseStub {
    private long id;
    private String fullname;
    private String shortname;
    private long timemodified;
    private AssignmentSummary[] assignments;
}
