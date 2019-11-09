package org.simonscode.moodleapi.objects.course;

import lombok.Data;
import org.simonscode.moodleapi.objects.course.module.Module;

import java.util.List;

@Data
public class CourseContent {
    private long id;
    private String name;
    private int visible;
    private String summary;
    private int summaryformat;
    private List<Module> modules;
}
