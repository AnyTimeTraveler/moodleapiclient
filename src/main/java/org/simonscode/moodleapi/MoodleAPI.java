package org.simonscode.moodleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MoodleAPI {

    private static final String USER_AGENT = "MoodleBot/1.0.0";

    private static String moodleAddress = "";

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
    public static void setMoodleAddress(String moodleAddress) {
        if (moodleAddress.endsWith("/")) {
            moodleAddress = moodleAddress.substring(0, moodleAddress.lastIndexOf('/'));
        }
        MoodleAPI.moodleAddress = moodleAddress;
    }

    public static String getToken(final String username, final String password) throws UnirestException {
        return Unirest.post(moodleAddress + "/login/token.php")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body("username=" + username + "&password=" + password + "&service=moodle_mobile_app")
                .asJson().getBody().getObject().getString("token");
    }

    public static UserInfo getUserInfo(final String token) throws UnirestException {
        return Unirest.post(moodleAddress + "/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=core_webservice_get_site_info")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body("moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=core_webservice_get_site_info&wstoken=" + token)
                .asObject(UserInfo.class).getBody();
    }

    public static Course[] getCourses(final String token, final long userid) throws UnirestException {
        HttpResponse<Course[]> response = Unirest.post(moodleAddress + "/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=core_enrol_get_users_courses")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body("moodlewssettingfilter=true&moodlewssettingfileurl=true&wstoken=" + token + "&userid=" + userid + "&wsfunction=core_enrol_get_users_courses")
                .asObject(Course[].class);
        return response.getBody();
    }

    public static CourseContent[] getCourseDetails(final String token, final long courseid) throws UnirestException {
        HttpResponse<CourseContent[]> response = Unirest.post(moodleAddress + "/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=core_course_get_contents")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body("courseid=" + courseid + "&moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=core_course_get_contents&wstoken=" + token)
                .asObject(CourseContent[].class);
        return response.getBody();
    }

    public static AssignmentReply getAssignments(final String token, final List<Long> courses) throws UnirestException {
        StringBuilder bodyBuilder = new StringBuilder();

        int counter = 0;
        for (long course : courses) {
            bodyBuilder.append(URLEncoder.encode("courseids[" + counter++ + "]", StandardCharsets.UTF_8));
            bodyBuilder.append('=');
            bodyBuilder.append(course);
            bodyBuilder.append("&");
        }
        bodyBuilder.append("moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=mod_assign_get_assignments&wstoken=");
        bodyBuilder.append(token);

        HttpResponse<AssignmentReply> response = Unirest.post(moodleAddress + "/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=mod_assign_get_assignments")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body(bodyBuilder.toString())
                .asObject(AssignmentReply.class);
        return response.getBody();
    }

    public static AssignmentStatus getAssignmentStatus(final String token, final long userid, long assignmentid) throws UnirestException {
        HttpResponse<AssignmentStatus> response = Unirest.post(moodleAddress + "/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=mod_assign_get_submission_status")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .body("assignid=" + assignmentid + "&userid=" + userid + "&moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=mod_assign_get_submission_status&wstoken=" + token)
                .asObject(AssignmentStatus.class);
        return response.getBody();
    }

    public static InputStream downloadFile(String token, String url) throws UnirestException {
        return Unirest.get(url)
                .queryString("token", token)
                .asBinary()
                .getBody();
    }

    public static SentFileResponse[] sendFile(String token, File file) throws UnirestException, FileNotFoundException {
        @Cleanup InputStream is = new FileInputStream(file);
        return sendFile(token, is, file.getName());
    }


    public static SentFileResponse[] sendFile(String token, InputStream data, String fileName) throws UnirestException {
        return Unirest.post(moodleAddress + "/webservice/upload.php")
                .header("cache-control", "no-cache")
                .field("token", token)
                .field("filearea", "draft")
                .field("itemid", "0")
                .field("file", data, fileName)
                .asObject(SentFileResponse[].class).getBody();
    }
}
