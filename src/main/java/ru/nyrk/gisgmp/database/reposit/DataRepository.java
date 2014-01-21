package ru.nyrk.gisgmp.database.reposit;


import org.springframework.data.repository.CrudRepository;
import ru.nyrk.gisgmp.database.entity.DataEntity;

public interface DataRepository extends CrudRepository<DataEntity, Long>{

}
