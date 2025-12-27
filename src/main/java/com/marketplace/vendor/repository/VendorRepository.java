package com.marketplace.vendor.repository;

import com.marketplace.vendor.entity.Vendor;
import com.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	// Find vendor by owning user
	Optional<Vendor> findByUser(User user);

	// Check if a vendor already exists for a user
	boolean existsByUser(User user);
}
