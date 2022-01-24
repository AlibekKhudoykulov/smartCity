package com.example.smartcity.constants;

public interface PathConstant {
    String VERSION = "/v1";

    String BASE_PATH = "/api" + VERSION;

    String AUTH_CONTROLLER = BASE_PATH + "/login";
    String CRIME_CONTROLLER = BASE_PATH + "/crime";
    String OFFICER_CONTROLLER = BASE_PATH + "/officer";
    String USER_CONTROLLER = BASE_PATH + "/user";
    String POLICE_STATION_CONTROLLER = BASE_PATH + "/police";
    String PRISONER_CONTROLLER = BASE_PATH +"/prisoner";
    String VICTIM_CONTROLLER = BASE_PATH + "/victim";
    String WITNESS_CONTROLLER = BASE_PATH + "/witness";
}
