package com.oleynikovee.atm.service;

import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.model.security.UserPrincipal;
import com.oleynikovee.atm.web.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenUtil tokenUtil;

    public String login(String login, String password) {
        User user = userService.getByLogin(login);
        String hash = DigestUtils.md5Hex(password).toLowerCase();
        if (!hash.equals(user.getPassword())) {
            throw ApplicationException.badRequest("Wrong credentials!");
        } else {
            return tokenUtil.generateToken(new UserPrincipal(user));
        }
    }
}
