package com.mfpe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mfpe.feign.AuditBenchmarkFeign;
import com.mfpe.model.AuditBenchmark;
import com.mfpe.model.AuditDetail;
import com.mfpe.model.AuditQuestion;
import com.mfpe.model.AuditRequest;
import com.mfpe.model.AuditResponse;
import com.mfpe.service.AuditResponseService;
import com.mfpe.service.AuthorizationService;

@SpringBootTest
class AuditSeverityControllerTests {
	
	@Mock
	AuditResponseService auditResponseService;
	
	@Mock
	AuthorizationService authorizationService;
	
	@Mock
	AuditBenchmarkFeign auditBenchmarkFeign;
	
	@InjectMocks
	AuditSeverityController severityController;
	
		
	@Test
	public void testSeverityController() throws Exception {
		assertThat(severityController).isNotNull();
	}
	
	@Test
	public void testAuditSeverityStatus() {
		
		Date date = new Date();
		
		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1,"Internal",3));
		
		List<AuditQuestion> questionResponseList = new ArrayList<>();
		questionResponseList.add(new AuditQuestion(1,"question","Internal","YES"));
		
		AuditRequest auditRequest = new AuditRequest("ProjectName","ManagerName",new AuditDetail("Internal",date,questionResponseList));
		
		AuditResponse auditResponse = new AuditResponse(1,"ManagerName","ProjectName", new Date(),"ProjectExecutionStatus","RemedialActionDuration");
		
		when(authorizationService.validateJwt("jwt")).thenReturn(true);
		
		when(auditBenchmarkFeign.getAuditBenchmark("jwt")).thenReturn(benchmarkList);
		
		when(auditResponseService.getAuditResponse(benchmarkList,"Internal", questionResponseList)).thenReturn(auditResponse);
		
		when(auditResponseService.saveAuditResponse(auditResponse,auditRequest))
			.thenReturn(auditResponse);
		
		
		ResponseEntity<AuditResponse> expectedResponse = new ResponseEntity<AuditResponse>(auditResponse, HttpStatus.OK);
		
		assertEquals(expectedResponse,severityController.auditSeverity("jwt",auditRequest));
	}
	
	
	@Test
	public void testAuditHealthCheck() {
		assertEquals(severityController.healthCheck(),"Audit Severity Microservice is Active");
	}
	
	
	
}
