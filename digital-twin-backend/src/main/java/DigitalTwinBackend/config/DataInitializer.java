package DigitalTwinBackend.config;

import DigitalTwinBackend.entity.User;
import DigitalTwinBackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("Admin@123"),
                        "ADMIN"
                );

                userRepository.save(admin);
            }

            if (userRepository.findByUsername("manager").isEmpty()) {
                User manager = new User(
                        "manager",
                        passwordEncoder.encode("Manager@123"),
                        "MANAGER"
                );

                userRepository.save(manager);
            }
        };
    }
}