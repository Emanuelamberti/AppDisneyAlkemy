package com.appDisney.application.Services;

import com.appDisney.application.Entities.Image;
import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Entities.Usuario;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageServices imageServices;

    @Autowired
    private EmailServices emailServices;

    private void validate(String userName, String email) throws ErrorServices{
        if (userName==null || userName.isEmpty()){
            throw new ErrorServices("The User name cannot be empty.");
        }if (email==null || email.isEmpty()){
            throw new ErrorServices("The mail cannot be empty.");
        }
    }

    @Transactional
    public void saveUser(String userName, String email, String encrypted1, String encrypted2, MultipartFile archive) throws ErrorServices{
        if (!encrypted1.equals(encrypted2)){
            throw new ErrorServices("The keys do not match.");
        }

        validate(userName, email);

        Usuario usuario = new Usuario();

        String encrypted = new BCryptPasswordEncoder().encode(encrypted1);
        usuario.setEncrypted(encrypted);

        usuario.setEmail(email);
        usuario.setUserName(userName);

        if (archive!=null && !archive.isEmpty()){
            Image image = imageServices.saveImage(archive);
            usuario.setProfilePicture(image);
        }

        emailServices.mailToUsuario(usuario.getUserName(), usuario.getEmail(), "thank you for registering in this page.");
        userRepository.save(usuario);
        //notificationServices.sentMail("¡Welcome to App Disney!\nWelcome, "+
                //userName+".", "App Disney - Register users" , email);
    }

    @Transactional
    public void editUser(Integer idUser, String userName, String email, String encrypted1, String encrypted2, MultipartFile archive) throws ErrorServices{
        Optional<Usuario> answer = userRepository.findById(idUser);

        if(answer.isPresent()){
            Usuario usuario = answer.get();
            usuario.setUserName(userName);
            usuario.setEmail(email);
            if (archive!=null && !archive.isEmpty()){
                if (usuario.getProfilePicture()!=null){
                    Integer idImage = usuario.getProfilePicture().getId();
                    Image image = imageServices.editImage(idImage, archive);
                    usuario.setProfilePicture(image);
                }
                else {
                    Image image = imageServices.saveImage(archive);
                    usuario.setProfilePicture(image);
                }
            }
            userRepository.save(usuario);
            //notificationServices.sentMail("¡Welcome to App Disney!\nWelcome, "+
              //      userName+".", "App Disney - Register users" , email);
        }else {
            throw new ErrorServices("User not found.");
        }
    }

    @Transactional
    public void changePassword(Integer idUser, String encrypted1, String encrypted2) throws ErrorServices{
        Optional<Usuario> answer = userRepository.findById(idUser);

        if (answer.isPresent()){
            if (!encrypted1.equals(encrypted2)){
                throw new ErrorServices("The keys do not match.");
            }
            if (encrypted1==null || encrypted1.length()<8 || encrypted1.isEmpty()){
                throw new ErrorServices("Invalid key.");
            }
            Usuario usuario = answer.get();
            String encrypted = new BCryptPasswordEncoder().encode(encrypted1);
            usuario.setEncrypted(encrypted);
            userRepository.save(usuario);
            //notificationServices.sentMail("Disney notifies you!\n Your password has been changed successfully. \n ¡Thank you!",
              //      "App Disney - Change of password." , usuario.getEmail());
        }
        else{
            throw new ErrorServices("User not found.");
        }
    }

    @Transactional
    public void downloadUser(Integer id) throws ErrorServices{
        Optional<Usuario> answer = userRepository.findById(id);
        if (answer.isPresent()){
            Usuario usuario = answer.get();
            usuario.setBaja(new Date());
            userRepository.save(usuario);
            //notificationServices.sentMail("¡Bye!\n" + usuario.getUserName()+", come back soon.", "App Disney" , usuario.getEmail());
        }
        else {
            throw new ErrorServices("User not found.");
        }
    }

    @Transactional
    public void uploadUser(Integer id) throws ErrorServices{
        Optional<Usuario> answer = userRepository.findById(id);
        if (answer.isPresent()){
            Usuario usuario = answer.get();
            usuario.setBaja(null);
            userRepository.save(usuario);
            //notificationServices.sentMail("¡Welcome to App Disney!\n We were waiting for it, "
              //      + usuario.getUserName()+".", "App Disney" , usuario.getEmail());
        }
        else {
            throw new ErrorServices("User not found.");
        }
    }

    @Transactional
    public Usuario getByid(Integer id){
        return userRepository.getById(id);
    }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = (Usuario) userRepository.findUserByEmail(email);

        if (usuario!=null){
            List<GrantedAuthority> permissions = new ArrayList();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_REGISTERED_USER");
            permissions.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usersession", usuario);

            User user = new User(usuario.getEmail(), usuario.getEncrypted(), permissions);

            return user;

        }
        else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

}
