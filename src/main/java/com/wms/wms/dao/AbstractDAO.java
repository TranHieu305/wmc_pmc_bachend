package com.wms.wms.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class AbstractDAO<T> {

    protected EntityManager entityManager;

    public AbstractDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     Retrieves an entity of the given type by its ID from the database.
     @param clazz The class type of the entity to retrieve.
     @param id The ID of the entity to retrieve.
     @return The entity retrieved from the database, or null if no entity with the specified ID is found.
     */
    public T findByID(Class<T> clazz, int id) {
        return entityManager.find(clazz, id);
    }


    /**
     Retrieves all entities of the given type from the database.
     @param clazz The class type of the entities to retrieve.
     @return A list of all entities of the specified type retrieved from the database.
     */
    public List<T> findAll(Class<T> clazz) {
        String entityName = clazz.getSimpleName();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM ").append(entityName);

        TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
        return query.getResultList();
    }

    public T findOne(Class<T> clazz, String sql, Object... params) {
        return null;
    }

    /**
     Executes a parameterized query to retrieve multiple entities of the given type.
     @param clazz The class type of the entities to retrieve.
     @param sql The JPQL query string to execute.
     @param params The parameters to bind to the query.
     @return A list of entities retrieved from the database.
     */
    public List<T> findMany(Class<T> clazz, String sql, Object... params) {
        TypedQuery<T> query = entityManager.createQuery(sql, clazz);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        return query.getResultList();
    }


    /**
     Saves or updates the given entity in the database.
     @param entity the entity to be saved or updated
     @return the managed entity after it has been saved or updated
     */
    public T save(T entity) {
        return entityManager.merge(entity);
    }

    /**
     Deletes the given entity from the database.
     @param entity the entity to be deleted
     */
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
