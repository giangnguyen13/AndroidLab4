package giang.nguyen.s301033256.models;
/**
 * Giang Nguyen
 * Student# 301033256
 * COMP304 002
 * Professor: Haki Sharifi
 * */
public class Patient {
    private long id;
    private String firstName;
    private String lastName;
    private String department;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return String.format("%s %s (id#%d)", getFirstName(), getLastName(), getId());
    }

    public String getFullName(){
        return String.format("%s %s", getFirstName(), getLastName());
    }
}
