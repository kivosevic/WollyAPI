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

    @Query(nativeQuery = true, value = "select sum(amount),w.cryptocurrency_id,c.icon,c.name,c.abbreviation,c.value_of_one from wallet_item w inner join cryptocurrency c on w.cryptocurrency_id= c.id where w.wallet_uuid=:uuid group by w.cryptocurrency_id,c.icon,c.name,c.abbreviation,c.value_of_one;")
    public List<WalletItemDao> getSumOfCryptocurrency(@Param("uuid") String uuid);
}
