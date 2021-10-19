package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import dataAccess.DataAccessSaez2;
import domain.Equipo;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import exceptions.PrognosticAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.WrongParameters;
import test.utility.TestUtilityDataAccess;

class CreatePrognosticTeamDATestSaez2Test {
	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static DataAccessSaez2 testDA = new DataAccessSaez2();
	
	

	Equipo eq1= new Equipo("equipo1", 2021);
	Equipo eq2= new Equipo("equipo2", 2021);
	private Question q;
	private Event ev;
	private Event evbd=null;
	private Question qbd=null;

	

	
	
	@Test
	@DisplayName("Test 1:No existe ninguna pregunta en el evento")
	void testCreatePrognostic1()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			Question q= new Question("query", 15, ev, true);
			
			

		
			Question que= new Question("pregunta", 15, ev, true);
			assertNull(testDA.createPrognosticTeam(evbd.getEventNumber(), que, eq1, 50));
		} catch (ParseException e1) {
			fail();
		} catch (PrognosticAlreadyExist e) {
			fail();;
		} catch (WrongParameters e) {
			fail();
		}

			
			
		
		finally {


			testDA.removeEvent(evbd);
		}
		
	
		
		
	}
	
	@Test
	@DisplayName("Test 2:solo hay 1 pregunta y no es la que buscamos")
	void testCreatePrognostic2()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
	
		try {


			
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			

			testDA.createQuestion(evbd, "query", 15, true);

			
				Question que= new Question("pregunta", 15, ev, true);
				assertNull(testDA.createPrognosticTeam(evbd.getEventNumber(), que, eq1, 50));
			} catch (PrognosticAlreadyExist e) {
				fail();
			} catch (WrongParameters e) {
				fail();
			} catch (ParseException e) {
				fail();
			} catch (QuestionAlreadyExist e) {
				fail();
			}
			
		

	
		finally {

			testDA.removeEvent(evbd);
		}

		
	}
	/**
	@Test
	@DisplayName("Test 3:Ya existe nuestro pronostico")
	void testCreatePrognostic3()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
		
			
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			

			qbd= testDA.createQuestion(evbd, "query", 15, true);
			testDA.createPrognosticTeam(evbd.getEventNumber(), qbd, eq1, 15);

			assertThrows(PrognosticAlreadyExist.class, 
					()->testDA.createPrognosticTeam(evbd.getEventNumber(), qbd, eq1, 15));
		

		} catch (ParseException e1) {
			fail();
		} catch (QuestionAlreadyExist e) {
			fail();
			e.printStackTrace();
		} catch (WrongParameters e) {
			fail();
		} catch (PrognosticAlreadyExist e) {
			fail();
		}
		finally {
			
			testDA.removeEvent(evbd);
		}

	}
	
	
	@Test
	@DisplayName("Test 4:No existe el pronostico que queremos meter")
	void testCreatePrognostic4()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
		
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			

			qbd=testDA.createQuestion(evbd, "query", 15, true);
			float porcen= 50;
			
				Pronosticos pro = testDA.createPrognosticTeam(evbd.getEventNumber(), qbd, eq1, porcen);
				System.out.println(pro);
				assertEquals(eq1,pro.getEq());
				assertEquals(porcen,pro.getPorcentaje());
				assertEquals(qbd,pro.getQuestion());
			} catch (PrognosticAlreadyExist e) {
				fail();
			} catch (WrongParameters e) {
				fail();
			} catch (ParseException e) {
				fail();
			} catch (QuestionAlreadyExist e) {
				fail();
			}
			
		

	
		finally {
			testDA.removeEvent(evbd);
		}

		
	}
	*/
	
	@Test
	@DisplayName("Test 5:Numero de evento erroneo")
	void testCreatePrognostic5()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
			oneDate = sdf.parse("05/10/2022");

		
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			Question q= new Question("query", 15, ev, true);
		
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(-1, q, eq1, 15));

		} catch (ParseException e) {
			fail();
		}

		
		finally {
			testDA.removeEvent(evbd);
		}
	}
	@Test
	@DisplayName("Test 6:Numero de evento no existe")
	void testCreatePrognostic6()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);

	
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, null, eq1, 15));
			
		} catch (ParseException e1) {
			fail();
		}

		
		finally {
			testDA.removeEvent(evbd);
		}
	}
	
	@Test
	@DisplayName("Test 7:Equipo=null")
	void testCreatePrognostic7()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			Question q= new Question("query", 15, ev, true);

			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, q, null, 15));

		} catch (ParseException e1) {
			fail();
		}

		
		finally {
			testDA.removeEvent(evbd);
		}
	}
	
	@Test
	@DisplayName("Test 8:Porcentaje negativo")
	void testCreatePrognostic8()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		evbd=null;
		try {
			oneDate = sdf.parse("05/10/2022");

			
			evbd = testDA.addEvent("eventoPrueba",oneDate, eq1,eq2);
			Question q= new Question("query", 15, ev, true);

			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, q, eq1, -1));
		} catch (ParseException e1) {
			fail();
		}

		
		finally {
			testDA.removeEvent(evbd);
		}
	}
	
		


}
