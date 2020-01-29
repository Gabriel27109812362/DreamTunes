package builders;

import android.util.Base64;

import models.User;

public class UserBuilder implements Builder<User> {


    private String name;
    private String surname;
    private String email;
    private String password;
    private Base64 photo;


    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        // Todo : add encryption
        this.password = password;
        return this;
    }

    public UserBuilder photo(Base64 photo) {
        if (photo != null) {
            this.photo = photo;
        }
        return this;
    }

    @Override
    public User build() {
        User user = new User();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setPassword(this.password);
        if (this.photo != null) {
            user.setPhoto(this.photo);
        }
        return user;

    }
}
