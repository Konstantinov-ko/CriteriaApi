package ru.javaschool.dao.api;


public interface GenericDao<Entity, Id> {
    void create(Entity entity);

    void update(Entity entity);

    void remove(Entity entity);

    Entity get(Id key);
}
