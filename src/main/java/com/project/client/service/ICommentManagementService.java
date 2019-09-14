package com.project.client.service;

import java.util.List;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.Status;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.comment.PlaceComment;

public interface ICommentManagementService {
	long updatePlaceCommentStatus(long id, Status status);
	
	CommentResponse getPlaceComments(long placeId, Language language);
	
	List<PlaceComment> getPlaceComments(Language language);
	
	long savePlaceComment(Comment comment, long placeId);
	
	
}
