package org.simonscode.moodleapi.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
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
    private String siteid;
    private String sitecalendartype;
    private String usercalendartype;
    private String theme;

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
