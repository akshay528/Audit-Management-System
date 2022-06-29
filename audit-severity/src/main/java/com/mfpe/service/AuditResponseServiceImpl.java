package com.mfpe.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfpe.model.AuditBenchmark;
import com.mfpe.model.AuditResponse;
import com.mfpe.repository.AuditResponseRepo;
import com.mfpe.model.AuditQuestion;
import com.mfpe.model.AuditRequest;

@Service
public class AuditResponseServiceImpl implements AuditResponseService{
	
	@Autowired
	private AuditResponseRepo auditResponseRepo;
	
	Logger logger = LoggerFactory.getLogger("Severity-Response-Calculation");
	
	//This method is to check the audit responses with the audit type
	public AuditResponse createAuditResponse(int acceptableNo,List<AuditQuestion> questions) {
		
		String auditType = questions.get(0).getAuditType();
		int actualNo = countNos(questions);
		AuditResponse auditResponse = new AuditResponse();
		
		logger.info(String.format("Audit-type : %s, Actual-Nos : %d, Acceptable Nos : %d", auditType, actualNo, acceptableNo));
		
		/* Here we check actual condition of the severity microservice
		*  Determines the project execution status and the remediation duration detail
		*/
		if(actualNo <= acceptableNo && auditType.equalsIgnoreCase("Internal")) {
			auditResponse.setProjectExecutionStatus("GREEN");
			auditResponse.setRemedialActionDuration("No action needed");
		}
		else if(actualNo > acceptableNo && auditType.equalsIgnoreCase("Internal")) {
			auditResponse.setProjectExecutionStatus("RED");
			auditResponse.setRemedialActionDuration("Action to be taken in 2 weeks");
		}
		else if(actualNo <= acceptableNo && auditType.equalsIgnoreCase("SOX")) {
			auditResponse.setProjectExecutionStatus("GREEN");
			auditResponse.setRemedialActionDuration("No action needed");
		}
		else{
			auditResponse.setProjectExecutionStatus("RED");
			auditResponse.setRemedialActionDuration("Action to be taken in 1 week");
		}
		
		return auditResponse;
	}
	
	//This  method is to count the number of No's
	public int countNos(List<AuditQuestion> questions) {
		
		int count = 0;
		for(AuditQuestion q:questions) {
			if(q.getResponse().equalsIgnoreCase("No")) {
				count++;
			}
		}
		return count;		
	}

	@Override
	public AuditResponse getAuditResponse(List<AuditBenchmark> benchmarkList,String auditType, List<AuditQuestion> questionResponses) {
		// TODO Auto-generated method stub
		int acceptableNo = 0;
		for(AuditBenchmark ab : benchmarkList) {
			if(ab.getAuditType().equalsIgnoreCase(auditType)) {
				acceptableNo = ab.getBenchmarkNoAnswers();
			}
		}
		
		AuditResponse auditResponse = createAuditResponse(acceptableNo,questionResponses);
		//saving to the repo
		//auditResponseRepo.save(auditResponse);
		return auditResponse;
	}

	@Override
	public AuditResponse saveAuditResponse(AuditResponse auditResponse, AuditRequest auditRequest) {
		// TODO Auto-generated method stub
		// setting project name and manager name
		auditResponse.setProjectName(auditRequest.getProjectName());
		auditResponse.setManagerName(auditRequest.getManagerName());
		auditResponse.setCreationDateTime(new Date());
		logger.info("Saving Audit-Response in DB :: " + auditResponse);
		
		return auditResponseRepo.save(auditResponse);
	}	
	
}
