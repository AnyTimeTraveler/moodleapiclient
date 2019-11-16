package org.simonscode.moodleapi.objects.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignmentStatus {
    private Attempt lastattempt;
    private Feedback feedback;
    // "warnings": []
}
