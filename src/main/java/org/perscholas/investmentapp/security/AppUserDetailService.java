package org.perscholas.investmentapp.security;


import org.perscholas.investmentapp.dao.AuthGroupRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailService implements UserDetailsService {
    UserRepoI userRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailService(UserRepoI userRepoI, AuthGroupRepoI authGroupRepoI) {
        this.userRepoI = userRepoI;
        this.authGroupRepoI = authGroupRepoI;
        this.userRepoI = userRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new AppUserPrincipal(
                userRepoI.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Email Not Found"))
                , authGroupRepoI.findByEmail(username));
    }
}
