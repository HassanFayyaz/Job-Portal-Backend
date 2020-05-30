package com.example.excelProj.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class CandidateProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String field;

    @Column
    String imageContentType;

    @Column
    String resumeContentType;

    @Column(columnDefinition = "LONGTEXT")
    String presentationLetter;

    @Lob
    @Column
    byte[] cv;


    @Lob
    @Column
    byte[] dp;


    public CandidateProfile() {
    }

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
     User user;


//    @JsonIgnore
////    @ManyToMany(mappedBy = "candidateProfileList")
////    List<Job> jobList;


    public CandidateProfile(String field, String imageContentType, String resumeContentType, String presentationLetter, byte[] cv, byte[] dp, User user) {
        this.field = field;
        this.imageContentType = imageContentType;
        this.resumeContentType = resumeContentType;
        this.presentationLetter = presentationLetter;
        this.cv = cv;
        this.dp = dp;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }

    public String getPresentationLetter() {
        return presentationLetter;
    }

    public void setPresentationLetter(String presentationLetter) {
        this.presentationLetter = presentationLetter;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public byte[] getDp() {
        return dp;
    }

    public void setDp(byte[] dp) {
        this.dp = dp;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
