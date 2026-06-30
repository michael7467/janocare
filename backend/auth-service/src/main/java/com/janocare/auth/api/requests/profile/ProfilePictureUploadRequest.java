package com.janocare.auth.api.requests.profile;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.PartType;

import java.io.File;

public class ProfilePictureUploadRequest {

    @FormParam("profile_picture")
    @PartType("application/octet-stream")
    public File file;
}