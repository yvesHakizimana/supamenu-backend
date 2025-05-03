package com.supamenu.backend.users;


import com.supamenu.backend.auth.dtos.RegisterRequestDto;
import com.supamenu.backend.commons.exceptions.BadRequestException;
import com.supamenu.backend.users.dtos.UserResponseDto;
import com.supamenu.backend.users.mappers.UserMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdmin(){
        var adminEmail = "admin@gmail.com";
        var adminPassword = "admin123";
        var adminPhoneNumber = "0790000001";
        var adminNationalId = "1200680001234083";
        if(!userRepository.existsByEmailOrPhoneNumberOrNationalId(adminEmail, adminPhoneNumber, adminNationalId)){
            var admin = new User();
            admin.setFirstName("admin-fname");
            admin.setLastName("admin-lname");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setPhoneNumber(adminPhoneNumber);
            admin.setNationalId(adminNationalId);
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
        } else {
            log.info("Already created the admin user.");
        }
    }

    public UserResponseDto createUser(RegisterRequestDto user) {
        if(userRepository.existsByEmailOrPhoneNumberOrNationalId(user.email(), user.phoneNumber(), user.nationalId()))
            throw new BadRequestException("User with this email or nationalId or  phone number already exists.");

        var newUser = userMapper.toEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(Role.CUSTOMER);
        newUser.setEnabled(false);
        log.info("user is here, {}", newUser);
        userRepository.save(newUser);
        return userMapper.toResponseDto(newUser);
    }

    public void changeUserPassword(String userEmail, String newPassword){
        var user = findByEmail(userEmail);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void activateUserAccount(String userEmail){
        var user = findByEmail(userEmail);
        user.setEnabled(true);
        userRepository.save(user);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User with that email not found."));
    }
}
