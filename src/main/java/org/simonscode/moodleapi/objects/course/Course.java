package org.simonscode.moodleapi.objects.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Course {
    private long id;
    private String shortname;
    private String fullname;
    private long enrolledusercount;
    private String idnumber;
    private boolean visible;

    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible == 1;
    }
    private String summary;
    private int summaryformat;
    private String format;
    private boolean showgrades;
    private String lang;
    private boolean enablecompletion;
}
