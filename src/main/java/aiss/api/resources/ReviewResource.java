package aiss.api.resources;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.Review;

public class ReviewResource {

	private String uri = "https://proyecto-integracion-313615.appspot.com/api/reviews";
		
	public Review getReview(Integer reviewId) {
		
		ClientResource cr = null;
		Review list = null;
		String reviewIdString = String.valueOf(reviewId);
		try {
			cr = new ClientResource(uri + "/" + reviewIdString);
			list = cr.get(Review.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the review: " + cr.getResponse().getStatus());
		}
		
		return list;

	}
	

	public Review addReview(Review r) {
		
		ClientResource cr = null;
		Review resultReview = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			resultReview = cr.post(r,Review.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when adding the review: " + cr.getResponse().getStatus());
		}
		
		return resultReview;
	}
	

	public boolean updateReview(Review r) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(r);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the review: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	
	public boolean deleteReview(Integer reviewId) {
		ClientResource cr = null;
		boolean success = true;
		String reviewIdString = String.valueOf(reviewId);
		try {
			cr = new ClientResource(uri + "/" + reviewIdString);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the review: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
}
