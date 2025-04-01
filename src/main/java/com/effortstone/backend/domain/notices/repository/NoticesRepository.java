package com.effortstone.backend.domain.notices.repository;

import com.effortstone.backend.domain.notices.entity.Notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticesRepository extends JpaRepository<Notices, Long> {
}
