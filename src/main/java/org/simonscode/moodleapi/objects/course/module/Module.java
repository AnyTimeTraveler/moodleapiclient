package org.simonscode.moodleapi.objects.course.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Module {
    private long id;
    private String url;
    private String name;
    private long instance;
    private boolean visible;

    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible == 1;
    }

    private String modicon;
    private String modname;
    private String modplural;
    private int indent;
}
