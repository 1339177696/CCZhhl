package com.hulian.oa.bean;

public class MeetingRoom  {
    private String id;
    private String meetingRoomLocation;
    private String galleryful;
    private String photoPath;
    private String isCheck;

    public String getMeetingContacts() {
        return meetingContacts;
    }

    public void setMeetingContacts(String meetingContacts) {
        this.meetingContacts = meetingContacts;
    }

    private String meetingContacts;
    private String meetingRoomName;

    public String getMeetingContactsPhone() {
        return meetingContactsPhone;
    }

    public void setMeetingContactsPhone(String meetingContactsPhone) {
        this.meetingContactsPhone = meetingContactsPhone;
    }

    private String meetingContactsPhone;


    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }


    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetingRoomLocation() {
        return meetingRoomLocation;
    }

    public void setMeetingRoomLocation(String meetingRoomLocation) {
        this.meetingRoomLocation = meetingRoomLocation;
    }

    public String getGalleryful() {
        return galleryful;
    }

    public void setGalleryful(String galleryful) {
        this.galleryful = galleryful;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
