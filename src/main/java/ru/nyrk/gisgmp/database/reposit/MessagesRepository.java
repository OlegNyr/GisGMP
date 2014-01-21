package ru.nyrk.gisgmp.database.reposit;

import org.springframework.data.repository.CrudRepository;
import ru.nyrk.gisgmp.database.entity.MessagesEntity;

import java.util.List;

public interface MessagesRepository extends CrudRepository<MessagesEntity, Long> {
    public List<MessagesEntity> findByFoldersEntity_FolderKdOrderByMesIdAsc(String folderKd);
}
