package com.example.moviesmatch.models;

public class MoviesMatchURLS {
    public static String moviesMatchURL = "https://EQ2.420-6D9-LI.E2021.info.cegeplimoilou.ca";
    //public static String moviesMatchURL = "https://10.0.2.2:44394";

    //User
    public static String loginURL = "/api/user/login"; //Post
    public static String signupURL = "/api/user/signUp"; //Post
    public static String resetPasswordURL = "/api/user/sendResetPasswordEmail?email="; //Get
    public static String updateUserURL = "/api/user/updateUser"; //Put
    public static String getUserURL = "/api/user/getUser/"; //Get
    public static String changePasswordURL = "/api/user/changePassword"; //Put
    public static String getUserGroupsURL = "/api/user/getUserGroups/"; //Get
    public static String deleteUserURL = "/api/user/deleteUser"; //Delete

    //Genres
    public static String getAllGenresURL = "/api/genre/getAllGenres"; //Get
    public static String addGenresToUserURL = "/api/genre/addGenresToUser"; //Post
    public static String getUserGenreURL = "/api/genre/getUserGenres?idUser="; //Get

    //Group
    public static String createGroupURL = "/api/group/createGroup"; //Post
    public static String joinGroupURL = "/api/group/joinGroup"; //Post
    public static String getGroupInfoURL = "/api/group/getGroupInfo?groupId="; //Get
    public static String leaveGroupURL = "/api/group/leaveGroup"; //Delete
    public static String changeGroupNameURL = "/api/group/leaveGroup"; //Post
    public static String getMatchesURL = "/api/group/getMatches"; //Get
    public static String deleteMatchURL = "/api/group/deleteMatch"; //Delete

    //Movies
    public static String getMoviesURL = "/api/movie/GetMovies"; //Get
    public static String postSwipeMovie = "/api/movie/PostSwipeMovie"; //Post
    public static String getMatchURL = "/api/movie/PostSwipeMovie"; //Post
}
