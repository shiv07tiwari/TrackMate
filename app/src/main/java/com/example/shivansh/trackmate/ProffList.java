package com.example.shivansh.trackmate;

import java.util.ArrayList;

public class ProffList {

    private String Camera;
    private ArrayList<String> ProffName = new ArrayList<>();

    public ProffList(String camera, ArrayList<String> proffName) {
        Camera = camera;
        ProffName = proffName;
    }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }

    public ArrayList<String> getProffName() {
        return ProffName;
    }

    public void setProffName(ArrayList<String> proffName) {
        ProffName = proffName;
    }
}
