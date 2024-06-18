package com.ProjectManagement.pms.Controller;

import com.ProjectManagement.pms.DTO.LoginDto;
import com.ProjectManagement.pms.DTO.UserDto;
import com.ProjectManagement.pms.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> getAll(){
        return service.getAllData();
    }

    @PostMapping
    public UserDto addData(@RequestBody UserDto userDto){
        return service.saveData(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping()
    public ResponseEntity<UserDto> putProject(@RequestBody UserDto userDto){
        return ResponseEntity.ok(service.saveData(userDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Long id){
        service.deleteData(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
       return ResponseEntity.ok(service.loginUser(loginDto.getEmail(), loginDto.getPassword()));
    }

}
