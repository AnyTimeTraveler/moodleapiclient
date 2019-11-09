package org.simonscode.moodleapi.objects.course.module;

import lombok.Data;
import org.simonscode.moodleapi.objects.course.module.content.Content;

import java.util.List;

@Data
public class UrlModule extends Module {
    private List<Content> contents;

}
