package ru.nyrk.gisgmp.database.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nyrk.gisgmp.database.entity.MessagesEntity;
import ru.nyrk.gisgmp.database.reposit.MessagesRepository;
import ru.nyrk.gisgmp.util.kendo.DataSourceRequest;
import ru.nyrk.gisgmp.util.kendo.DataSourceResult;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("messagesService")
@Repository
@Transactional
public class MessagesServiceImpl implements MessagesService {

    @Qualifier("messagesRepository")
    @Autowired
    private MessagesRepository messagesRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public MessagesEntity save(MessagesEntity messagesEntity) {
        return messagesRepository.save(messagesEntity);
    }


    @Override
    public List<MessagesEntity> findByOutboxFolder() {
        return messagesRepository.findByFoldersEntity_FolderKdOrderByMesIdAsc("outbox");
    }

    @Override
    public DataSourceResult getListDataSource(DataSourceRequest request) {
        return request.toDataSourceResult((Session) em.getDelegate(), MessagesEntity.class);
    }

    @Override
    public void lock(MessagesEntity messagesEntity) {
        em.lock(messagesEntity, LockModeType.OPTIMISTIC);
    }

    @Override
    public MessagesEntity findOne(Long id) {
        return messagesRepository.findOne(id);
    }
}
