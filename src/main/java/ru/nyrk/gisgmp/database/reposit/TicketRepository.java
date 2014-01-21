package ru.nyrk.gisgmp.database.reposit;

import org.springframework.data.repository.CrudRepository;
import ru.nyrk.gisgmp.database.entity.TicketEntity;

import java.util.List;

public interface TicketRepository extends CrudRepository<TicketEntity, Long>{
 //   List<TicketEntity> findByMesIdOrderByTicketDtAsc(Long messId);
}
