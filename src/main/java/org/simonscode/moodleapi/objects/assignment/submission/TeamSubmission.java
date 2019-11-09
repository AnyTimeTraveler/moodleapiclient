package org.simonscode.moodleapi.objects.assignment.submission;

import lombok.Data;

import java.util.List;

@Data
public class TeamSubmission extends Submission {
    private long groupid;
    private long assignment;
    private int latest;
    private List<Plugin> plugins;

    @Data
    private static class Plugin {
        private String type;
        private String name;
        private List<FileArea> fileareas;

        @Data
        private static class FileArea {
            private String area;
        }
    }

    private long submissiongroup;
    private List<Long> submissiongroupmemberswhoneedtosubmit;
    private boolean submissionsenabled;
    private boolean locked;
    private boolean graded;
    private boolean canedit;
    private boolean cansubmit;
    private String extensionduedate;
    private boolean blindmarking;
    private String gradingstatus;
    private List<Long> usergroups;
}
