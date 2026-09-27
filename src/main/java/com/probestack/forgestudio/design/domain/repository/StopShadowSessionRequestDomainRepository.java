package com.probestack.forgestudio.design.domain.repository;

import com.probestack.forgestudio.design.model.StopShadowSessionRequest;
import java.util.List;
import java.util.Optional;

/**
 * Persistence-neutral repository port for StopShadowSessionRequest domain operations.
 */
public interface StopShadowSessionRequestDomainRepository {
    StopShadowSessionRequest save(StopShadowSessionRequest stopShadowSessionRequest);

    Optional<StopShadowSessionRequest> findById(String id);

    List<StopShadowSessionRequest> findAll();

    boolean existsById(String id);

    void deleteById(String id);

    long count();
}
