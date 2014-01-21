package ru.nyrk.gisgmp.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nyrk.gisgmp.database.entity.DataEntity;
import ru.nyrk.gisgmp.database.reposit.DataRepository;


@Service("dataService")
@Repository
@Transactional
public class DataServiceImpl implements DataService {

    @Qualifier("dataRepository")
    @Autowired
    private DataRepository dataRepository;

    @Override
    public DataEntity save(DataEntity dataEntity) {
        return dataRepository.save(dataEntity);
    }
}
