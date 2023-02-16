package com.oleynikovee.atm;

import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.model.security.UserRole;
import com.oleynikovee.atm.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;



import static com.oleynikovee.atm.config.Constants.API_KEY;

@Slf4j
@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = API_KEY, in = SecuritySchemeIn.HEADER)
public class AtmApplication implements ApplicationListener<ApplicationReadyEvent> {
    private final UserService userService;

    @Autowired
    public AtmApplication(UserService userService) {
        this.userService = userService;
    }
    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("APP STARTED!");

        addTestUser(0, "admin");
        addTestUser(1, "test");
    }

    private void addTestUser(int id, String login) {
        try {
            userService.getUserById(id);
            log.info("Admin is present, skip creating one");
        } catch (ApplicationException e) {
            User user = new User();
            user.setId(id);
            user.setRole(login.equals("admin") ? UserRole.ADMIN : UserRole.USER);
            user.setLogin(login);
            user.setPassword(login);
            user.setAmountOfMoney(2312234);
            userService.addUser(user);
            log.info(login.toUpperCase() + " user added");
        }
    }

}
