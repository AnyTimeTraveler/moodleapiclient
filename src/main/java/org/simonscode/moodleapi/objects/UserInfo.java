package org.simonscode.moodleapi.objects;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private String sitename;
    private String username;
    private String firstname;
    private String lastname;
    private String fullname;
    private String lang;
    private long userid;
    private String siteurl;
    private String userpictureurl;
    private List<Function> functions;

    private int downloadfiles;
    private int uploadfiles;
    private String release;
    private String version;
    private String mobilecssurl;
    private List<AdvFeature> advancedfeatures;

    private boolean usercanmanageownfiles;
    private long userquota;
    private long usermaxuploadfilesize;
    private int userhomepage;

    @Data
    public static class Function {
        private String name;
        private String version;
    }

    @Data
    public static class AdvFeature {
        private String name;
        private int value;
    }
}
