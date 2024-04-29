package com.devsplan.ketchup.mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Entity
@Table(name = "tbl_receiver")
@Getter
@Setter
@ToString
public class Receiver {
    @Id
    @Column(name = "RECEIVER_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receiverNo;

//    @ManyToOne
//    @JoinColumn(name = "Recivers", nullable = false)
    @Column(name = "MAIL_NO")
    private int mailNo;

    @Column(name = "RECEIVER_MEM", nullable = false)
    private int receiverMem;

    @Column(name = "READ_TIME")
    private Timestamp readTime;

    @Column(name = "RECEIVER_DEL_STATUS", nullable = false)
    private char receiverDelStatus;

    public Receiver() {}

    public Receiver(int receiverNo, int mailNo, int receiverMem, Timestamp readTime, char receiverDelStatus) {
        this.receiverNo = receiverNo;
        this.mailNo = mailNo;
        this.receiverMem = receiverMem;
        this.readTime = readTime;
        this.receiverDelStatus = receiverDelStatus;
    }
}
