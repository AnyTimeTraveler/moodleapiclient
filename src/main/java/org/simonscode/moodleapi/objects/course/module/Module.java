package org.simonscode.moodleapi.objects.course.module;

import lombok.Data;

@Data
public class Module {
    private long id;
    private String url;
    private String name;
    private long instance;
    private int visible;
    private String modicon;
    private String modname;
    private String modplural;
    private int indent;
}
