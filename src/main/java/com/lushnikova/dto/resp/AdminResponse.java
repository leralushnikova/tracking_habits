package com.lushnikova.dto.resp;

import java.util.Objects;
import java.util.UUID;

public class AdminResponse {
    private UUID id;
    private String email;
    private String password;

    public AdminResponse() {
    }

    public AdminResponse(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminResponse admin)) return false;
        return Objects.equals(id, admin.id) && Objects.equals(email, admin.email) && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
