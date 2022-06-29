package com.mfpe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfpe.feign.AuditBenchmarkFeign;
import com.mfpe.model.AuditBenchmark;
import com.mfpe.model.AuditRequest;
import com.mfpe.model.AuditResponse;
import com.mfpe.model.AuditType;
import com.mfpe.model.AuditQuestion;
import com.mfpe.service.AuditResponseService;
import com.mfpe.service.AuthorizationService;

@RestController
@RequestMapping("/severity")
@CrossOrigin(origins = "*")
public class AuditSeverityController {
	
	@Autowired
	private AuditBenchmarkFeign auditBenchmarkFeign;
	
	@Autowired
	private AuditResponseService auditResponseService;
	
	@Autowired
	private AuthorizationService authorizationService;

	Logger logger = LoggerFactory.getLogger("Severity-Controller-Logger");

	//This is to check the microservice is working or not 
	@GetMapping("/health-check")
	public String healthCheck() {
		return "Audit Severity Microservice is Active";
	}
	
	//This is to check the severity of the audit and it returns the execution status of the project
	@PostMapping("/ProjectExecutionStatus")
	public ResponseEntity<?> auditSeverity(@RequestHeader("Authorization") String jwt,
				@RequestBody AuditRequest auditRequest){

		AuditResponse auditResponse = new AuditResponse();
		
		ResponseEntity<AuditResponse> response= null;
		
		// checking if the jwt is valid or not
		// creating auditResponse according to auditRequest
		if(jwt.length()>0 && authorizationService.validateJwt(jwt)) {				
			logger.info("Successfully validated the JWT :: " + jwt);
			
			// getting benchmark list from Benchmark-MS
			List<AuditBenchmark> benchmarkList = auditBenchmarkFeign.getAuditBenchmark(jwt);
			
			AuditType auditType = new AuditType();
			
			auditType.setAuditType(auditRequest.getAuditDetail().getAuditType());	// setting auditType
			
			// getting responses back from RequestBody
			List<AuditQuestion> questionResponses = auditRequest.getAuditDetail().getAuditQuestions();
			
			// create Audit-response
			auditResponse = auditResponseService.getAuditResponse(benchmarkList,auditType.getAuditType(), questionResponses);

			// saving response in DB
			auditResponse = auditResponseService.saveAuditResponse(auditResponse,auditRequest);
			
			logger.info("AuditResponse successfully created!!");
			
			response = new ResponseEntity<AuditResponse>(auditResponse, HttpStatus.OK);
		}
		else {
			logger.error("Failed to validate the JWT :: " + jwt);
			response = new ResponseEntity<>(auditResponse,HttpStatus.FORBIDDEN);
		}
		return response;
		
	}
}
