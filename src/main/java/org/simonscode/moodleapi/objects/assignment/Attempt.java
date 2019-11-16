package org.simonscode.moodleapi.objects.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.simonscode.moodleapi.objects.assignment.submission.Submission;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attempt {
    private Submission submission;
    private boolean submissionsenabled;
    private boolean locked;
    private boolean graded;
    private boolean canedit;
    private boolean cansubmit;
    private long extensionduedate;
    private boolean blindmarking;
    private String gradingstatus;

    //    submissiongroupmemberswhoneedtosubmit: []
    // "usergroups": []
}
