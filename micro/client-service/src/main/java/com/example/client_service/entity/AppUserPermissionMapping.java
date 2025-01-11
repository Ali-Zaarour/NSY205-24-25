package com.example.client_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user_permission_mapping")
public class AppUserPermissionMapping {

    @EmbeddedId
    private AppUserPermissionMappingId id;

    @ManyToOne
    @MapsId("appUserId")
    @JoinColumn(name = "au_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_au_id_fkey"))
    private AppUser appUser;

    @ManyToOne
    @MapsId("appUserPermissionId")
    @JoinColumn(name = "aup_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_aup_id_fkey"))
    private AppUserPermission appUserPermission;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_created_by_fkey"))
    private AppUser createdBy;

}
