package com.example.bankmanagesystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tx_id", unique = true, nullable = false, length = 50)
    private String txId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account", referencedColumnName = "account_number")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account", referencedColumnName = "account_number")
    private Account toAccount;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String type; // deposit/withdraw/transfer

    @Column(length = 20)
    private String status = "PENDING"; // SUCCESS/FAIL/PENDING

    @Column(name = "created_time")
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(length = 500)
    private String remark;

    @PrePersist
    public void prePersist() {
        if (this.createdTime == null) {
            this.createdTime = LocalDateTime.now();
        }
    }


    // 可选：toString 方法（避免循环引用）
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", txId='" + txId + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", createdTime=" + createdTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
