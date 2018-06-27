package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentController {

	@RequestMapping(value = "/Students")
	public String listSchoolClass(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());

		return "studentsList";
	}

	@RequestMapping(value = "/AddStudent")
	public String displayAddSchoolClassForm(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());

		return "studentForm";
	}

	@RequestMapping(value = "/Edit")// bylo edit student
	public String displayEditStudent(
			@RequestParam(value = "studentId", required = false) long studentId,
			@RequestParam(value = "studentSurname", required = false) String editSurname,
			@RequestParam(value = "studentPesel", required = false) String editPesel,
			@RequestParam(value = "studentName", required = false) String editName,
		//	@RequestParam(value = "student", required = false) String editSchoolClassId, 
			Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("studentId", studentId);
		model.addAttribute("studentSurname", editSurname);
		model.addAttribute("studentPesel", editPesel);
		model.addAttribute("studentName", editName);
	//	model.addAttribute("student", editSchoolClassId);
		return "studentEditForm";
	}

	

	/*@RequestMapping(value = "/EditStudent", method = RequestMethod.POST)
	public String editStudent(
			@RequestParam(value = "studentSurname", required = false) String surname,
			@RequestParam(value = "studentPesel", required = false) String pesel,
			@RequestParam(value = "studentName", required = false) String name,
			//@RequestParam(value = "student", required = false) String schoolClassId, 
			Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		Student student = new Student();
		student.setSurname(surname);
		student.setPesel(pesel);
		student.setName(name);

		DatabaseConnector.getInstance().editStudent(student);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "student został zedytowanyana");

		return "studentsList";
	}*/
	
	
	
	
	@RequestMapping(value = "/CreateStudent", method = RequestMethod.POST)
	public String createSchoolClass(@RequestParam(value = "studentSurname", required = false) String surname,
			@RequestParam(value = "studentPesel", required = false) String pesel,
			@RequestParam(value = "studentName", required = false) String name,
			@RequestParam(value = "student", required = false) String schoolClassId, Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		Student student = new Student();
		student.setSurname(surname);
		student.setPesel(pesel);
		student.setName(name);

		DatabaseConnector.getInstance().addStudent(student, schoolClassId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Nowa student została dodana");

		return "studentsList";
	}

	@RequestMapping(value = "/DeleteStudent", method = RequestMethod.POST)
	public String deleteStudent(@RequestParam(value = "studentId", required = false) String studentId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().deleteStudent(studentId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "student została usunięta");

		return "studentsList";
	}

}