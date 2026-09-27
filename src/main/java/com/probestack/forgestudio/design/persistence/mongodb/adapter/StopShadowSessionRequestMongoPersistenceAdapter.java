package com.probestack.forgestudio.design.persistence.mongodb.adapter;

import com.probestack.forgestudio.design.domain.repository.StopShadowSessionRequestDomainRepository;
import com.probestack.forgestudio.design.model.StopShadowSessionRequest;
import com.probestack.forgestudio.design.persistence.mongodb.document.StopShadowSessionRequestDocument;
import com.probestack.forgestudio.design.persistence.mongodb.repository.StopShadowSessionRequestMongoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class StopShadowSessionRequestMongoPersistenceAdapter implements StopShadowSessionRequestDomainRepository {
    private final StopShadowSessionRequestMongoRepository repository;

    public StopShadowSessionRequestMongoPersistenceAdapter(
            StopShadowSessionRequestMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public StopShadowSessionRequest save(StopShadowSessionRequest stopShadowSessionRequest) {
        StopShadowSessionRequestDocument document = toDocument(stopShadowSessionRequest);
        return toDomain(repository.save(document));
    }

    @Override
    public Optional<StopShadowSessionRequest> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<StopShadowSessionRequest> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private StopShadowSessionRequestDocument toDocument(
            StopShadowSessionRequest stopShadowSessionRequest) {
        StopShadowSessionRequestDocument document = new StopShadowSessionRequestDocument();
        BeanUtils.copyProperties(stopShadowSessionRequest, document);
        return document;
    }

    private StopShadowSessionRequest toDomain(StopShadowSessionRequestDocument document) {
        StopShadowSessionRequest domain = new StopShadowSessionRequest();
        BeanUtils.copyProperties(document, domain);
        return domain;
    }
}
