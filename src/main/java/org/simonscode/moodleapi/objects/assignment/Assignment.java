package org.simonscode.moodleapi.objects.assignment;

import lombok.Data;

import java.util.List;
@Data
public class Assignment {
    private long id;
    private long cmid;
    private long course;
    private String name;
    private int nosubmissions;
    private int submissiondrafts;
    private int sendnotifications;
    private int sendlatenotifications;
    private int sendstudentnotifications;
    private long duedate;
    private long allowsubmissionsfromdate;
    private int grade;
    private long timemodified;
    private int completionsubmit;
    private long cutoffdate;
    private int teamsubmission;
    private int requireallteammemberssubmit;
    private long teamsubmissiongroupingid;
    private int blindmarking;
    private int revealidentities;
    private String attemptreopenmethod;
    private int maxattempts;
    private int markingworkflow;
    private int markingallocation;
    private int requiresubmissionstatement;
    private List<Config> configs;
    private String intro;
    private int introformat;

    @Data
    private static class Config {
        private long id;
        private long assignment;
        private String plugin;
        private String subtype;
        private String name;
        private String value;
    }
}
