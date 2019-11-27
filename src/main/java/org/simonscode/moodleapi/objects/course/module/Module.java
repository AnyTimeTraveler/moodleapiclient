package org.simonscode.moodleapi.objects.course.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Module {
    protected long id;
    protected String url;
    protected String name;
    protected long instance;
    protected boolean visible;

    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible == 1;
    }

    protected boolean uservisible;
    protected boolean visibleoncoursepage;
    protected String modicon;
    protected String modname;
    protected String modplural;
    protected int indent;
    protected String onclick;
    protected String customdata;
    protected boolean completion;
    private String afterlink;

    @JsonProperty("visibleoncoursepage")
    public void setVisibleOnCoursePage(int visible) {
        this.visibleoncoursepage = visible == 1;
    }

    @JsonProperty("completion")
    public void setCompletion(int completion) {
        this.completion = completion == 1;
    }

}
