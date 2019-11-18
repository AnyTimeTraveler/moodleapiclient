package org.simonscode.moodleapi.objects.course.module.content;

import lombok.Data;

@Data
public class URLContent extends ModuleContent {
    private String filename;
    private String filepath;
    private long filesize;
    private String fileurl;
    private long timecreated;
    private long timemodified;
    private int sortorder;
    private long userid;
    private String author;
    private String license;
}
