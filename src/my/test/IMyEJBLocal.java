package my.test;

import hk.gov.lad.webservices.type.LAPSubmissionRequestDocument;
import hk.gov.lad.webservices.type.LAPSubmissionResponseDocument;

import javax.ejb.EJBException;
import javax.ejb.Local;

@Local
public interface IMyEJBLocal {
	public LAPSubmissionResponseDocument getLAPSubmission(LAPSubmissionRequestDocument lAPSubmissionRequest)
			throws EJBException;
}
