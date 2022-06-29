package com.mfpe;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class AuditSeverityApplicationTests {	
	
	@Autowired
	AuditSeverityApplication auditSeverityApplication;
	
	@Test
	public void contextLoads(){
		assertNotNull(auditSeverityApplication);
	}

}
