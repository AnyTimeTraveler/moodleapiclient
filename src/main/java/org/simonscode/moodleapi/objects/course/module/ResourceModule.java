package org.simonscode.moodleapi.objects.course.module;

import lombok.Data;
import org.simonscode.moodleapi.objects.course.module.content.ModuleContent;

import java.util.List;

@Data
public class ResourceModule extends Module {
    private List<ModuleContent> contents;
}
