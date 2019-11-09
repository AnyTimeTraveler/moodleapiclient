package org.simonscode.moodleapi.objects.course;

import lombok.Data;

@Data
public class Course {
    private long id;
    private String shortname;
    private String fullname;
    private long enrolledusercount;
    private String idnumber;
    private int visible;
    private String summary;
    private int summaryformat;
    private String format;
    private boolean showgrades;
    private String lang;
    private boolean enablecompletion;
}
