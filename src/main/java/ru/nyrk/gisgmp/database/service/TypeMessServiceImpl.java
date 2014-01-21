package ru.nyrk.gisgmp.database.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nyrk.gisgmp.database.entity.TypeMessEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service("typeMessService")
@Repository
@Transactional
public class TypeMessServiceImpl implements TypeMessService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public TypeMessEntity getReference(String name) {
        return em.getReference(TypeMessEntity.class, name);
    }
}
