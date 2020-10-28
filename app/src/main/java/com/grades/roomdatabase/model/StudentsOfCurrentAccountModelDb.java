package com.grades.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "studentTable")
public class StudentsOfCurrentAccountModelDb {


    @PrimaryKey(autoGenerate = true)
    public int Id;

    @ColumnInfo(name = "isElementarySchool")
    private boolean isElementarySchool;


    @ColumnInfo(name = "idNumber")
    private int idNumber;

    @ColumnInfo(name = "studentNumber")
    private int studentNumber;

    @ColumnInfo(name = "schoolCode")
    private int schoolCode;

    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "relativeApplicationPhotoPath")
    private String relativeApplicationPhotoPath;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "middleName")
    private String middleName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "suffix")
    private String suffix;

    @ColumnInfo(name = "firstNameAlias")
    private String firstNameAlias;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "birthdate")
    private String birthdate;

    @ColumnInfo(name = "ethnicityCode")
    private String ethnicityCode;

    @ColumnInfo(name = "ethnicityDescription")
    private String ethnicityDescription;

    @ColumnInfo(name = "race")
    private String race;

    @ColumnInfo(name = "mailingAddress")
    private String mailingAddress;

    @ColumnInfo(name = "mailingCity")
    private String mailingCity;

    @ColumnInfo(name = "mailingState")
    private String mailingState;

    @ColumnInfo(name = "mailingZipCode")
    private String mailingZipCode;

    @ColumnInfo(name = "mailingZipCodeExtension")
    private String mailingZipCodeExtension;

    @ColumnInfo(name = "residenceAddress")
    private String residenceAddress;

    @ColumnInfo(name = "residenceCity")
    private String residenceCity;

    @ColumnInfo(name = "residenceState")
    private String residenceState;

    @ColumnInfo(name = "residenceZipCode")
    private String residenceZipCode;

    @ColumnInfo(name = "residenceZipCodeExtension")
    private String residenceZipCodeExtension;

    @ColumnInfo(name = "nameOfParentGuardian")
    private String nameOfParentGuardian;

    @ColumnInfo(name = "nameOfFather")
    private String nameOfFather;

    @ColumnInfo(name = "nameOfMother")
    private String nameOfMother;

    @ColumnInfo(name = "nameOfCounselor")
    private String nameOfCounselor;

    @ColumnInfo(name = "homePhone")
    private String homePhone;

    @ColumnInfo(name = "studentMobilePhone")
    private String studentMobilePhone;

    @ColumnInfo(name = "fatherWorkPhone")
    private String fatherWorkPhone;

    @ColumnInfo(name = "fatherWorkPhoneExtension")
    private String fatherWorkPhoneExtension;

    @ColumnInfo(name = "motherWorkPhone")
    private String motherWorkPhone;

    @ColumnInfo(name = "motherWorkPhoneExtension")
    private String motherWorkPhoneExtension;

    @ColumnInfo(name = "studentEmailAddress")
    private String studentEmailAddress;

    @ColumnInfo(name = "parentGuardianEmailAddress")


    private String parentGuardianEmailAddress;

    @ColumnInfo(name = "parentGuardianEducationLevel")
    private String parentGuardianEducationLevel;

    @ColumnInfo(name = "grade")
    private int grade;

    @ColumnInfo(name = "statusTag")
    private String statusTag;

    @ColumnInfo(name = "lockerID")
    private String lockerID;

    @ColumnInfo(name = "isAddressVerified")
    private boolean isAddressVerified;

    @ColumnInfo(name = "dateOfNameAndAddressVerification")
    private String dateOfNameAndAddressVerification;

    @ColumnInfo(name = "notAuthorizedToReleaseStudentRecords")
    private String notAuthorizedToReleaseStudentRecords;

    @ColumnInfo(name = "correspondenceLanguage")
    private String correspondenceLanguage;

    @ColumnInfo(name = "homeLanguage")
    private String homeLanguage;

    @ColumnInfo(name = "fluencyLanguage")
    private String fluencyLanguage;

    @ColumnInfo(name = "userCode1Title")
    private String userCode1Title;

    @ColumnInfo(name = "userCode1")
    private String userCode1;

    @ColumnInfo(name = "userCode1Description")
    private String userCode1Description;

    @ColumnInfo(name = "userCode2Title")
    private String userCode2Title;

    @ColumnInfo(name = "userCode2")
    private String userCode2;

    @ColumnInfo(name = "userCode2Description")
    private String userCode2Description;

    @ColumnInfo(name = "userCode3Title")
    private String userCode3Title;

    @ColumnInfo(name = "userCode3")
    private String userCode3;

    @ColumnInfo(name = "userCode3Description")
    private String userCode3Description;

    @ColumnInfo(name = "userCode4Title")
    private String userCode4Title;

    @ColumnInfo(name = "userCode4")
    private String userCode4;

    @ColumnInfo(name = "userCode4Description")
    private String userCode4Description;

    @ColumnInfo(name = "userCode5Title")
    private String userCode5Title;

    @ColumnInfo(name = "userCode5")
    private String userCode5;

    @ColumnInfo(name = "userCode5Description")
    private String userCode5Description;

    @ColumnInfo(name = "userCode6Title")
    private String userCode6Title;

    @ColumnInfo(name = "userCode6")
    private String userCode6;

    @ColumnInfo(name = "userCode6Description")
    private String userCode6Description;

    @ColumnInfo(name = "userCode7Title")
    private String userCode7Title;

    @ColumnInfo(name = "userCode7")
    private String userCode7;

    @ColumnInfo(name = "userCode7Description")
    private String userCode7Description;

    @ColumnInfo(name = "userCode8Title")
    private String userCode8Title;

    @ColumnInfo(name = "userCode8")
    private String userCode8;

    @ColumnInfo(name = "userCode8Description")
    private String userCode8Description;

    @ColumnInfo(name = "userCode9Title")
    private String userCode9Title;

    @ColumnInfo(name = "userCode9")
    private String userCode9;

    @ColumnInfo(name = "userCode9Description")
    private String userCode9Description;

    @ColumnInfo(name = "userCode10Title")
    private String userCode10Title;

    @ColumnInfo(name = "userCode10")
    private String userCode10;

    @ColumnInfo(name = "userCode10Description")
    private String userCode10Description;

    @ColumnInfo(name = "userCode11Title")
    private String userCode11Title;

    @ColumnInfo(name = "userCode11")
    private String userCode11;

    @ColumnInfo(name = "userCode11Description")
    private String userCode11Description;

    @ColumnInfo(name = "userCode12Title")
    private String userCode12Title;

    @ColumnInfo(name = "userCode12")
    private String userCode12;

    @ColumnInfo(name = "userCode12Description")
    private String userCode12Description;

    @ColumnInfo(name = "userCode13Title")
    private String userCode13Title;

    public boolean isElementarySchool() {
        return isElementarySchool;
    }

    public void setElementarySchool(boolean elementarySchool) {
        isElementarySchool = elementarySchool;
    }



    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(int schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRelativeApplicationPhotoPath() {
        return relativeApplicationPhotoPath;
    }

    public void setRelativeApplicationPhotoPath(String relativeApplicationPhotoPath) {
        this.relativeApplicationPhotoPath = relativeApplicationPhotoPath;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstNameAlias() {
        return firstNameAlias;
    }

    public void setFirstNameAlias(String firstNameAlias) {
        this.firstNameAlias = firstNameAlias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEthnicityCode() {
        return ethnicityCode;
    }

    public void setEthnicityCode(String ethnicityCode) {
        this.ethnicityCode = ethnicityCode;
    }

    public String getEthnicityDescription() {
        return ethnicityDescription;
    }

    public void setEthnicityDescription(String ethnicityDescription) {
        this.ethnicityDescription = ethnicityDescription;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getMailingCity() {
        return mailingCity;
    }

    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    public String getMailingState() {
        return mailingState;
    }

    public void setMailingState(String mailingState) {
        this.mailingState = mailingState;
    }

    public String getMailingZipCode() {
        return mailingZipCode;
    }

    public void setMailingZipCode(String mailingZipCode) {
        this.mailingZipCode = mailingZipCode;
    }

    public String getMailingZipCodeExtension() {
        return mailingZipCodeExtension;
    }

    public void setMailingZipCodeExtension(String mailingZipCodeExtension) {
        this.mailingZipCodeExtension = mailingZipCodeExtension;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getResidenceCity() {
        return residenceCity;
    }

    public void setResidenceCity(String residenceCity) {
        this.residenceCity = residenceCity;
    }

    public String getResidenceState() {
        return residenceState;
    }

    public void setResidenceState(String residenceState) {
        this.residenceState = residenceState;
    }

    public String getResidenceZipCode() {
        return residenceZipCode;
    }

    public void setResidenceZipCode(String residenceZipCode) {
        this.residenceZipCode = residenceZipCode;
    }

    public String getResidenceZipCodeExtension() {
        return residenceZipCodeExtension;
    }

    public void setResidenceZipCodeExtension(String residenceZipCodeExtension) {
        this.residenceZipCodeExtension = residenceZipCodeExtension;
    }

    public String getNameOfParentGuardian() {
        return nameOfParentGuardian;
    }

    public void setNameOfParentGuardian(String nameOfParentGuardian) {
        this.nameOfParentGuardian = nameOfParentGuardian;
    }

    public String getNameOfFather() {
        return nameOfFather;
    }

    public void setNameOfFather(String nameOfFather) {
        this.nameOfFather = nameOfFather;
    }

    public String getNameOfMother() {
        return nameOfMother;
    }

    public void setNameOfMother(String nameOfMother) {
        this.nameOfMother = nameOfMother;
    }

    public String getNameOfCounselor() {
        return nameOfCounselor;
    }

    public void setNameOfCounselor(String nameOfCounselor) {
        this.nameOfCounselor = nameOfCounselor;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getStudentMobilePhone() {
        return studentMobilePhone;
    }

    public void setStudentMobilePhone(String studentMobilePhone) {
        this.studentMobilePhone = studentMobilePhone;
    }

    public String getFatherWorkPhone() {
        return fatherWorkPhone;
    }

    public void setFatherWorkPhone(String fatherWorkPhone) {
        this.fatherWorkPhone = fatherWorkPhone;
    }

    public String getFatherWorkPhoneExtension() {
        return fatherWorkPhoneExtension;
    }

    public void setFatherWorkPhoneExtension(String fatherWorkPhoneExtension) {
        this.fatherWorkPhoneExtension = fatherWorkPhoneExtension;
    }

    public String getMotherWorkPhone() {
        return motherWorkPhone;
    }

    public void setMotherWorkPhone(String motherWorkPhone) {
        this.motherWorkPhone = motherWorkPhone;
    }

    public String getMotherWorkPhoneExtension() {
        return motherWorkPhoneExtension;
    }

    public void setMotherWorkPhoneExtension(String motherWorkPhoneExtension) {
        this.motherWorkPhoneExtension = motherWorkPhoneExtension;
    }

    public String getStudentEmailAddress() {
        return studentEmailAddress;
    }

    public void setStudentEmailAddress(String studentEmailAddress) {
        this.studentEmailAddress = studentEmailAddress;
    }

    public String getParentGuardianEmailAddress() {
        return parentGuardianEmailAddress;
    }

    public void setParentGuardianEmailAddress(String parentGuardianEmailAddress) {
        this.parentGuardianEmailAddress = parentGuardianEmailAddress;
    }

    public String getParentGuardianEducationLevel() {
        return parentGuardianEducationLevel;
    }

    public void setParentGuardianEducationLevel(String parentGuardianEducationLevel) {
        this.parentGuardianEducationLevel = parentGuardianEducationLevel;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStatusTag() {
        return statusTag;
    }

    public void setStatusTag(String statusTag) {
        this.statusTag = statusTag;
    }

    public String getLockerID() {
        return lockerID;
    }

    public void setLockerID(String lockerID) {
        this.lockerID = lockerID;
    }

    public boolean isAddressVerified() {
        return isAddressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        isAddressVerified = addressVerified;
    }

    public String getDateOfNameAndAddressVerification() {
        return dateOfNameAndAddressVerification;
    }

    public void setDateOfNameAndAddressVerification(String dateOfNameAndAddressVerification) {
        this.dateOfNameAndAddressVerification = dateOfNameAndAddressVerification;
    }

    public String getNotAuthorizedToReleaseStudentRecords() {
        return notAuthorizedToReleaseStudentRecords;
    }

    public void setNotAuthorizedToReleaseStudentRecords(String notAuthorizedToReleaseStudentRecords) {
        this.notAuthorizedToReleaseStudentRecords = notAuthorizedToReleaseStudentRecords;
    }

    public String getCorrespondenceLanguage() {
        return correspondenceLanguage;
    }

    public void setCorrespondenceLanguage(String correspondenceLanguage) {
        this.correspondenceLanguage = correspondenceLanguage;
    }

    public String getHomeLanguage() {
        return homeLanguage;
    }

    public void setHomeLanguage(String homeLanguage) {
        this.homeLanguage = homeLanguage;
    }

    public String getFluencyLanguage() {
        return fluencyLanguage;
    }

    public void setFluencyLanguage(String fluencyLanguage) {
        this.fluencyLanguage = fluencyLanguage;
    }

    public String getUserCode1Title() {
        return userCode1Title;
    }

    public void setUserCode1Title(String userCode1Title) {
        this.userCode1Title = userCode1Title;
    }

    public String getUserCode1() {
        return userCode1;
    }

    public void setUserCode1(String userCode1) {
        this.userCode1 = userCode1;
    }

    public String getUserCode1Description() {
        return userCode1Description;
    }

    public void setUserCode1Description(String userCode1Description) {
        this.userCode1Description = userCode1Description;
    }

    public String getUserCode2Title() {
        return userCode2Title;
    }

    public void setUserCode2Title(String userCode2Title) {
        this.userCode2Title = userCode2Title;
    }

    public String getUserCode2() {
        return userCode2;
    }

    public void setUserCode2(String userCode2) {
        this.userCode2 = userCode2;
    }

    public String getUserCode2Description() {
        return userCode2Description;
    }

    public void setUserCode2Description(String userCode2Description) {
        this.userCode2Description = userCode2Description;
    }

    public String getUserCode3Title() {
        return userCode3Title;
    }

    public void setUserCode3Title(String userCode3Title) {
        this.userCode3Title = userCode3Title;
    }

    public String getUserCode3() {
        return userCode3;
    }

    public void setUserCode3(String userCode3) {
        this.userCode3 = userCode3;
    }

    public String getUserCode3Description() {
        return userCode3Description;
    }

    public void setUserCode3Description(String userCode3Description) {
        this.userCode3Description = userCode3Description;
    }

    public String getUserCode4Title() {
        return userCode4Title;
    }

    public void setUserCode4Title(String userCode4Title) {
        this.userCode4Title = userCode4Title;
    }

    public String getUserCode4() {
        return userCode4;
    }

    public void setUserCode4(String userCode4) {
        this.userCode4 = userCode4;
    }

    public String getUserCode4Description() {
        return userCode4Description;
    }

    public void setUserCode4Description(String userCode4Description) {
        this.userCode4Description = userCode4Description;
    }

    public String getUserCode5Title() {
        return userCode5Title;
    }

    public void setUserCode5Title(String userCode5Title) {
        this.userCode5Title = userCode5Title;
    }

    public String getUserCode5() {
        return userCode5;
    }

    public void setUserCode5(String userCode5) {
        this.userCode5 = userCode5;
    }

    public String getUserCode5Description() {
        return userCode5Description;
    }

    public void setUserCode5Description(String userCode5Description) {
        this.userCode5Description = userCode5Description;
    }

    public String getUserCode6Title() {
        return userCode6Title;
    }

    public void setUserCode6Title(String userCode6Title) {
        this.userCode6Title = userCode6Title;
    }

    public String getUserCode6() {
        return userCode6;
    }

    public void setUserCode6(String userCode6) {
        this.userCode6 = userCode6;
    }

    public String getUserCode6Description() {
        return userCode6Description;
    }

    public void setUserCode6Description(String userCode6Description) {
        this.userCode6Description = userCode6Description;
    }

    public String getUserCode7Title() {
        return userCode7Title;
    }

    public void setUserCode7Title(String userCode7Title) {
        this.userCode7Title = userCode7Title;
    }

    public String getUserCode7() {
        return userCode7;
    }

    public void setUserCode7(String userCode7) {
        this.userCode7 = userCode7;
    }

    public String getUserCode7Description() {
        return userCode7Description;
    }

    public void setUserCode7Description(String userCode7Description) {
        this.userCode7Description = userCode7Description;
    }

    public String getUserCode8Title() {
        return userCode8Title;
    }

    public void setUserCode8Title(String userCode8Title) {
        this.userCode8Title = userCode8Title;
    }

    public String getUserCode8() {
        return userCode8;
    }

    public void setUserCode8(String userCode8) {
        this.userCode8 = userCode8;
    }

    public String getUserCode8Description() {
        return userCode8Description;
    }

    public void setUserCode8Description(String userCode8Description) {
        this.userCode8Description = userCode8Description;
    }

    public String getUserCode9Title() {
        return userCode9Title;
    }

    public void setUserCode9Title(String userCode9Title) {
        this.userCode9Title = userCode9Title;
    }

    public String getUserCode9() {
        return userCode9;
    }

    public void setUserCode9(String userCode9) {
        this.userCode9 = userCode9;
    }

    public String getUserCode9Description() {
        return userCode9Description;
    }

    public void setUserCode9Description(String userCode9Description) {
        this.userCode9Description = userCode9Description;
    }

    public String getUserCode10Title() {
        return userCode10Title;
    }

    public void setUserCode10Title(String userCode10Title) {
        this.userCode10Title = userCode10Title;
    }

    public String getUserCode10() {
        return userCode10;
    }

    public void setUserCode10(String userCode10) {
        this.userCode10 = userCode10;
    }

    public String getUserCode10Description() {
        return userCode10Description;
    }

    public void setUserCode10Description(String userCode10Description) {
        this.userCode10Description = userCode10Description;
    }

    public String getUserCode11Title() {
        return userCode11Title;
    }

    public void setUserCode11Title(String userCode11Title) {
        this.userCode11Title = userCode11Title;
    }

    public String getUserCode11() {
        return userCode11;
    }

    public void setUserCode11(String userCode11) {
        this.userCode11 = userCode11;
    }

    public String getUserCode11Description() {
        return userCode11Description;
    }

    public void setUserCode11Description(String userCode11Description) {
        this.userCode11Description = userCode11Description;
    }

    public String getUserCode12Title() {
        return userCode12Title;
    }

    public void setUserCode12Title(String userCode12Title) {
        this.userCode12Title = userCode12Title;
    }

    public String getUserCode12() {
        return userCode12;
    }

    public void setUserCode12(String userCode12) {
        this.userCode12 = userCode12;
    }

    public String getUserCode12Description() {
        return userCode12Description;
    }

    public void setUserCode12Description(String userCode12Description) {
        this.userCode12Description = userCode12Description;
    }

    public String getUserCode13Title() {
        return userCode13Title;
    }

    public void setUserCode13Title(String userCode13Title) {
        this.userCode13Title = userCode13Title;
    }

    public String getUserCode13() {
        return userCode13;
    }

    public void setUserCode13(String userCode13) {
        this.userCode13 = userCode13;
    }

    public String getUserCode13Description() {
        return userCode13Description;
    }

    public void setUserCode13Description(String userCode13Description) {
        this.userCode13Description = userCode13Description;
    }

    @ColumnInfo(name = "userCode13")
    private String userCode13;

    @ColumnInfo(name = "userCode13Description")
    private String userCode13Description;

}
