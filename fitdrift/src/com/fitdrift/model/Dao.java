package com.fitdrift.model;

public interface Dao<E> {
	void persist(E entity);
    void remove(E entity);
    E findById(Long id);
}
