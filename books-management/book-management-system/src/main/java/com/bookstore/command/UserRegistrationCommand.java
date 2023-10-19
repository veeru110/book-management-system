package com.bookstore.command;

import com.bookstore.constants.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;


public class UserRegistrationCommand {
    private String firstName;

    private String lastName;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "User Role is must")
    private UserRole role;

    @NotEmpty(message = "Password can't be empty")
    private String password;

    private Set<String> genresInterested;

    public Set<String> getGenresInterested() {
        return genresInterested;
    }

    public void setGenresInterested(Set<String> genresInterested) {
        this.genresInterested = genresInterested;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
