package org.pojo.course;

import org.pojo.course.CourseData;

import java.util.List;

public class CourseTypesData {

    private List<CourseData> webAutomation;
    private List<CourseData> api;
    private List<CourseData> mobile;

    public List<CourseData> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<CourseData> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<CourseData> getApi() {
        return api;
    }

    public void setApi(List<CourseData> api) {
        this.api = api;
    }

    public List<CourseData> getMobile() {
        return mobile;
    }

    public void setMobile(List<CourseData> mobile) {
        this.mobile = mobile;
    }
}
