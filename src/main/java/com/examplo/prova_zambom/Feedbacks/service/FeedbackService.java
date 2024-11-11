package com.examplo.prova_zambom.Feedbacks.service;

import com.examplo.prova_zambom.Feedbacks.dto.RetornarUsuarioDTO;
import com.examplo.prova_zambom.Feedbacks.model.Feedback;
import com.examplo.prova_zambom.Feedbacks.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback createFeedback(String token, Feedback feedback) {
        RetornarUsuarioDTO user = validateTokenAndSetUser(token);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }

        if (!"ADMIN".equalsIgnoreCase(user.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks(String token) {
        RetornarUsuarioDTO user = validateTokenAndSetUser(token);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }

        if (!"ADMIN".equalsIgnoreCase(user.getPapel()) && !"DEVELOPER".equalsIgnoreCase(user.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(String token, String id) {
        RetornarUsuarioDTO user = validateTokenAndSetUser(token);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }

        if (!"ADMIN".equalsIgnoreCase(user.getPapel()) && !"DEVELOPER".equalsIgnoreCase(user.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback não encontrado"));
    }

    public void deleteFeedback(String token, String id) {
        RetornarUsuarioDTO user = validateTokenAndSetUser(token);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }

        if (!"ADMIN".equalsIgnoreCase(user.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        Optional<Feedback> feedback = feedbackRepository.findById(id);

        if (feedback.isPresent()) {
            feedbackRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback não encontrado");
        }
    }

    public RetornarUsuarioDTO validateTokenAndSetUser(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<RetornarUsuarioDTO> response = restTemplate.exchange(
                    "http://184.72.80.215/usuario/validate",
                    HttpMethod.GET,
                    entity,
                    RetornarUsuarioDTO.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                RetornarUsuarioDTO usuarioDTO = response.getBody();


                if (usuarioDTO != null) {
                    return usuarioDTO;
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido.");
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido.");
        }
    }
}
