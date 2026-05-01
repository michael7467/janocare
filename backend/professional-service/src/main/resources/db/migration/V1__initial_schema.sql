CREATE TABLE professionals (
                               id UUID PRIMARY KEY,
                               firstName VARCHAR(100),
                               lastName VARCHAR(100),
                               approvalStatus VARCHAR(20)
);

CREATE TABLE specializations (
                                 id UUID PRIMARY KEY,
                                 name VARCHAR(100),
                                 professional_id UUID REFERENCES professionals(id)
);

CREATE TABLE availability_slots (
                                    id UUID PRIMARY KEY,
                                    startTime TIMESTAMP,
                                    endTime TIMESTAMP,
                                    professional_id UUID REFERENCES professionals(id)
);

CREATE TABLE professional_reviews (
                                      id UUID PRIMARY KEY,
                                      rating INT,
                                      comment TEXT,
                                      professional_id UUID REFERENCES professionals(id)
);
