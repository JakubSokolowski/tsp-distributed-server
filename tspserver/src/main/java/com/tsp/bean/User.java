package com.tsp.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsp.validation.RegistrationValidateGroup;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User implements Serializable{
    @Id
    @NotBlank(groups = {RegistrationValidateGroup.class},message = "Nazwa użytkownika jest wymagana!")
    @NotEmpty(groups = {RegistrationValidateGroup.class},message = "Nazwa użytkownika jest wymagana!")
    @Size(groups = {RegistrationValidateGroup.class},min = 4,max = 20,message = "Nazwa użytkownika musi mieć długość od 4 do 20 znaków!")
    private String username;

    @NotBlank(groups = {RegistrationValidateGroup.class},message = "Hasło jest wymagane!")
    @NotEmpty(groups = {RegistrationValidateGroup.class},message = "Hasło jest wymagane!")
    @Size(groups = {RegistrationValidateGroup.class},min = 4,max = 20,message = "Hasło musi mieć długość od 4 do 20 znaków!")
    private String password;

    @AssertTrue(groups = {RegistrationValidateGroup.class},message = "Użytkownik musi być aktywny!")
    private Boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(enabled, user.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
