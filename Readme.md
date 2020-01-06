# Moodle API Client

This is a library that behaves like the mobile app and allows you to use part of it's features.

In essence, this is just a wrapper around the JSON API and is far from complete.

What's working:
 - Login
 - Get courses of user
 - Get course details (the content of the moodle page of a course)
 - Get assignments for a set of courses
 - Get status of an assignment
 - Download a file 
 - Upload a file (untested)
 - Hand in an assignment with an uploaded file attached (untested)

What's planned:
 - Test untested features
 - Join and leave courses
 - Display polls
 - Participate in polls
 - Write proper tests (currently don't have time to setup a moodle instance and figure out good automated testing, help appreciated)

# Development

If you want to contribute, create an issue describing what you are working on first!
Otherwise, this library should be pretty straightforward to build with maven.
