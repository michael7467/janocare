package com.janocare.auth.application.ports;


import com.janocare.auth.domain.entities.ActivityLog;

public interface ActivityLogPort {
    void save(ActivityLog log);
}
