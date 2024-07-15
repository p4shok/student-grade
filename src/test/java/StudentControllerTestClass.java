import com.student_grade.model.Mark;
import com.student_grade.model.MarksKey;
import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import com.student_grade.dto.StudentWithMarks;
import com.student_grade.dto.StudentDTO;
import com.student_grade.repository.StudentRepository;
import com.student_grade.controller.StudentResource;
import com.student_grade.service.StudentService;
import com.student_grade.util.AlreadyExistsException;
import com.student_grade.util.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/*
Тестирование контроллера для обработки запросов для работы с сущностью студент
 */
@ExtendWith(MockitoExtension.class)
public class StudentControllerTestClass {

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentResource studentResource;

    @Test
    @DisplayName("Успешное решение для get запроса /api/students/rate")
    void validTest_GetAllStudents() {
        List<StudentDTO> expectedResult = List.of(
                new StudentDTO(
                        0L, 1L, "Петров Петр Петрович", 21,
                        "gggg@gmail.com", "89099999"
                ),
                new StudentDTO(
                        1L, 1L, "Иванов Иван Иванович", 22,
                        "ivan@gmail.com", "8994224"
                )
        );
        doReturn(expectedResult).when(this.studentService).findAll();

        ResponseEntity<?> response = studentResource.getAllStudents();

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResult, response.getBody());
        Logger logger = LoggerFactory.getLogger(StudentControllerTestClass.class);
        logger.info(response.toString());
    }

    @Test
    @DisplayName("Успешное решение для get запроса /api/students/{fullname}")
    void validTest_getByFullname() {
        String studentName = "Петров Петр Петрович";
        Student student = new Student(
                0L, 1L, studentName, 21,
                "gggg@gmail.com", "89099999", new LinkedList<Mark>()
        );
        Subject subject = new Subject(1L, 1L, "Русский язык");
        StudentWithMarks expectedResult = new StudentWithMarks(
                student,
                List.of(),
                List.of(new Mark(new MarksKey(), student, subject, 50))
        );
        doReturn(expectedResult).when(this.studentService).
                getStudentByFullname(studentName);
        ResponseEntity<?> response = studentResource.getByFullname(studentName);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResult, response.getBody());
    }

    @Test
    @DisplayName("Неуспешное выполнение для get запроса /api/students/ауиаоуиоп")
    void invalidTest_getByFullname() {
        String fullname = "ауиаоуиоп";
        doThrow(
                new NotFoundException("Пользователь с именем: " + fullname + " не найден"))
                .when(this.studentService)
                .getStudentByFullname(fullname);
        ResponseEntity<?> response = studentResource.getByFullname(fullname);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(
                "Пользователь с именем: " + fullname + " не найден",
                response.getBody());
    }

    @Test
    @DisplayName("Успешное выполнение для post запроса /api/students/")
    void validTest_createStudent() {
        StudentDTO createdStudent = new StudentDTO(
                1L, 1L, "Иванов Иван Иванович", 22,
                "ivan@gmail.com", "8994224"
        );
        try {
            doReturn(
                    1L)
                    .when(this.studentService)
                    .create(createdStudent);
        } catch (AlreadyExistsException e) {

        }
        ResponseEntity<?> response = studentResource.createStudent(createdStudent);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Студент успешно создан, его id: " + 1, response.getBody());
    }

    @Test
    @DisplayName("Неуспешное выполнение для post запроса /api/students/")
    void invalidTest_createStudent() {
        StudentDTO createdStudent = new StudentDTO(
                1L, 1L, "Иванов Иван Иванович", 22,
                "ivan@gmail.com", "8994224"
        );

        try {
            doThrow(
                    new AlreadyExistsException(
                            "Пользователь с такими данными уже существует"
                    ))
                    .when(this.studentService)
                    .create(createdStudent);
            ResponseEntity<?> response = studentResource.createStudent(createdStudent);
            assertNotNull(response);
            assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            assertEquals("Пользователь с такими данными уже существует", response.getBody());
        } catch (AlreadyExistsException e) {

        }

    }

    @Test
    @DisplayName("Успешное выполнение для put запроса /api/students/")
    void validTest_updateStudent() {
        StudentDTO createdStudent = new StudentDTO(
                1L, 1L, "Иванов Иван Иванович", 22,
                "ivan@gmail.com", "8994224"
        );

        StudentDTO updated = new StudentDTO(
                2L, 1L, "Иванов Иван Иванович", 22,
                "2222", "8994224"
        );

        ResponseEntity<?> response = studentResource.updateStudent(2L, updated);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Неуспешное выполнение для put запроса /api/students/")
    void invalidTest_updateStudent() {

        StudentDTO updated = new StudentDTO(
                2L, 1L, "Иванов Иван Иванович", 22,
                "2222", "8994224"
        );
        doThrow(new NotFoundException("Операция завершена неудачно")).when(studentService).update(2L, updated);
        ResponseEntity<?> response = studentResource.updateStudent(2L, updated);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals("Операция завершена неудачно", response.getBody());
    }

    @Test
    @DisplayName("Успешное выполнение для delete запроса /api/students/")
    void validTest_deleteStudent() {

        StudentDTO updated = new StudentDTO(
                2L, 1L, "Иванов Иван Иванович", 22,
                "2222", "8994224"
        );
        //doThrow(new NotFoundException("Операция завершена неудачно")).when(studentService).delete(2L);
        ResponseEntity<?> response = studentResource.deleteStudent(2L);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Студент успешно удален", response.getBody());
    }

    @Test
    @DisplayName("Безуспешное выполнение для delete запроса /api/students/")
    void invalidTest_deleteStudent() {

        StudentDTO updated = new StudentDTO(
                2L, 1L, "Иванов Иван Иванович", 22,
                "2222", "8994224"
        );
        doThrow(new NotFoundException("Операция завершена неудачно")).when(studentService).delete(2L);
        ResponseEntity<?> response = studentResource.deleteStudent(2L);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals("Операция завершена неудачно", response.getBody());
    }

}
