package com.aristotle.core.service;

import com.aristotle.core.persistance.Election;

public interface ElectionService {

    Election saveElection(Election election);

    Election findElectionById(Long electionId);
}
