
package com.codic.studentmanagementsystem.controller;

import com.codic.studentmanagementsystem.model.Student;
import com.codic.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("student", new Student());
        return "students";
    }

    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student,
                              RedirectAttributes redirectAttributes) {
        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("message",
                "Student saved successfully!");
        return "redirect:/";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        studentRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message",
                "Student deleted successfully!");
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid student ID: " + id));

        model.addAttribute("student", student);
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("editMode", true);
        return "students";
    }
}
