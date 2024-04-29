package com.devsplan.ketchup.mail.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class ReceiverDTO {
    private int receiverNo;             // 수신자 번호
    private int mailNo;                 // 메일 번호
    private int receiverMem;            // 수신자 사원 번호
    private Timestamp readTime;         // 메일 읽은 시간
    private char receiverDelStatus;     // 수신 메일 삭제 여부

    public ReceiverDTO() {
    }

    public ReceiverDTO(int mailNo, int receiverMem, Timestamp readTime, char receiverDelStatus) {
        this.mailNo = mailNo;
        this.receiverMem = receiverMem;
        this.readTime = readTime;
        this.receiverDelStatus = receiverDelStatus;
    }

    public ReceiverDTO(int receiverNo, int mailNo, int receiverMem, Timestamp readTime, char receiverDelStatus) {
        this.receiverNo = receiverNo;
        this.mailNo = mailNo;
        this.receiverMem = receiverMem;
        this.readTime = readTime;
        this.receiverDelStatus = receiverDelStatus;
    }
}

