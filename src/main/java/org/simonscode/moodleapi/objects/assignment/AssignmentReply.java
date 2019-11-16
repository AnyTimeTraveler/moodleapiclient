package org.simonscode.moodleapi.objects.assignment;

import lombok.Data;

@Data
public class AssignmentReply {
    private CourseStub[] courses;
    private Warning[] warnings;

    @Data
    private static class Warning {
        private String item;
        private long itemid;
        private String warningcode;
        private String message;
    }
}
