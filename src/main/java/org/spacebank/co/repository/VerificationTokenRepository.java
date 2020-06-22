package org.spacebank.co.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.spacebank.co.models.User;
import org.spacebank.co.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	public VerificationToken findByToken(String token);

	public VerificationToken findByUser(User user);

	public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

	public void deleteByExpiryDateLessThan(Date now);

	@Modifying
	@Query("delete from VerificationToken t where t.expiryDate <= ?1")
	public void deleteAllExpiredSince(Date now);
}
