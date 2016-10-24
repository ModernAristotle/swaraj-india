package com.aristotle.core.service.impl;

import com.aristotle.core.persistance.Election;
import com.aristotle.core.persistance.repo.ElectionRepository;
import com.aristotle.core.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.aristotle.core.util.ValidationUtil.*;

@Service
@Transactional(rollbackFor = {Throwable.class})
public class ElectionServiceImpl implements ElectionService {
    @Autowired
    private ElectionRepository electionRepository;


    @Override
    public Election saveElection(Election election) {
        assertNotBlank(election.getTitle(), "Election title can not be blank");
        assertNotNull(election.getLocation(), "Election Location can not be null");
        assertNotNull(election.getLocationType(), "Election Location type can not be null");
        assertNotNull(election.getStartDate(), "Election start date can not be null");
        assertNotNull(election.getEndDate(), "Election end date can not be null");
        assertNotNull(election.getCandidatureFilingDate(), "Election Candidature filing date can not be null");
        assertBefore(election.getCandidatureFilingDate(), election.getStartDate(), "Election Candidature date must be before Election Start Date");
        assertBefore(election.getStartDate(), election.getEndDate(), "Election start date must be before Election End Date");

        election = electionRepository.save(election);
        return election;
    }

    @Override
    public Election findElectionById(Long electionId) {
        return electionRepository.findOne(electionId);
    }
}
