package org.simonscode.moodleapi.objects.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.simonscode.moodleapi.objects.course.module.Module;

import java.util.List;

@Data
public class CourseContent {
    private long id;
    private String name;
    private boolean visible;

    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible == 1;
    }

    private String summary;
    private int summaryformat;
    private long section;
    private long hiddenbynumsections;
    private boolean uservisible;
    private List<Module> modules;
}
