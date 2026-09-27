package com.probestack.forgestudio.design.persistence.mongodb.repository;

import com.probestack.forgestudio.design.persistence.mongodb.document.StopShadowSessionRequestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for StopShadowSessionRequest documents.
 */
public interface StopShadowSessionRequestMongoRepository extends MongoRepository<StopShadowSessionRequestDocument, String> {
}
