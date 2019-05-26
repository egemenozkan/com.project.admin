package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.Status;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.PlaceComment;
import com.project.api.data.model.comment.PlaceCommentResponse;
import com.project.client.service.ICommentManagementService;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class CommentManagementService extends BaseApiService implements ICommentManagementService {

	@Value("${com.project.api-uri}")
	private String API_URL;

	@Override
	public PlaceCommentResponse getPlaceComments(long id, Language language) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/places/{id}/comments");
		endpoint.append("?language=");
		endpoint.append(language.getCode());

		return (PlaceCommentResponse) getObject(endpoint.toString(), PlaceCommentResponse.class, id);
	}

	@Override
	public List<PlaceComment> getPlaceComments(Language language) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/places/comments");
		endpoint.append("?language=");
		endpoint.append(language.getCode());
		
		return (List<PlaceComment>) getList(endpoint.toString());
	}

	@Override
	public long savePlaceComment(Comment comment, long placeId) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/places/");
		endpoint.append(String.valueOf(placeId));
		endpoint.append("/comments");
		PlaceComment placeComment = (PlaceComment) postObject(endpoint.toString(), comment, PlaceComment.class);
		return placeComment.getId();
		
	}

	@Override
	public long updatePlaceCommentStatus(long id, Status status) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/places/comments/");
		endpoint.append(String.valueOf(id));
		endpoint.append("/status");
		postObject(endpoint.toString(), status, Long.class);
		return 0;
	}

}
