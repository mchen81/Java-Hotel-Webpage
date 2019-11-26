package common.bean;

import com.google.gson.annotations.SerializedName;

public class HotelJsonObject {

    @SerializedName("id")
    private int id;
    @SerializedName("f")
    private String name;
    @SerializedName("c")
    private String country;
    @SerializedName("pr")
    private String state;
    @SerializedName("ci")
    private String city;
    @SerializedName("ad")
    private String address;
    @SerializedName("ll")
    private Coordinate coordinate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return getId() + ", " + getName();
    }

    public Double getLatitude() {
        return coordinate.latitude;
    }

    public Double getLongitude() {
        return coordinate.longitude;
    }

    static class Coordinate {
        @SerializedName("lat")
        private double latitude;
        @SerializedName("lng")
        private double longitude;
    }
}
