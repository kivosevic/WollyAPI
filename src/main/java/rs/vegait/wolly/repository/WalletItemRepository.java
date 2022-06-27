package rs.vegait.wolly.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.vegait.wolly.dao.WalletItemDao;
import rs.vegait.wolly.models.WalletItem;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, UUID> {

    @Query(nativeQuery = true, value = "select w.cryptocurrency_id,sum(amount) from wallet_item w where w.wallet_uuid=:uuid group by w.cryptocurrency_id;")
    public List<WalletItemDao> getSumOfCryptocurrency(@Param("uuid") String uuid);
}
