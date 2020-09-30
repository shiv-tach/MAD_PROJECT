package com.example.solo;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private String name;
    private String fullName;
    private String password;
    public Map<String, Boolean> stars = new HashMap<>();


    public Users(){




    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", name);
        result.put("Full Name", fullName);
        result.put("title", password);
        result.put("stars", stars);

        return result;
    }


}
