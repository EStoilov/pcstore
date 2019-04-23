package com.softuni.pcstore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.repository.RoleRepository;
import com.softuni.pcstore.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class UserServiceTests {
    
    private UserService userService;
    private UserServiceModel testUser;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);
        this.userService = new UserServiceImpl(userRepository, roleService, modelMapper,bCryptPasswordEncoder);
        
        this.testUser = new UserServiceModel();
        this.testUser.setUsername("testUsername");
        this.testUser.setPassword("123456");
        this.testUser.setEmail("testEmail");
    }
    
    @Test
    public void T01_Register_User_With_Valid_Data(){
        this.userService.registerUser(testUser);
        long expected = this.userRepository.findAll().size();

        Assert.assertEquals(1, expected);
    }
    
    @Test(expected = Exception.class)
    public void T02_Register_User_With_Invalid_Data_Throw_Exception(){
        this.testUser.setUsername(null);
        this.userService.registerUser(this.testUser);
    }
    
    @Test
    public void T03_FindAllUsers_With_Correct_Data_Return_OK(){
         this.userService.registerUser(this.testUser);
         long expected = this.userService.findAllUsers().size();
         
         Assert.assertEquals(1, expected);
    }
    
    @Test
    public void T04_FindByUsername_With_Correct_Data_Return_OK(){
         this.userService.registerUser(this.testUser);
         UserServiceModel expected = this.userService.findByUsername(this.testUser.getUsername());
         
         Assert.assertEquals(expected.getUsername(), this.testUser.getUsername());
    }
    
    @Test(expected = Exception.class)
    public void T05_FindByUsername_With_Incorrect_Data_Throw_Exception(){
        this.userService.registerUser(this.testUser);
        this.userService.findByUsername("expectedException");
    }
}
