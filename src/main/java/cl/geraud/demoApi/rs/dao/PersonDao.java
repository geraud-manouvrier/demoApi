package cl.geraud.demoApi.rs.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cl.geraud.demoApi.rs.model.PersonDb;

@Repository
public class PersonDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PersonDb getPersonFromDbByRut(String rut) {
		PersonDb personDb=new PersonDb();
		
		
		int rutInt;
		char rutDv;
		//rutTemp=Integer.parseInt(rut);
		rutInt=Integer.parseInt(rut.split("-")[0]);
		rutDv=rut.split("-")[1].charAt(0);
		String sql = "SELECT rutint, rutdv, nameperson, lastname, age, course FROM public.person WHERE rutint=? and rutdv=?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql,rutInt, rutDv);
		
		if(result.next()) {
			personDb.setRutInt(result.getInt("rutint"));
			personDb.setRutDv(result.getString("rutdv").charAt(0));
			personDb.setName(result.getString("nameperson"));
			personDb.setLastName(result.getString("lastname"));
			personDb.setAge(result.getInt("age"));
			personDb.setCourse(result.getString("course"));
			
		}
		
		return personDb;
	}
	
	public List<PersonDb> getPersons() {

		List<PersonDb> personsDb = new ArrayList<PersonDb>();
		
		
		String sql = "SELECT rutint, rutdv, nameperson, lastname, age, course FROM public.person ORDER BY rutint ASC;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		while(result.next()) {
			PersonDb personDb=new PersonDb();
			personDb.setRutInt(result.getInt("rutint"));
			personDb.setRutDv(result.getString("rutdv").charAt(0));
			personDb.setName(result.getString("nameperson"));
			personDb.setLastName(result.getString("lastname"));
			personDb.setAge(result.getInt("age"));
			personDb.setCourse(result.getString("course"));
			personsDb.add(personDb);
			
		}
		
		return personsDb;
	}
	
	public boolean savePerson(PersonDb person) /* throws Exception */ {
		
		int affectedRows;
		String sql = "INSERT INTO public.person(rutint, rutdv, nameperson, lastname, age, course) VALUES (?, ?, ?, ?, ?, ?); ";
		affectedRows=jdbcTemplate.update(sql, person.getRutInt(), person.getRutDv(), 
								person.getName(), person.getLastName(), 
								person.getAge(), person.getCourse());
		if (affectedRows==0)
			return false;
		else
			return true;
	}
	
	public boolean updatePersinByRut(PersonDb person) {
		
		int affectedRows;
		/*
		MapSqlParameterSource mapaSql=new MapSqlParameterSource();
		String dynSql="";
		
		if (person.getName() != null) {
			dynSql=dynSql+" nameperson = :name -";
			mapaSql.addValue("name", person.getName(), Types.VARCHAR);
		}
		
		if (person.getLastName() != null) {
			dynSql=dynSql+" lastname = :lastName -";
			mapaSql.addValue("lastName", person.getLastName(), Types.VARCHAR);
		}
		
		if (person.getAge()!=0) {
			dynSql=dynSql+" age = :age -";
			mapaSql.addValue("age", person.getAge(), Types.TINYINT);
		}
		
		if (person.getCourse() != null) {
			dynSql=dynSql+" course = :course -";
			mapaSql.addValue("course", person.getCourse(), Types.VARCHAR);
		}
		
		System.out.println(dynSql);
		dynSql=dynSql.replace(" - ", ", ");
		System.out.println(dynSql);
		dynSql=dynSql.replace(" -", "");
		System.out.println(dynSql);
		
		if (dynSql!="") {
			//Update DB
			dynSql="UPDATE public.person SET "+dynSql+" WHERE rutint = :rutInt";
			System.out.println(dynSql);
			//SqlParameter p1=new SqlParameter(Types.INTEGER);
			
			//NamedParameterStatement p = new NamedParameterStatement(dataSourceDemoApi.getConnection(), dynSql);
			//PreparedStatement p=dataSourceDemoApi.getConnection().prepareStatement(dynSql);
			
			mapaSql.addValue("rutInt", person.getRutInt(), Types.INTEGER);
			//mapaSql.addValue("rutDv", person.getRutDv(), Types.CHAR);
			
			
			affectedRows=jdbcTemplate.update(dynSql,mapaSql);
		}
		else {
			System.out.println("No hay campos para actualizar");
			affectedRows=0;
		}
		*/
		
		
		String sql = "UPDATE public.person SET rutint=?, rutdv=?, "+
						"nameperson=?, lastname=?, "+
						"age=?, course=? "+
						"WHERE rutint=? and rutdv=?;";
		
		Integer intTemp=null;
		if (person.getAge()!=0)
			intTemp=person.getAge();
		sql = "UPDATE public.person SET "+
				"nameperson=COALESCE(?,nameperson), lastname=COALESCE(?,lastname), "+
				"age=COALESCE(?, age), course=COALESCE(?,course) "+
				"WHERE rutint=? and rutdv=?;";
		
		affectedRows=jdbcTemplate.update(sql, 
								person.getName(), person.getLastName(), 
								intTemp, person.getCourse(), 
								person.getRutInt(), person.getRutDv());
		System.out.println(sql);
		
		if (affectedRows==0)
			return false;
		else
			return true;
	}
	
	
	
	public boolean deletePersonByRut(String rut) {
		
		int rutInt;
		char rutDv;
		int affectedRows;
		
		rutInt		= Integer.parseInt(rut.split("-")[0]);
		rutDv		= rut.split("-")[1].charAt(0);
		String sql	= "delete from public.person where rutint=? and rutdv=?;";
		affectedRows= jdbcTemplate.update(sql,rutInt, rutDv);
		
		if (affectedRows==0)
			return false;
		else
			return true;

	}
	

}
