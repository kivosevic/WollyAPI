package com.backend.backend.repository;

import com.backend.backend.models.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, UUID> {
}
