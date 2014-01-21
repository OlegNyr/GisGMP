package ru.nyrk.gisgmp.database.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nyrk.gisgmp.database.entity.FoldersEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("foldersService")
@Repository
@Transactional
public class FoldersServiceImpl implements FoldersService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public FoldersEntity getReference(String name) {
        return em.getReference(FoldersEntity.class, name);
    }

    @Override
    public FoldersEntity findOne(String name) {
        return em.find(FoldersEntity.class, name);
    }

    @Override
    public List<FoldersEntity> findAll() {
        return em.createQuery("from FoldersEntity f order by f.folderKd").getResultList();
    }
}
