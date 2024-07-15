import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import com.student_grade.dto.GradeDTO;
import com.student_grade.repository.MarkRepository;
import com.student_grade.controller.MarksController;
import com.student_grade.service.MarkService;
import com.student_grade.util.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GradeControllerTestClass {

    @Mock
    MarkService markService;
    @InjectMocks
    MarksController marksController;

    @Mock
    MarkRepository markRepository;

    @Test
    @DisplayName("Успешное выполнение post запроса по адресу 'api/marks/create'")
    public void validTest_createMark() {
        Student student = new Student();
        student.setId(1L);
        Subject subject = new Subject();
        subject.setId(1L);
        GradeDTO gradeDTO = new GradeDTO(student, subject, 88);

        ResponseEntity<?> response = marksController.create(gradeDTO);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(
                "Успешно создано",
                response.getBody());

    }

    @Test
    @DisplayName("Безуспешное выполнение post запроса по адресу 'api/marks/create'")
    public void invalidTest_createMark() {
        Student student = new Student();
        student.setId(1L);
        Subject subject = new Subject();
        subject.setId(1L);
        GradeDTO gradeDTO = new GradeDTO(student, subject, 88);
        doThrow(new NotFoundException("Студент или преподаватель не найдены")).when(markService).create(gradeDTO);
        ResponseEntity<?> response = marksController.create(gradeDTO);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(
                "Студент или преподаватель не найдены",
                response.getBody());

    }
}
