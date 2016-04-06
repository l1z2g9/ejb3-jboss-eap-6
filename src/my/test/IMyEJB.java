package my.test;

import hk.gov.lad.webservices.type.LAPSubmissionRequestDocument;
import hk.gov.lad.webservices.type.LAPSubmissionResponseDocument;

import javax.ejb.EJBException;
import javax.ejb.Remote;

@Remote
public interface IMyEJB {
	public LAPSubmissionResponseDocument getLAPSubmission(LAPSubmissionRequestDocument lAPSubmissionRequest)
			throws EJBException;
}
