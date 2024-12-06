package com.wms.wms.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper
 *
 * @param <D> DTO type parameter
 * @param <E> Entity type paramerter
 */

public interface BaseMapper<D, E> {
    D toDto (E entity);

    E toEntity (D dto);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);
}
