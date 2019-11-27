package org.simonscode.moodleapi.objects.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Course {
    private long id; //  3
    private String shortname; // test2
    private String fullname; // Test 2
    private String displayname; // Test 2
    private long enrolledusercount; //  1
    private String idnumber; //
    private boolean visible; //  1

    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible == 1;
    }

    private String summary; //
    private int summaryformat; //  1
    private String format; // topics
    private boolean showgrades; //  true
    private String lang; //
    private boolean enablecompletion; //  true
    private boolean completionhascriteria; //  false
    private boolean completionusertracked; //  true
    private long category; //  1
    private String progress; //  null
    private boolean completed; //  false
    private long startdate; //  1573257600
    private long enddate; //  1604793600
    private long marker; //  0
    private long lastaccess; //  1574802658
    private boolean isfavourite; //  false
    private boolean hidden; //  false
    private OverviewFile[] overviewfiles;
}
