package com.janocare.auth.application.handlers;

import com.janocare.auth.api.responses.auth.AuthResponse;
import com.janocare.auth.api.responses.auth.LoginResponse;
import com.janocare.auth.api.responses.auth.RegisterResponse;
import com.janocare.auth.api.responses.auth.TokenResponse;
import com.janocare.auth.api.responses.oauth.GoogleSignInResponse;
import com.janocare.auth.api.responses.otp.ResendOtpResponse;
import com.janocare.auth.api.responses.otp.VerifyOtpResponse;
import com.janocare.auth.api.responses.password.ChangePasswordResponse;
import com.janocare.auth.api.responses.password.ForgotPasswordResponse;
import com.janocare.auth.api.responses.password.ResetPasswordResponse;
import com.janocare.auth.api.responses.user.UserResponse;

import com.janocare.auth.application.commands.auth.LoginCommand;
import com.janocare.auth.application.commands.auth.RefreshTokenCommand;
import com.janocare.auth.application.commands.auth.RegisterCommand;
import com.janocare.auth.application.commands.oauth.GoogleSignInCommand;
import com.janocare.auth.application.commands.otp.ResendOtpCommand;
import com.janocare.auth.application.commands.otp.VerifyOtpCommand;
import com.janocare.auth.application.commands.password.ChangeMyPasswordCommand;
import com.janocare.auth.application.commands.password.ForgotPasswordCommand;
import com.janocare.auth.application.commands.password.ResetPasswordCommand;
import com.janocare.auth.application.commands.password.SetPasswordCommand;
import com.janocare.auth.application.ports.*;
import com.janocare.auth.application.queries.profile.GetMyProfileQuery;
import com.janocare.auth.application.queries.user.FindUserByIdQuery;
import com.janocare.auth.domain.entities.*;
import com.janocare.auth.domain.enums.UserRole;
import com.janocare.auth.domain.enums.UserStatus;
import com.janocare.auth.api.mappers.CountryApiMapper;
import com.janocare.auth.api.mappers.StateApiMapper;
import com.janocare.auth.api.mappers.CityApiMapper;
import com.janocare.auth.infrastructure.clients.notification.CreateNotificationRequest;
import com.janocare.auth.infrastructure.clients.notification.NotificationClient;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.janocare.auth.api.responses.profile.ProfileResponse;
import com.janocare.auth.domain.utils.PasswordGenerator;
import com.janocare.auth.infrastructure.clients.professional.ProfessionalServiceClient;
import com.janocare.auth.application.commands.profile.UpdateProfilePictureCommand;
import com.janocare.auth.application.commands.profile.UpdateProfileCommand;
import com.janocare.auth.api.mappers.ProfileApiMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
public class AuthCommandHandler {

    @Inject UserRepositoryPort users;
    @Inject UserProfilePort userProfiles;
    @Inject UserAccessPort userAccesses;
    @Inject ActivityLogPort activityLogs;
    @Inject PasswordHasherPort hasher;
    @Inject JwtProviderPort jwt;
    @Inject VerificationServicePort otp;
    @Inject
    @RestClient
    ProfessionalServiceClient professionalClient;
    @Inject CountryRepositoryPort countries;
    @Inject StateRepositoryPort states;
    @Inject CityRepositoryPort cities;
    @Inject
    @RestClient
    NotificationClient notificationClient;


    @Transactional
    public RegisterResponse register(RegisterCommand command) {

        var resp = new RegisterResponse();

        if (command == null) {
            resp.success = false;
            resp.message = "Command body is required";
            return resp;
        }

        if (users.findByEmail(command.email).isPresent()) {
            resp.success = false;
            resp.message = "Email already registered";
            return resp;
        }

        UserRole role =
                command.role != null
                        ? UserRole.valueOf(command.role)
                        : UserRole.PATIENT;

        UUID professionTypeId =
                command.professionTypeId != null
                        ? UUID.fromString(command.professionTypeId)
                        : null;

        if (role == UserRole.PROFESSIONAL &&
                professionTypeId == null) {

            resp.success = false;
            resp.message =
                    "Profession type is required for professional registration";
            return resp;
        }

        String initialPassword =
                PasswordGenerator.generate();

        String hashedInitialPassword =
                hasher.hash(initialPassword);

        User user =
                User.create(
                        command.username,
                        command.email,
                        command.phone,
                        hashedInitialPassword
                );

        user.changeRole(role);
        user.setMustChangePassword(true);

        users.save(user);

        userProfiles.save(
                UserProfile.createFor(user)
        );

        UserAccess access =
                UserAccess.createInitial(user);

        AccessDevice device =
                AccessDevice.fromRegistration(
                        command.deviceName,
                        command.deviceType
                );

        access.addDevice(device);

        userAccesses.save(access);

        if (role == UserRole.PROFESSIONAL) {
            professionalClient.createProfessional(
                    user.getId(),
                    professionTypeId
            );
        }

        var otpCode =
                otp.generateAndSendCode(
                        user.getEmail().getValue()
                );

        try {
            CreateNotificationRequest notification =
                    new CreateNotificationRequest();

            notification.userId =
                    user.getId().toString();

            notification.subject =
                    "JanoCare Account Verification";

            notification.message =
                    "Your verification code is: "
                            + otpCode
                            + "\n\nYour initial password is: "
                            + initialPassword
                            + "\n\nPlease verify your email and change your password after login.";

            notification.destination =
                    user.getEmail().getValue();

            notification.type =
                    "SYSTEM_MESSAGE";

            notification.channel =
                    "EMAIL";

            notificationClient.createNotification(notification);

        } catch (Exception e) {
            System.out.println(
                    "Notification service failed/skipped: "
                            + e.getMessage()
            );
        }

        activityLogs.save(
                ActivityLog.register(user)
        );

        activityLogs.save(
                ActivityLog.initialPassword(user)
        );

        resp.success = true;
        resp.message =
                "Registration successful. Please verify your email.";

        return resp;
    }
    @Transactional
    public UserResponse updateProfile(UpdateProfileCommand command) {
        User user = users.findById(command.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfiles.findByUserId(command.userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        profile.updateProfile(
                command.firstName,
                command.lastName,
                command.profilePic,
                command.gender,
                command.countryId,
                command.stateId,
                command.cityId
        );

        userProfiles.save(profile);

        return toUserResponse(user);
    }
    @Transactional
    public UserResponse updateProfilePicture(
            UpdateProfilePictureCommand command
    ) {
        if (command == null || command.userId == null) {
            throw new RuntimeException("User ID is required");
        }

        if (command.file == null) {
            throw new RuntimeException("Profile picture file is required");
        }

        User user = users.findById(command.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfiles.findByUserId(command.userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        String fileName =
                command.userId + "-" + System.currentTimeMillis() + ".jpg";

        String relativePath =
                "uploads/profile-pictures/" + fileName;

        try {
            java.nio.file.Path uploadDir =
                    java.nio.file.Paths.get("uploads/profile-pictures");

            java.nio.file.Files.createDirectories(uploadDir);

            java.nio.file.Path targetPath =
                    uploadDir.resolve(fileName);

            java.nio.file.Files.copy(
                    command.file.toPath(),
                    targetPath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }

        profile.updateProfilePicture(relativePath);

        userProfiles.save(profile);

        UserResponse userResponse = toUserResponse(user);

        userProfiles.findByUserId(user.getId())
                .ifPresent(updatedProfile -> {
                    userResponse.userProfile =
                            ProfileApiMapper.toResponse(updatedProfile);
                });

        return userResponse;
    }
    @Transactional
    public LoginResponse login(LoginCommand command) {
        User user = users.findByEmail(command.identifier)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!hasher.verify(command.password, user.getPasswordHash().getValue())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Account not verified");
        }

        if (user.isMustChangePassword()) {
            throw new RuntimeException("Password change required");
        }

        LoginResponse resp = new LoginResponse();
        resp.success = true;
        resp.access_token = jwt.generateAccessToken(user);
        resp.refresh_token = jwt.generateRefreshToken(user);
        UserResponse userResponse =
                toUserResponse(user);

        var profileOpt = userProfiles.findByUserId(user.getId());

        System.out.println("LOGIN USER ID = " + user.getId());
        System.out.println("PROFILE FOUND = " + profileOpt.isPresent());

        profileOpt.ifPresent(profile -> {
            System.out.println("PROFILE ID = " + profile.getId());
            System.out.println("PROFILE FIRST NAME = " + profile.getFirstName());

            userResponse.userProfile =
                    ProfileApiMapper.toResponse(profile);

            System.out.println("MAPPED PROFILE = " + userResponse.userProfile);
        });

        resp.user = userResponse;
        return resp;
    }

    @Transactional
    public TokenResponse refreshToken(RefreshTokenCommand command) {
        if (command == null || command.refreshToken == null || command.refreshToken.isBlank()) {
            throw new RuntimeException("Missing refresh token");
        }

        if (!jwt.validateRefreshToken(command.refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        UUID userId = jwt.extractUserId(command.refreshToken);

        User user = users.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TokenResponse resp = new TokenResponse();
        resp.access_token = jwt.generateAccessToken(user);
        resp.refresh_token = jwt.generateRefreshToken(user);

        return resp;
    }

    @Transactional
    public VerifyOtpResponse verifyOtp(VerifyOtpCommand command) {
        boolean ok = otp.verifyCode(command.email, command.otp);

        VerifyOtpResponse resp = new VerifyOtpResponse();
        resp.success = ok;

        if (!ok) {
            return resp;
        }

        User user = users.findByEmail(command.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(UserStatus.ACTIVE);
        users.save(user);

        AuthResponse auth = new AuthResponse();
        auth.success = true;
        auth.user = toUserResponse(user);
        auth.access_token = jwt.generateAccessToken(user);
        auth.refresh_token = jwt.generateRefreshToken(user);
        auth.mustChangePassword = true;

        resp.auth = auth;

        return resp;
    }

    @Transactional
    public ResendOtpResponse resendOtp(
            ResendOtpCommand command
    ) {

        User user =
                users.findByEmail(command.email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        var otpCode =
                otp.generateAndSendCode(
                        command.email
                );

        try {
            CreateNotificationRequest notification =
                    new CreateNotificationRequest();

            notification.userId =
                    user.getId().toString();

            notification.subject =
                    "JanoCare OTP Verification";

            notification.message =
                    "Your new verification code is: "
                            + otpCode;

            notification.destination =
                    user.getEmail().getValue();

            notification.type =
                    "OTP";

            notification.channel =
                    "EMAIL";

            notificationClient.createNotification(
                    notification
            );

        } catch (Exception e) {

            System.out.println(
                    "Notification service failed/skipped: "
                            + e.getMessage()
            );
        }

        activityLogs.save(
                ActivityLog.resendOtp(user)
        );

        ResendOtpResponse resp =
                new ResendOtpResponse();

        resp.success = true;

        resp.message =
                "OTP sent to email";

        return resp;
    }

    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordCommand command) {
        otp.generateAndSendCode(command.email);

        ForgotPasswordResponse resp = new ForgotPasswordResponse();
        resp.success = true;
        resp.message = "Password reset OTP sent to email";

        return resp;
    }

    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordCommand command) {
        if (!otp.verifyCode(command.email, command.otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (!command.newPassword.equals(command.confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = users.findByEmail(command.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updatePassword(hasher.hash(command.newPassword));
        user.setMustChangePassword(false);
        users.save(user);

        ResetPasswordResponse resp = new ResetPasswordResponse();
        resp.success = true;
        resp.message = "Password reset successful";

        return resp;
    }

    @Transactional
    public ChangePasswordResponse setPassword(SetPasswordCommand command) {
        if (!command.password.equals(command.confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = users.findByEmail(command.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updatePassword(hasher.hash(command.password));
        user.setStatus(UserStatus.ACTIVE);
        user.setMustChangePassword(false);
        users.save(user);

        ChangePasswordResponse resp = new ChangePasswordResponse();
        resp.success = true;
        resp.message = "Password set successfully";

        return resp;
    }

    @Transactional
    public ChangePasswordResponse changeMyPassword(ChangeMyPasswordCommand command) {
        User user = users.findByEmail(command.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!hasher.verify(command.previousPassword, user.getPasswordHash().getValue())) {
            throw new RuntimeException("Invalid previous password");
        }

        if (!command.newPassword.equals(command.confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        user.updatePassword(hasher.hash(command.newPassword));
        user.setMustChangePassword(false);
        users.save(user);

        ChangePasswordResponse resp = new ChangePasswordResponse();
        resp.success = true;
        resp.message = "Password changed successfully";

        return resp;
    }

    public void logout() {}

    public UserResponse getProfile(GetMyProfileQuery query) {

        User user = users.findById(query.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = toUserResponse(user);

        userProfiles.findByUserId(user.getId())
                .ifPresent(profile -> {

                    ProfileResponse profileResponse =
                            ProfileApiMapper.toResponse(profile);

                    if (profile.getCountryId() != null) {

                        countries.findDomainById(profile.getCountryId())
                                .ifPresent(country -> {
                                    profileResponse.country =
                                            CountryApiMapper.toResponse(country);
                                });
                    }

                    if (profile.getStateId() != null) {

                        states.findDomainById(profile.getStateId())
                                .ifPresent(state -> {
                                    profileResponse.state =
                                            StateApiMapper.toResponse(state);
                                });
                    }

                    if (profile.getCityId() != null) {

                        cities.findDomainById(profile.getCityId())
                                .ifPresent(city -> {
                                    profileResponse.city =
                                            CityApiMapper.toResponse(city);
                                });
                    }

                    userResponse.userProfile = profileResponse;
                });

        return userResponse;
    }

        public UserResponse findUserById(FindUserByIdQuery query) {
        User user = users.findById(query.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = toUserResponse(user);

        userProfiles.findByUserId(user.getId())
                .ifPresent(profile -> {
                        userResponse.userProfile = ProfileApiMapper.toResponse(profile);
                });

        return userResponse;
        }

    public String getGoogleRedirectUri() {
        return "https://accounts.google.com/o/oauth2/v2/auth";
    }

    public GoogleSignInResponse googleSignIn(GoogleSignInCommand command) {
        GoogleSignInResponse resp = new GoogleSignInResponse();
        resp.success = false;
        resp.message = "Google sign-in not implemented";

        return resp;
    }

    private UserResponse toUserResponse(User user) {
        UserResponse r = new UserResponse();

        r.id = user.getId().toString();
        r.username = user.getUsername();
        r.email = user.getEmail().getValue();
        r.phone = user.getPhone().getValue();
        r.role = user.getRole().name();
        r.status = user.getStatus().name();

        return r;
    }
}