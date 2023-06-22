package com.hientran.sohebox.cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class BaseCache {
	@PersistenceContext
	protected EntityManager entityManager;
}
