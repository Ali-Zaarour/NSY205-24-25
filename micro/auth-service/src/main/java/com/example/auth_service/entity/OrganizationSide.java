package com.example.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_side")
public class OrganizationSide {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "key", nullable = false, unique = true, length = 50)
    private String Key;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "os_parent_id", foreignKey = @ForeignKey(name = "organization_side_self_fkey"))
    private OrganizationSide parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<OrganizationSide> children;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

}
