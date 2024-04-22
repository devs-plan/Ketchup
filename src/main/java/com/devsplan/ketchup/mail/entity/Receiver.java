package com.devsplan.ketchup.mail.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "RECEIVER")
public class Receiver {
    @Id
    @Column(name = "RECEIVER_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receiverNo;

    @ManyToOne
    @JoinColumn(name = "MAIL_NO", nullable = false)
    private Mail mailNo;

    @Column(name = "RECEIVER_NAME", nullable = false)
    private String receiverName;

    @Column(name = "READ_TIME")
    private Timestamp readTime;

    @Column(name = "RECEIVER_DEL_STATUS", nullable = false)
    private char receiverDelStatus;

    protected Receiver() {}

    public Receiver(int receiverNo, Mail mailNo, String receiverName, Timestamp readTime, char receiverDelStatus) {
        this.receiverNo = receiverNo;
        this.mailNo = mailNo;
        this.receiverName = receiverName;
        this.readTime = readTime;
        this.receiverDelStatus = receiverDelStatus;
    }
}