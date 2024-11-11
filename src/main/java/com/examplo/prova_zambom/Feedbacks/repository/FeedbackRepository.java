package com.examplo.prova_zambom.Feedbacks.repository;

import com.examplo.prova_zambom.Feedbacks.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}