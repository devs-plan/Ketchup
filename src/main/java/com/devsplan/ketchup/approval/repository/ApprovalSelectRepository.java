package com.devsplan.ketchup.approval.repository;

import com.devsplan.ketchup.approval.entity.ApprovalSelect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ApprovalSelectRepository extends JpaRepository<ApprovalSelect, Integer> {
    @Query(value = "SELECT a FROM AppSelect a WHERE a.member.memberNo = :memberNo AND a.appStatus IN (:status)")
    List<ApprovalSelect> findMyApproval(String memberNo, List<String> status);

    @Query(value = "SELECT a FROM AppSelect a WHERE a.member.memberNo = :memberNo AND a.appTitle LIKE %:searchValue% AND a.appStatus IN (:status)")
    List<ApprovalSelect> findMyApprovalWithSearch(String memberNo, List<String> status, String searchValue);

    @Query(value = "SELECT a FROM AppSelect a WHERE a.approvalNo IN :appNo AND a.appStatus = :status")
    List<ApprovalSelect> findRefApp(String status, List<Integer> appNo);

    @Query(value = "SELECT a FROM AppSelect a WHERE a.approvalNo IN :appNo AND a.appStatus = :status AND a.appTitle LIKE %:searchValue%")
    List<ApprovalSelect> findRefAppWithSearch(String status, String searchValue, List<Integer> appNo);

    @Query("select a from AppSelect a join AppLine b on (a.approvalNo = b.approvalNo) where b.memberNo = :memberNo AND a.sequence = b.alSequence AND a.appStatus IN :status")
    List<ApprovalSelect> findReceiveApp(String memberNo, List<String> status);

    @Query("select a from AppSelect a join AppLine b on (a.approvalNo = b.approvalNo) where b.memberNo = :memberNo AND a.sequence = b.alSequence AND a.appStatus IN :status AND a.appTitle LIKE %:searchValue%")
    List<ApprovalSelect> findReceiveAppWithSearch(String memberNo, List<String> status, String searchValue);



}
