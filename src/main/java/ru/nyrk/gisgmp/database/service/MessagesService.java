package ru.nyrk.gisgmp.database.service;

import ru.nyrk.gisgmp.database.entity.MessagesEntity;
import ru.nyrk.gisgmp.util.kendo.DataSourceRequest;
import ru.nyrk.gisgmp.util.kendo.DataSourceResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 20.12.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public interface MessagesService {
    MessagesEntity save(MessagesEntity messagesEntity);

    void lock(MessagesEntity messagesEntity);

    List<MessagesEntity> findByOutboxFolder();

    DataSourceResult getListDataSource(DataSourceRequest request);

    MessagesEntity findOne(Long id);

}
