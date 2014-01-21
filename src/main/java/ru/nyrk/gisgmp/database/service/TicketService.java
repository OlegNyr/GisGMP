package ru.nyrk.gisgmp.database.service;

import ru.nyrk.gisgmp.database.entity.TicketEntity;

import java.util.List;


public interface TicketService {
    TicketEntity save(TicketEntity ticketEntity);

    List<TicketEntity> findByMesIdOrderByTicketDtAsc(Long messId);
}
