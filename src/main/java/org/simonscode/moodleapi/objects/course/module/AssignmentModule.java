package org.simonscode.moodleapi.objects.course.module;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentModule extends Module {
    private String description;
}
