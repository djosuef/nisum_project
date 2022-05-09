package com.example.security.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Arrays;

import com.example.phone.Phone;
import com.example.phone.PhoneRepository;
import com.example.phone.PhoneRestController;
import com.example.security.JwtAuthenticationRequest;
import com.example.security.JwtTokenUtil;
import com.example.security.JwtUser;
import com.example.security.config.exception.NotFoundException;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${password.validate_regex}")
    private String regex_password;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PhoneRestController phoneController;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ObjectNode getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser jwtuser = (JwtUser) userDetailsService.loadUserByUsername(username);

        ObjectNode user = convertJwtUserToUser(jwtuser);

        return user;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity < User > getEmployeeById(@PathVariable(value = "id") Long userId)
    throws ResourceNotFoundException {
            User user = userRepository.findOne(userId);
            
            if(user != null){
                return new ResponseEntity<>(user, HttpStatus.OK);
            }else{
                throw new NotFoundException("No existe usurio con id " + userId );
            }
    }

    /*@RequestMapping(method = RequestMethod.POST)
    public User add(@RequestBody ObjectNode user_phone) throws JsonParseException, JsonMappingException, IOException {
     
        //System.out.println("username:" + user_phone.get("username").asText());

        String name = user_phone.get("username").asText();
        String password = user_phone.get("password").asText();
        Boolean enable = true;
        String email = user_phone.get("email").asText();

        

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode userJson = nodeFactory.objectNode();
        userJson.put("username", name);
        userJson.put("password", password);
        userJson.put("enabled", enable);
        userJson.put("email", email);

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date datetimeNow = Date.from(instant);

        User newUser = new User(name, password, email, enable, datetimeNow, null, datetimeNow, "a");
        
        //User newUser = jsonObjectMapper.readValue(userJson.traverse(), User.class);

        System.out.println("USER:" + user_phone.toString());

        User userSaved = userRepository.save(newUser);

        ObjectNode phoneJson = nodeFactory.objectNode();
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        arrayNode = (ArrayNode) user_phone.get("phones");

        System.out.println("arrayNode_phones:" + arrayNode.toString());

        for (JsonNode jsonNode : arrayNode) {
            System.out.println("jsonNode_1:" + jsonNode.toString());

            ((ObjectNode) jsonNode).set("user",userJson);

            System.out.println("jsonNode_2:" + jsonNode.toString());  
            
            Phone phone = new Phone(jsonNode.get("number").asText(), jsonNode.get("citycode").asText(), jsonNode.get("countrycode").asText(), newUser);

            phoneController.add(phone);
        }

        return userSaved;
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity <JsonNode> add(@RequestBody ObjectNode user_phone) throws JsonParseException, JsonMappingException, IOException {

        String name = user_phone.get("username").asText();
        String password = user_phone.get("password").asText();
        Boolean enable = true;
        String email = user_phone.get("email").asText();

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode userJson = nodeFactory.objectNode();
        userJson.put("username", name);
        userJson.put("password", password);
        userJson.put("enabled", enable);
        userJson.put("email", email);

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date datetimeNow = Date.from(instant);

        if (!validarPassword(password)){
            ObjectNode formatPasswordWrong = nodeFactory.objectNode();
            formatPasswordWrong.put("message", "Password no cumple con las condiciones");
            return new ResponseEntity<>(formatPasswordWrong, HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        if (!validarEmail(email)){
            ObjectNode errorEmail = nodeFactory.objectNode();
            errorEmail.put("message", "Email inválido");
            return new ResponseEntity<>(errorEmail, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmailUser(email)!= null){
            ObjectNode emailExist = nodeFactory.objectNode();
            emailExist.put("message", "El correo ya registrado");
            return new ResponseEntity<>(emailExist, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByUsername(name)!= null){
            ObjectNode nameExist = nodeFactory.objectNode();
            nameExist.put("message", "Ya existe un usuario con el nombre:" + name);
            return new ResponseEntity<>(nameExist, HttpStatus.BAD_REQUEST);
        }


        User newUser = new User(name, encodedPassword, email, enable, datetimeNow, null, datetimeNow, "token");

        User userSaved = userRepository.save(newUser);

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        arrayNode = (ArrayNode) user_phone.get("phones");

        for (JsonNode jsonNode : arrayNode) {
            ((ObjectNode) jsonNode).set("user",userJson);
            
            Phone phone = new Phone(jsonNode.get("number").asText(), jsonNode.get("citycode").asText(), jsonNode.get("countrycode").asText(), newUser);

            phoneController.add(phone);         
            
        }
        
        ObjectNode userSavedJson = nodeFactory.objectNode();

        userSavedJson.put("id", userSaved.getId());
        userSavedJson.put("created", userSaved.getCreated().toString());
        userSavedJson.put("modified", "");
        userSavedJson.put("last_login", userSaved.getLast_login().toString());
        userSavedJson.put("token", userSaved.getToken());
        userSavedJson.put("isactive", userSaved.getEnabled());

        return new ResponseEntity<>(userSavedJson, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity < JsonNode > updateUser(@PathVariable(value = "id") Long userId,
        @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findOne(userId);

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode userUpdateJson = nodeFactory.objectNode();

        if(user != null){
            user.setEmail(userDetails.getEmail());
            user.setEnabled(userDetails.getEnabled());
            user.setPassword(userDetails.getPassword());
            user.setToken(userDetails.getToken());            
        }else{
            userUpdateJson.put("message","No existe el usuario");
        }      
        final User updatedUser = userRepository.save(user);

        userUpdateJson = constructUserResponse(updatedUser);
        
        return ResponseEntity.ok(userUpdateJson);
    }

    @DeleteMapping("/user/{id}")
    public JsonNode deleteEmployee(@PathVariable(value = "id") Long userId)
    throws ResourceNotFoundException {
        User user = userRepository.findOne(userId);
        
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode userJson = nodeFactory.objectNode();

        if(user != null){
            userRepository.delete(user);
        }else{
            userJson.put("message", "No existe usurio con id " + userId );
            throw new NotFoundException(userJson.toString());
        }
        userJson.put("message", "Fue eliminado el usurio: " + user.getUsername() );

        return userJson;
    }


    private ObjectNode convertJwtUserToUser(JwtUser jwtuser){

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode user = nodeFactory.objectNode();

        user.put("name", jwtuser.getUsername());
        user.put("email", jwtuser.getEmail());
        user.put("password", jwtuser.getPassword());
        //user.set("phones", arrayNode);

        return user;
    }

    public boolean validarEmail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    public boolean validarPassword(String password) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile(regex_password);
        Matcher mather = pattern.matcher(password);
        return mather.find();
    }

    public User updateDate_login(long id) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date datetimeNow = Date.from(instant);

        User user = userRepository.findOne(id);
        user.setLast_login(datetimeNow);
        userRepository.save(user);

        return user;
    }

    private ObjectNode constructUserResponse(User userSaved){
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode userUpdateJson = nodeFactory.objectNode();

        userUpdateJson.put("id", userSaved.getId());
        userUpdateJson.put("created", userSaved.getCreated().toString());
        userUpdateJson.put("modified", "");
        userUpdateJson.put("last_login", userSaved.getLast_login().toString());
        userUpdateJson.put("token", userSaved.getToken());
        userUpdateJson.put("isactive", userSaved.getEnabled());

        return userUpdateJson;
    }


}
