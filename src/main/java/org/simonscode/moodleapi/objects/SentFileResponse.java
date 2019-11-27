package org.simonscode.moodleapi.objects;

import lombok.Data;

@Data
public class SentFileResponse {
    private String component; // "user",
    private long contextid; // 25
    private String userid; // "3",
    private String filearea; // "draft",
    private String filename; // "IMG_20191108_233516.jpg",
    private String filepath; // "\/",
    private long itemid; // 653739666
    private String license; // "allrightsreserved",
    private String author; // "Test Test",
    private String source; // "O:8:\"stdClass\":1:{s:6:\"source\";s:23:\"IMG_20191108_233516.jpg\";}"

}
