package com.example.auth_service.entity;

import com.example.auth_service.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 200)
    private String username;

    @Column(name = "password", length = 115)
    private String password;

    @Column(name = "psw_last_update", nullable = false)
    private Timestamp pswLastUpdate;

    @ManyToOne
    @JoinColumn(name = "os_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_os_id_fkey"))
    private OrganizationSide organizationSide;

    @ManyToOne
    @JoinColumn(name = "aus_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_aus_id_fkey"))
    private AppUserStatus appUserStatus;

    @ManyToOne
    @JoinColumn(name = "aur_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_aur_id_fkey"))
    private AppUserRole appUserRole;

    @Column(name = "login_attempts", columnDefinition = "int default 0")
    private int loginAttempts;

    @Column(name = "last_login_at")
    private Timestamp lastLoginAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;


    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AppUserPermissionMapping> permissionMappings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add user's role as a SimpleGrantedAuthority
        authorities.add(new SimpleGrantedAuthority(Constants.GRANTED_AUTHORITY_FIRST_KEY + appUserRole.getKey().toUpperCase()));

        // Add user's permissions
        authorities.addAll(permissionMappings.stream()
                .map(permissions -> new SimpleGrantedAuthority(
                        Constants.GRANTED_AUTHORITY_SECOND_KEY +
                                permissions.getAppUserPermission().getKey().toLowerCase()))
                .toList());

        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return appUserStatus.getKey().equals("ACTIVATED");
    }
}
