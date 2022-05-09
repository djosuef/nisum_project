package com.example.phone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/phone")
public class PhoneRestController {

    @Autowired
    private PhoneRepository phoneRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }
    
	@RequestMapping(method = RequestMethod.GET, value = "/{phoneId}")
    public Phone findOne(@PathVariable Long phoneId) {
        return phoneRepository.findOne(phoneId);
    }
    
	@RequestMapping(method = RequestMethod.POST)
    public Phone add(@RequestBody Phone phone) {
        return phoneRepository.save(phone);
    }

	@RequestMapping(method = RequestMethod.PUT)
    public Phone update(@RequestBody Phone phone) {
        return phoneRepository.save(phone);
    }
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{phoneId}")
    public void delete(@PathVariable Long phoneId) {
        phoneRepository.delete(phoneId);
    }
	
}

