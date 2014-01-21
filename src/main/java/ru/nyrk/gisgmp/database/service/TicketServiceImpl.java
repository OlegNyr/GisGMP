package ru.nyrk.gisgmp.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nyrk.gisgmp.database.entity.TicketEntity;
import ru.nyrk.gisgmp.database.reposit.TicketRepository;

import java.util.List;

@Service("ticketService")
@Repository
@Transactional
public class TicketServiceImpl implements TicketService {


    @Qualifier("ticketRepository")
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public TicketEntity save(TicketEntity ticketEntity) {
        return ticketRepository.save(ticketEntity);
    }

    @Override
    public List<TicketEntity> findByMesIdOrderByTicketDtAsc(Long messId) {
    return null;
    //    return ticketRepository.findByMesIdOrderByTicketDtAsc(messId);
    }
}
