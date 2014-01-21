package ru.nyrk.gisgmp.database.reposit;

import org.springframework.data.repository.CrudRepository;
import ru.nyrk.gisgmp.database.entity.LogEntity;

import java.util.List;

public interface LogRepository extends CrudRepository<LogEntity, Long> {
  public List<LogEntity> findByMesIdOrderByLogDtDesc(Long messId);
}
