package com.example.eedu.courseDetails;

public class categoryHelperClass {

    String image;
    String cat;

    public categoryHelperClass(){

    }

    public categoryHelperClass(String image, String cat) {
        this.image = image;
        this.cat = cat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
