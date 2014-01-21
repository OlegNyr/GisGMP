package ru.nyrk.gisgmp.web.security;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 10.03.13
 * Time: 21:02
 */
public class Family implements Serializable {
    String firstName;
    String lastName;
    String familyName;

    public Family(String firstName, String lastName, String familyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append(familyName).
                append(' ').
                append(firstName).
                append(' ').
                append(lastName).
                toString();
    }
}
