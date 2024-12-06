package com.wms.wms.service;

import java.util.List;

/**
 * A generic interface for defining common CRUD operations for services.
 * This interface can be implemented by specific services to provide
 * standardized methods for handling requests and responses.
 *
 * @param <DtoRequest>  The type of the data transfer object (DTO) used for requests (e.g., for creating or updating an entity).
 * @param <DtoResponse> The type of the data transfer object (DTO) used for responses (e.g., for returning data to the client).
 */
public interface BaseService <DtoRequest, DtoResponse>{
    /**
     * Retrieves a list of all entities and maps them to response DTOs.
     *
     * @return A list of response DTOs representing all entities.
     */
    List<DtoResponse> findAll();

    /**
     * Retrieves an entity by its unique identifier and maps it to a response DTO.
     *
     * @return The response DTO representing the entity with the given ID.
     * @throws RuntimeException if the entity with the specified ID is not found.
     */
    DtoResponse findById(Long entityId);

    /**
     * Saves or updates an entity based on the given request DTO.
     *
     * @param request The request DTO containing the data for the entity to be saved or updated.
     * @return The response DTO representing the saved or updated entity.
     */
    DtoResponse save(DtoRequest request);

    /**
     * Deletes an entity identified by its unique ID.
     *
     * @param id The unique identifier of the entity to be deleted.
     * @throws RuntimeException if the entity with the specified ID is not found.
     */
    void deleteById(Long id);
}
