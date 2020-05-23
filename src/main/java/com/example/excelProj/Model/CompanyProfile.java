package com.example.excelProj.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;


@Entity
public class CompanyProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column(columnDefinition = "LONGTEXT")
    String corporateAddress;

    @Column
    String billingAddress;

    @Column
    String contactName;

    @Column
    String contactTitle;

    @Lob
    byte[] logo;

    @Lob
    byte[] resume;

    @Lob
    byte[] certificate;

    @Column
    String logoContentType;

    @Column
    Double avgRating;


    @OneToMany(mappedBy="companyProfile")
    @JsonBackReference(value = "job_list_reference")
    List<Job> jobList;

    @OneToMany(mappedBy="companyProfile")
    @JsonBackReference(value = "reiview_list_reference")
    List<ReviewAndRating> reviewAndRatings;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference(value = "user_reference")
    User user;

    public CompanyProfile() {
    }

    public CompanyProfile(String name, String corporateAddress, String billingAddress, String contactName, String contactTitle, byte[] logo, byte[] resume, byte[] certificate, String logoContentType, Double avgRating, List<Job> jobList, List<ReviewAndRating> reviewAndRatings, User user) {
        this.name = name;
        this.corporateAddress = corporateAddress;
        this.billingAddress = billingAddress;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.logo = logo;
        this.resume = resume;
        this.certificate = certificate;
        this.logoContentType = logoContentType;
        this.avgRating = avgRating;
        this.jobList = jobList;
        this.reviewAndRatings = reviewAndRatings;
        this.user = user;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorporateAddress() {
        return corporateAddress;
    }

    public void setCorporateAddress(String corporateAddress) {
        this.corporateAddress = corporateAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public List<ReviewAndRating> getReviewAndRatings() {
        return reviewAndRatings;
    }

    public void setReviewAndRatings(List<ReviewAndRating> reviewAndRatings) {
        this.reviewAndRatings = reviewAndRatings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

