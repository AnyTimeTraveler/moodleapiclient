package org.simonscode.moodleapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.simonscode.moodleapi.objects.assignment.Assignment;
import org.simonscode.moodleapi.objects.UserInfo;
import org.simonscode.moodleapi.objects.course.Course;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Requests {

    private static final String USER_AGENT = "MoodleBot/1.0.0";


    public static String getToken(final String username, final String password) throws UnirestException {
        return Unirest.post("https://moodle.hs-emden-leer.de/moodle/login/token.php")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "close")
                .header("cache-control", "no-cache")
                .body("username=" + username + "&password=" + password + "&service=moodle_mobile_app")
                .asJson().getBody().getObject().getString("token");
    }

    public static UserInfo getUserInfo(final String token) throws UnirestException {
        return Unirest.post("https://moodle.hs-emden-leer.de/moodle/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=core_webservice_get_site_info")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "close")
                .header("cache-control", "no-cache")
                .body("moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=core_webservice_get_site_info&wstoken=" + token)
                .asObject(UserInfo.class).getBody();
    }

    public static List<Course> getCourses(final String token, final String userid) throws UnirestException {
        //noinspection unchecked
        HttpResponse<List<Course>> response = Unirest.post("https://moodle.hs-emden-leer.de/moodle/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=core_enrol_get_users_courses")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "close")
                .header("cache-control", "no-cache")
                .body("moodlewssettingfilter=true&moodlewssettingfileurl=true&wstoken=" + token + "&userid=" + userid + "&wsfunction=core_enrol_get_users_courses")
                .asObject(List.class);
        return response.getBody();
    }

    public static List<Assignment> getAssignments(final String token, final List<Long> courses) throws UnirestException {
        StringBuilder bodyBuilder = new StringBuilder();

        int counter = 0;
        for (long course : courses) {
            bodyBuilder.append(URLEncoder.encode("courseids[" + counter++ + "]=" + course, StandardCharsets.UTF_8));
        }
        bodyBuilder.append("&moodlewssettingfilter=true&moodlewssettingfileurl=true&wsfunction=mod_assign_get_assignments&wstoken=");
        bodyBuilder.append(token);

        //noinspection unchecked
        HttpResponse<List<Assignment>> response = Unirest.post("https://moodle.hs-emden-leer.de/moodle/webservice/rest/server.php?moodlewsrestformat=json&wsfunction=mod_assign_get_assignments")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "moodle.hs-emden-leer.de")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "close")
                .header("cache-control", "no-cache")
                .body(bodyBuilder.toString())
                .asObject(List.class);
        return response.getBody();
    }

    public void sendFile(String token) throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://moodle.hs-emden-leer.de/moodle/webservice/upload.php")
                .header("cache-control", "no-cache")
                .header("Postman-Token", "8990733f-d565-4cf6-b008-7fb46a2536f0")
                .field("token", token)
                .field("filearea", "draft")
                .field("itemid","0")
                .field("file",new File(""))
                .asString();
    }
}
