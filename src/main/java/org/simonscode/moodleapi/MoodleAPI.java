package org.simonscode.moodleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import lombok.Cleanup;
import org.simonscode.moodleapi.helpers.PropertyBasedDeserializer;
import org.simonscode.moodleapi.objects.SentFileResponse;
import org.simonscode.moodleapi.objects.UserInfo;
import org.simonscode.moodleapi.objects.assignment.AssignmentReply;
import org.simonscode.moodleapi.objects.assignment.AssignmentStatus;
import org.simonscode.moodleapi.objects.course.Course;
import org.simonscode.moodleapi.objects.course.CourseContent;
import org.simonscode.moodleapi.objects.course.module.Module;
import org.simonscode.moodleapi.objects.course.module.*;
import org.simonscode.moodleapi.objects.course.module.content.FileContent;
import org.simonscode.moodleapi.objects.course.module.content.ModuleContent;
import org.simonscode.moodleapi.objects.course.module.content.URLContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MoodleAPI {

    private static final String USER_AGENT = "MoodleBot/1.0.0";

    private static String moodleAddress = "";
    private static String moodleHost = "";

    static {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        SimpleModule module = new SimpleModule("module-deserializers", Version.unknownVersion());
        final PropertyBasedDeserializer<Module> moduleDerserializer = new PropertyBasedDeserializer<>(Module.class, "modname");
        moduleDerserializer.register("forum", ForumModule.class);
        moduleDerserializer.register("resource", ResourceModule.class);
        moduleDerserializer.register("folder", ResourceModule.class);
        moduleDerserializer.register("page", PageModule.class);
        moduleDerserializer.register("label", LabelModule.class);
        moduleDerserializer.register("choicegroup", ChoiceGroupModule.class);
        moduleDerserializer.register("choice", ChoiceModule.class);
        moduleDerserializer.register("url", UrlModule.class);
        moduleDerserializer.register("assign", AssignmentModule.class);

        module.addDeserializer(Module.class, moduleDerserializer);
        mapper.registerModule(module);

        final PropertyBasedDeserializer<ModuleContent> contentDeserializer = new PropertyBasedDeserializer<>(ModuleContent.class, "type");
        contentDeserializer.register("file", FileContent.class);
        contentDeserializer.register("url", URLContent.class);

        module.addDeserializer(ModuleContent.class, contentDeserializer);
        mapper.registerModule(module);
        Unirest.setObjectMapper(new ObjectMapper() {
            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * Set moodle address
     *
     * @param moodleAddress base address to your moodle installation
     */
    public static void setMoodleAddresses(String moodleAddress, String moodleHost) {
        if (moodleAddress.endsWith("/")) {
            moodleAddress = moodleAddress.substring(0, moodleAddress.lastIndexOf('/'));
        }
        MoodleAPI.moodleAddress = moodleAddress;
        MoodleAPI.moodleHost = moodleHost;
    }


    private static HttpRequestWithBody getMinimalRequest(String subURL) {
        return Unirest.post(moodleAddress + subURL)
                .header("User-Agent", USER_AGENT)
                .header("Host", moodleHost)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache");
    }

    private static MultipartBody getFormRequest(String token, String function) {
        return getMinimalRequest("/webservice/rest/server.php")
                .queryString("moodlewsrestformat", "json")
                .queryString("wsfunction", function)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("wstoken", token)
                .field("moodlewssettingfilter", true)
                .field("moodlewssettingfileurl", true)
                .field("wsfunction", function);
    }

    public static String getToken(final String username, final String password) throws UnirestException {
        return getMinimalRequest("/login/token.php")
                .field("username", username)
                .field("password", password)
                .field("service", "moodle_mobile_app")
                .asJson()
                .getBody()
                .getObject()
                .getString("token");
    }

    public static UserInfo getUserInfo(final String token) throws UnirestException {
        return getFormRequest(token, "core_webservice_get_site_info")
                .asObject(UserInfo.class).getBody();
    }

    public static Course[] getCourses(final String token, final long userid) throws UnirestException {
        return getFormRequest(token, "core_enrol_get_users_courses")
                .field("userid", userid)
                .asObject(Course[].class).getBody();
    }

    public static CourseContent[] getCourseDetails(final String token, final long courseid) throws UnirestException {
        return getFormRequest(token, "core_course_get_contents")
                .field("courseid", courseid)
                .asObject(CourseContent[].class).getBody();
    }

    public static AssignmentReply getAssignments(final String token, final List<Long> courses) throws UnirestException {
        final MultipartBody request = getFormRequest(token, "mod_assign_get_assignments");

        int counter = 0;
        for (long course : courses) {
            request.field("courseids[" + counter++ + "]", course);
        }
        return request.asObject(AssignmentReply.class).getBody();
    }

    public static AssignmentStatus getAssignmentStatus(final String token, final long userid, long assignmentid) throws UnirestException {
        return getFormRequest(token, "mod_assign_get_submission_status")
                .field("assignid", assignmentid)
                .field("userid", userid)
                .asObject(AssignmentStatus.class).getBody();
    }

    public static InputStream downloadFile(String token, String url) throws UnirestException {
        return Unirest.get(url)
                .queryString("token", token)
                .asBinary()
                .getBody();
    }

    public static SentFileResponse[] sendFile(String token, File file) throws UnirestException, IOException {
        @Cleanup InputStream is = new FileInputStream(file);
        return sendFile(token, is, file.getName());
    }

    public static SentFileResponse[] sendFile(String token, InputStream data, String fileName) throws UnirestException {
        return getMinimalRequest("/webservice/upload.php")
                .field("token", token)
                .field("filearea", "draft")
                .field("itemid", "0")
                .field("file", data, fileName)
                .asObject(SentFileResponse[].class).getBody();
    }

    public static void assignFileToAssignment(String token, long assignmentId, long fileId) throws UnirestException {
        final MultipartBody field = getFormRequest(token, "mod_assign_save_submission");
        System.out.println(field.getEntity());
        field.field("assignmentid", assignmentId)
                .field("plugindata[files_filemanager]", fileId)
                .asString();
    }
}
