package ru.nyrk.gisgmp.database.service;

import ru.nyrk.gisgmp.database.entity.FoldersEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 20.12.13
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public interface FoldersService {
    FoldersEntity getReference(String name);

    FoldersEntity findOne(String name);

    List<FoldersEntity> findAll();
}
