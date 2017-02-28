package org.openpkw.repositories;

import org.openpkw.model.entity.PasswordChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordChangeRequestRepository extends JpaRepository<PasswordChangeRequest, Long> {

    List<PasswordChangeRequest> findByUser_Email(String email);

    Optional<PasswordChangeRequest> findByUser_EmailAndActiveTrue(String email);

    Optional<PasswordChangeRequest> findByTokenAndActiveTrue(String token);
}
