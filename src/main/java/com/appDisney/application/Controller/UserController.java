package com.appDisney.application.Controller;

import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Entities.Usuario;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.UserRepository;
import com.appDisney.application.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/usuarioProfile")
    public String userProfile(Model model){
        return "usuarioProfile";
    }

    @PostMapping("/usuarioProfile")
    public String userProfile(ModelMap model, @RequestParam String userName, @RequestParam String email,
                              @RequestParam String encrypted1, @RequestParam String encrypted2, @RequestParam(required = false) MultipartFile image){
        try {
            userService.saveUser(userName, email, encrypted1, encrypted2,image);
            return "redirect:/indexWithUser";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("userName", userName);
            model.put("email", email);
            model.put("encrypted1", encrypted1);
            model.put("encrypted2", encrypted2);
            model.put("image",image);
            return "redirect:/indexWithUser";
        }
    }


    @GetMapping("/editUser/{id}")
    public String editUser(ModelMap model, @PathVariable ("id") Integer id){
        Usuario usuario = userService.getByid(id);
        model.put("usuario", usuario);
        model.put("id", usuario.getId());
        return "editUser";
    }


    @PostMapping("/editUser/{id}")
    public String editUser(ModelMap model, @PathVariable (name = "id", required = true) Integer idUser, @RequestParam String userName, @RequestParam String email,
                              @RequestParam String encrypted1, @RequestParam String encrypted2, @RequestParam(required = false) MultipartFile image){
        try {
            userService.editUser(idUser, userName, email, encrypted1, encrypted2,image);
            return "redirect:/usuarioProfile";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("userName", userName);
            model.put("email", email);
            model.put("encrypted1", encrypted1);
            model.put("encrypted2", encrypted2);
            model.put("image",image);
            return "redirect:/usuarioProfile";
        }
    }


    @GetMapping("/eliminateUser")
    public String eliminateUser(ModelMap model, HttpSession session, @RequestParam Integer idUser) throws ErrorServices{
        Usuario usuario = userRepository.getById(idUser);
        model.put("userName", usuario.getUserName());
        model.put("idUser", usuario.getId());
        return "redirect:/";
    }


    @GetMapping("/usuario/{idUsuario}")
    public String usuario(ModelMap model, @PathVariable(name = "idUsuario", required = true) Integer idUsuario){
        Usuario usuario = userRepository.getById(idUsuario);
        model.put("usuario", usuario);

        model.put("usuarioId", usuario.getId());
        model.put("userName", usuario.getUserName());
        model.put("email", usuario.getEmail());
        model.put("encrypted1", usuario.getEncrypted());
        model.put("image",usuario.getProfilePicture());
        return "usuario";
    }
}
