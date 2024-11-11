package com.examplo.prova_zambom.Feedbacks.controller;

import com.examplo.prova_zambom.Feedbacks.model.Feedback;
import com.examplo.prova_zambom.Feedbacks.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Feedback createFeedback(@RequestHeader("Authorization") String token,
                                   @RequestBody Feedback feedback) {
        return feedbackService.createFeedback(token, feedback);
    }

    @GetMapping
    public List<Feedback> getAllFeedbacks(@RequestHeader("Authorization") String token) {
        return feedbackService.getAllFeedbacks(token);
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@RequestHeader("Authorization") String token,
                                    @PathVariable String id) {
        return feedbackService.getFeedbackById(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@RequestHeader("Authorization") String token,
                               @PathVariable String id) {
        feedbackService.deleteFeedback(token, id);
    }
}