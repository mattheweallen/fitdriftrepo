package com.fitdrift.model;
//interface not being used. May be deleted.
public interface Dao<E> {
	void persist(E entity);
    void remove(E entity);
    E findById(Long id);
}
