package my.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hk.gov.lad.webservices.type.ESubmissionBatchType;
import hk.gov.lad.webservices.type.ESubmissionListType;
import hk.gov.lad.webservices.type.LAPSubmissionRequestDocument;
import hk.gov.lad.webservices.type.LAPSubmissionResponseDocument;

import javax.annotation.security.PermitAll;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless(name = "MyEJB")
@TransactionManagement(TransactionManagementType.BEAN)
@PermitAll
public class MyEJB extends EJBBase implements IMyEJB, IMyEJBLocal {
	public LAPSubmissionResponseDocument getLAPSubmission(LAPSubmissionRequestDocument lAPSubmissionRequest)
			throws EJBException {
		LAPSubmissionResponseDocument outDoc = LAPSubmissionResponseDocument.Factory.newInstance();
		ESubmissionBatchType esubBatchType = outDoc.addNewLAPSubmissionResponse().addNewOut();
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();

		esubBatchType.setFromDateTime(fromDate);
		esubBatchType.setToDateTime(toDate);

		List<String> returnList = new ArrayList<String>();
		String a = "\uD840\udcd9";
		String b = "\uD840\udc59";
		returnList.add(a);
		returnList.add(b);

		ESubmissionListType eSubmitListType = esubBatchType.addNewESubmissionList();
		for (int i = 0; i < returnList.size(); i++) {
			eSubmitListType.addESubmission(returnList.get(i));
		}
		
		System.out.println("XXXXXXXXXXXXXXXXXXX#####zzz");
		logInfo("John Chan Checking**********************" + "logInfo is working in updCMUsers function");
		esubBatchType.setNoOfSubmission(new Integer(eSubmitListType.sizeOfESubmissionArray()).toString());

		return outDoc;

	}
}
