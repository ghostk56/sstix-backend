package com.sstixbackend.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {
//
//	protected final Logger _log = LoggerFactory.getLogger(getClass());
//
//	private static final String ERROR_PROPERTY_FORMAT = "%1$s-%2$s";

//	private static final List<String> ignoreErrorTypeCodeList = ImmutableList.of("40101");
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception error) {
		System.out.println("handleException");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
	}
	/**
	 * 
	 * @param error
	 * @return
	 */
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public <T> RestfulResponse<T> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
//		MDC.remove("requestId");
//		_log.error(error.getMessage(), error);
//
//		StringBuilder errorFields = new StringBuilder();
//		List<FieldError> fieldErrors = error.getBindingResult().getFieldErrors();
//		for (FieldError fieldError : fieldErrors) {
//			if (errorFields.length() > 0) {
//				errorFields.append(", ");
//			}
//			errorFields.append(
//					String.format(ERROR_PROPERTY_FORMAT, fieldError.getField(), fieldError.getDefaultMessage()));
//		}
//
//		_log.error("參數值驗證有誤 : " + errorFields.toString());
//
//		// 回傳錯誤訊息
//		return new RestfulResponse<T>(RestfulResponse.ERROR_CODE, "參數值驗證有誤 : " + errorFields.toString(),
//				"參數值驗證有誤 : " + errorFields.toString());
//	}

	/**
	 * 處理RiamsException 例外處理
	 * HIGH: Error
	 * MEDIUM/LOW: Info
	 * WARNING: deBug
	 * 
	 * @param error
	 * @return
	 */
//	@ExceptionHandler(RiamsException.class)
//	public <T> RestfulResponse<T> handleRiamsException(RiamsException error) {
//		MDC.remove("requestId");
//		if (!ignoreErrorTypeCodeList.contains(error.getErrorType().getErrorCode())) {
//			
//			if (ErrorLevel.WARNING.equals(error.getErrorType().getErrorLevel())) {
//				_log.debug(error.getMessage());
//			} else if(ErrorLevel.LOW.equals(error.getErrorType().getErrorLevel()) || ErrorLevel.MEDIUM.equals(error.getErrorType().getErrorLevel())) {
//				_log.info(error.getMessage(), error);
//			} else if(ErrorLevel.HIGH.equals(error.getErrorType().getErrorLevel())) {
//				_log.error(error.getMessage(), error);
//			}
//		}
//		
//		// 回傳錯誤訊息
//		return new RestfulResponse<T>(error.getErrorType().getErrorCode(), error.getMessage(),
//				error.getDisplayErrorMessage());
//	}
//
//	/**
//	 * 處理RiamsException 例外處理
//	 * 
//	 * @param error
//	 * @return
//	 */
//	@ExceptionHandler(QueryTimeoutException.class)
//	public <T> RestfulResponse<T> handleRiamsException(QueryTimeoutException error) {
//		MDC.remove("requestId");
//		// 回傳錯誤訊息
//		return new RestfulResponse<T>(RiamsErrorType.RIAMS_DB_QUERY_TIME_OUT.getErrorCode(), "資料庫查詢時間逾時", "系統處理逾時");
//	}
//
//	/**
//	 * 用於驗證使用者發送資訊 ConstraintViolationException
//	 * 
//	 * @return
//	 */
//	@ExceptionHandler(ConstraintViolationException.class)
//	public <T> RestfulResponse<T> formatErrorResponse(ConstraintViolationException error) {
//		
//		MDC.remove("requestId");
//		_log.error(error.getMessage(), error);
//
//		StringBuilder errorFields = new StringBuilder();
//		
//		Set<ConstraintViolation<?>> violations = error.getConstraintViolations();
//        for (ConstraintViolation<?> violation : violations ) {
//        	errorFields.append(violation.getMessage());
//        	errorFields.append(", ");
//        }
//		
//
//		_log.error("參數值驗證有誤 : " + errorFields.toString());
//
//		// 回傳錯誤訊息
//		return new RestfulResponse<T>(RestfulResponse.ERROR_CODE, "參數值驗證有誤 : " + errorFields.toString(),
//				"參數值驗證有誤 : " + errorFields.toString());
//
//	}
//
//	public FileAttachmentVo[] setFileInfo(MultipartFile[] files) {
//		FileAttachmentVo[] fileAttachmentVoArray = new FileAttachmentVo[files.length];
//		for (int i = 0; i < files.length; i++) {
//			fileAttachmentVoArray[i] = new FileAttachmentVo();
//			try {
//				fileAttachmentVoArray[i].setFileInfo(files[i].getBytes(),files[i].getOriginalFilename(),files[i].getContentType(),files[i].getSize());
//			} catch (IOException e) {
//				_log.error("包裝附檔資訊失敗" );
//				throw new RiamsException(RiamsErrorType.RIAMS_FILE_UPLOAD_FAIL, "包裝附檔資訊失敗");
//			}
//		}
//		return fileAttachmentVoArray;
//	}
//	 /**
//	  * 處理T 例外處理
//	  * 
//	  * @param error
//	  * @return
//	  */
//	 @ExceptionHandler(Exception.class)
//	 public <T> RestfulResponse<T> handleException(Exception error) {
//	  MDC.remove("requestId");
//	  _log.error(error.getMessage(), error);
//
//	  String displayMessage = (error.getCause() != null ? error.getCause().getMessage() : error.getMessage());
//
//	  String message = error.getMessage();
//	  Exception ex = error;
//	  while (ex != null) {
//	   if (ex instanceof RiamsException) {
//	    message = ((RiamsException) ex).getDisplayErrorMessage();
//	    break;
//	   }
//	   ex = (Exception) ex.getCause();
//	  }
//
//	  // 回傳錯誤訊息
//	  return new RestfulResponse<T>(RestfulResponse.ERROR_CODE, "錯誤:" + displayMessage, "系統內部發生錯誤");
//
//	 }
	
	
}
