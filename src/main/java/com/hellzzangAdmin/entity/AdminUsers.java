package com.hellzzangAdmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

//UserService에서 설정
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsers implements UserDetails {

    @JsonIgnore
    @Id
    @Column(name = "user_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //사용자 인덱스

    @Column(name = "userid", length = 50, unique = true)
    private String userid; //로그인 아이디

    @Column(name = "password", length = 100)
    private String password; //비밀번호

    @Column(name = "username", length = 50)
    private String username; //이름

    @Column(name = "nickname", length = 50)
    private String nickname; //닉네임

    @Column(name = "address", length = 50)
    private String address; //주소

    @Column(name = "addressDetail", length = 50)
    private String addressDetail; //상세주소

    @Column(name = "phone", length = 50)
    private String phone; //핸드폰

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "admin_user_authority",
            joinColumns = {@JoinColumn(name = "user_index", referencedColumnName = "user_index")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AdminAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }
}
