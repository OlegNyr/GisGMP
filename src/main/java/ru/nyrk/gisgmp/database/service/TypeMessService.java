package ru.nyrk.gisgmp.database.service;

import ru.nyrk.gisgmp.database.entity.TypeMessEntity;

public interface TypeMessService {
    TypeMessEntity getReference(String name);
}
