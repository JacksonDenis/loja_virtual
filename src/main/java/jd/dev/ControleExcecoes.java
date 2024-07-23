package jd.dev;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jd.dev.model.dto.ObjetoErroDTO;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ExceptionMentoriaJava.class)
	public ResponseEntity<Object> handleExceptionCustom (ExceptionMentoriaJava ex) {
		
		ObjetoErroDTO dto = new ObjetoErroDTO();
		
		dto.setError(ex.getMessage());
		dto.setCode(HttpStatus.OK.toString());
		
		return new ResponseEntity<Object> (dto, HttpStatus.OK);
		
	}
	
	@ExceptionHandler({Exception.class,RuntimeException.class, Throwable.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ObjetoErroDTO dto = new ObjetoErroDTO();
		
		String msg = "";
		
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for(ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else if (ex instanceof HttpMessageNotReadableException) {
			msg = "Não este sendo enviado dados para o BODY corpo da requisicao";
		} else {
			msg = ex.getMessage();
		}
		
		dto.setError(msg);
		dto.setCode(status.value() + " ==> " + status.getReasonPhrase() );
		ex.printStackTrace();
		
		return new ResponseEntity<Object>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegry (Exception ex) {
		ObjetoErroDTO dto = new ObjetoErroDTO();
		
		String msg = "";
		
		if (ex instanceof SQLException) {
			msg ="Erro de SQL do banco" + ((SQLException) ex).getCause().getCause().getMessage();
		} else {
			msg = ex.getMessage();
		}
		
		if (ex instanceof ConstraintViolationException) {
			msg ="Erro de Chave estrangeira" + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		} else {
			msg = ex.getMessage();
		}
		
		
		if (ex instanceof DataIntegrityViolationException) {
			msg ="Erro de integridade do banco" + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		} else {
			msg = ex.getMessage();
		}
		
		
		
		dto.setError(msg);
		dto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString() );
		ex.printStackTrace();
		
		return new ResponseEntity<Object>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
