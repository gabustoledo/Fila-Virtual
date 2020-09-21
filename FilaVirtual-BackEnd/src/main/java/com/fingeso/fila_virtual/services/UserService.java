package com.fingeso.fila_virtual.services;

import com.fingeso.fila_virtual.models.User;
import com.fingeso.fila_virtual.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // requests can be done from every port with *
@RequestMapping(value = "/user") // user queries URL
public class UserService {

    @Autowired // configuration to bring queries from UserRepo without some manual configurations
    private UserRepo userRepo;

    // basically CRUD to connect the web browser with the DB, this is a GET
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody // this will make the controller respond with a JSON format
    public List<User> getAll() {
        return this.userRepo.findAll();
    }

    @RequestMapping(value = "/getbyname/{names}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserByName(@PathVariable(value = "names") String names) {
        return this.userRepo.findUserByNames(names);
    }

    @RequestMapping(value = "/registeruser", method = RequestMethod.GET)
    public ModelAndView createUser(@RequestParam(defaultValue = "defaultNames") String names
                            , @RequestParam(defaultValue = "defaultSurnames") String surnames
                            , @RequestParam(defaultValue = "11.111.111-1") String rut
                            , @RequestParam(defaultValue = "example@ex.com") String email
                            , @RequestParam(defaultValue = "safepass") String password
                            , @RequestParam(defaultValue = "+56912345678") String phoneNum
                            , @RequestParam(defaultValue = "user") String role){

        ModelMap model = new ModelMap();
        model.addAttribute("names", names);

        User newUser = new User(rut, names, surnames, email, password, phoneNum, role);
        try{
            this.userRepo.save(newUser);
        }
        catch (Exception e){
            System.out.println("ERROR: COULDN'T CREATE USER");
            return null;
        }

        return new ModelAndView("redirect:/owner/registerowner", model);

    }

}

