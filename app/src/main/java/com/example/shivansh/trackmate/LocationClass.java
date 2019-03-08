package com.example.shivansh.trackmate;

import com.google.android.gms.maps.model.LatLng;

public class LocationClass {

        private String name,Camera;
        private LatLng latLng;



    public LocationClass(String name, LatLng latLng, String Camera) {
            this.name = name;
            this.latLng = latLng;
            this.Camera = Camera;
        }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }
    }


