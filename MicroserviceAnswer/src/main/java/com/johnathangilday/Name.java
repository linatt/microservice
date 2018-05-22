package com.johnathangilday;

import lombok.Value;

@Value(staticConstructor = "of")
    // Name zusammengesetzt
public class Name {
    public final String firstname;
    public final String lastname;

    public Name (String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
