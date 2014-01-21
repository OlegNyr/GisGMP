package ru.nyrk.gisgmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.nyrk.gisgmp.core.mess.MessageExecuttor;
import ru.nyrk.gisgmp.database.entity.MessagesEntity;
import ru.nyrk.gisgmp.database.service.MessagesService;
import ru.nyrk.util.DateTry;
import ru.nyrk.util.ThreadInterationCustom;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Component
public class SenderIteration extends ThreadInterationCustom {

    private static final Logger log = LoggerFactory.getLogger(SenderIteration.class);
    @Qualifier("messagesService")
    @Autowired
    private MessagesService messagesService;
    @Qualifier("messageExecuttor")
    @Autowired
    private MessageExecuttor messageExecuttor;


    @PersistenceUnit
    private volatile EntityManagerFactory emf;


    public SenderIteration() {
        setSleep(5000);
    }

    @Override
    public void execute() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityManagerHolder emHolder = new EntityManagerHolder(em);
        try {
            TransactionSynchronizationManager.bindResource(emf, emHolder);
            super.execute();
        } finally {
            EntityManagerHolder emHolderB = (EntityManagerHolder)
                    TransactionSynchronizationManager.unbindResource(emf);
            log.debug("Closing JPA EntityManager in OpenEntityManagerInViewFilter");
            EntityManagerFactoryUtils.closeEntityManager(emHolderB.getEntityManager());
        }
        sleep(DateTry.Second*10);
    }


    @Override
    public void interation() throws Exception {
        try {
            List<MessagesEntity> listMessage = messagesService.findByOutboxFolder();
            for (MessagesEntity msEnt : listMessage) {
                messageExecuttor.sendMess(msEnt);
            }
        } catch (Throwable th) {
            log.error("LOG00080:", th);
        }
    }

    @Override
    public void errorMessage(Exception e) {
        log.error("LOG00060:", e);
    }

    @PostConstruct
    public void startIteration() {
        this.start();
    }

    @PreDestroy
    public void stopIteration() {
        this.terminate();
    }


}
