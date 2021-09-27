package br.com.codenation.CentralDeErros.service;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.model.User;
import br.com.codenation.CentralDeErros.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public List<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable).getContent();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return (UserDetails) this.userRepository.findByEmail(login);
    }
}
