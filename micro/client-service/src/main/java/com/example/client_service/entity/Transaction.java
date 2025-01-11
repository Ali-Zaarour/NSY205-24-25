package com.example.client_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "transaction_account_id_fkey"))
    private Account account;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false, foreignKey = @ForeignKey(name = "transaction_transaction_type_id_fkey"))
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(name = "transaction_status_id_fkey"))
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;
}