package app.service.services;

import app.domain.entities.User;
import app.domain.entities.UserRole;
import app.domain.models.service.UserServiceModel;
import app.errors.UserAlreadyExistsException;
import app.service.repositories.RoleRepository;
import app.service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userFromDb = this.userRepository.findByUsername(username).orElse(null);

        if (userFromDb == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }

        return userFromDb;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElse(null);

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        this.seedRolesInDb();

        user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));

        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel extractUserByUsername(String username) {
        User userFromDb = this.userRepository.findByUsername(username).orElse(null);

        if (userFromDb == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }

        return this.modelMapper.map(userFromDb, UserServiceModel.class);
    }

    private void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            UserRole user = new UserRole();
            user.setAuthority("USER");

            this.roleRepository.save(user);
        }
    }
}
