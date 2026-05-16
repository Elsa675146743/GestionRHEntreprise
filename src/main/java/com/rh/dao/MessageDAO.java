package com.rh.dao;

import com.rh.model.Message;
import java.util.List;

public interface MessageDAO {
    void create(Message message);
    Message read(Long id);
    void update(Message message);
    void delete(Long id);
    List<Message> findByDestinataire(Long destinataireId);
    List<Message> findByExpediteur(Long expediteurId);
    List<Message> findNonLusParDestinataire(Long destinataireId);
    void marquerCommeLu(Long id);
    int countNonLus(Long destinataireId);
}