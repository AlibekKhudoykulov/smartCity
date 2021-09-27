package com.example.smartcity.Entity;

import com.example.smartcity.Entity.Template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Collection;
import java.util.Set;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbsEntity implements UserDetails {

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String phoneNumber;

    private Long cardNumber;

    private String email;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    @ManyToMany
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String username, String password, String phoneNumber, Long cardNumber, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        this.email = email;
        this.roles = roles;
    }

    public User(String username, String password, String phoneNumber, Long cardNumber, String email, boolean enabled, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }
}
