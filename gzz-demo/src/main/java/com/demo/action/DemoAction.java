package com.demo.action;


import com.demo.dao.IPersonMapper;
import com.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAction {

	@Autowired
	private IPersonMapper personMapper;
	
	@RequestMapping("/getTicket")
	public Object getTicket(Long id) {
		System.out.println("" + id);
		
		Person p = personMapper.selectByPrimaryKey(id);
		return p;
	}
}
