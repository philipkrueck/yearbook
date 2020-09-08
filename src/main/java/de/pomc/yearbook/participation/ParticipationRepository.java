package de.pomc.yearbook.participation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findParticipationByParticipant_Email(String email);

}
