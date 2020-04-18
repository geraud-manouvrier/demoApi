package cl.geraud.demoApi.rs.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.geraud.demoApi.rs.model.request.PersonRequest;

@RestController
public class TestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PostMapping(value="/reporte-test")
	public int listarTest(@RequestParam String usuario, @RequestParam String contrasena) throws NoSuchAlgorithmException{

		//
		String sql = "select count(*) as cant FROM public.testtb;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		int diff = -1;
		
		if(result.next()) {
			diff = (int) result.getDouble("cant");
			
		}
		
		return diff;
		
	}
	
	@PostMapping(value="/person/save")
	public int savePerson(@RequestBody PersonRequest person) throws NoSuchAlgorithmException{

		//
		String sql = "select count(*) as cant FROM public.testtb;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		int diff = -1;
		
		if(result.next()) {
			diff = (int) result.getDouble("cant");
			
		}
		
		return diff;
		
	}
	
	@GetMapping(value="/person/list/")
	public int listPerson(@RequestParam(name="name", required = false, defaultValue = "Ahmed") String name) throws NoSuchAlgorithmException{

		//
		String sql = "select count(*) as cant FROM public.testtb;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		int diff = -1;
		
		if(result.next()) {
			diff = (int) result.getDouble("cant");
			
		}
		
		return diff;
		
	}
	
	/*
	 * @RequestMapping(method = RequestMethod.GET) =>>>> @GetMapping
	 * @RequestMapping(
		  value = "/ex/foos", 
		  headers = { "key1=val1", "key2=val2" },
		  method = GET, 
		  headers = "Accept=application/json")
	 */
	@GetMapping("/person/list2/{name}")
	public String listPerson2(@PathVariable("name") String name)
	{
	    return "Hello " + name;
	}
	
	@GetMapping("/person/list2/")
	public String listPersonWithoutName()
	{
	    return "Hello nadie";
	}
	
	
}
