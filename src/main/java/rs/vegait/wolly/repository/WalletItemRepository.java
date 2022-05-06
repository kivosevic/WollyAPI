package rs.vegait.wolly.repository;

import rs.vegait.wolly.models.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, UUID> {
}