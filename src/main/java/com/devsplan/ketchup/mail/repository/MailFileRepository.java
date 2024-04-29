package com.devsplan.ketchup.mail.repository;

import com.devsplan.ketchup.mail.entity.MailFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailFileRepository extends JpaRepository<MailFile, Integer> {
}
