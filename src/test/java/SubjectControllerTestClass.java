import com.student_grade.controller.SubjectController;
import com.student_grade.service.SubjectService;
import com.student_grade.util.NotFoundException;
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
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class SubjectControllerTestClass {

    @Mock
    SubjectService subjectService;

    @InjectMocks
    SubjectController subjectController;

    @Test
    @DisplayName("Успешное выполнение для delete запроса /api/subjects/")
    void validTest_deleteStudent() {
        ResponseEntity<?> response = subjectController.delete(2L);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Предмет успешно удален", response.getBody());
    }

    @Test
    @DisplayName("Безуспешное выполнение для delete запроса /api/students/")
    void invalidTest_deleteStudent() {
        doThrow(new NotFoundException("Операция завершена неудачно")).when(subjectService).delete(2L);
        ResponseEntity<?> response = subjectController.delete(2L);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals("Операция завершена неудачно", response.getBody());
    }
}
