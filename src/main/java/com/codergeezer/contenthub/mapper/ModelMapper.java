package com.codergeezer.contenthub.mapper;

import com.codergeezer.contenthub.dto.request.*;
import com.codergeezer.contenthub.dto.response.*;
import com.codergeezer.contenthub.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Mapper
public interface ModelMapper {

    EmailTemplate convertToEmailTemplate(EmailTemplateRequest emailTemplateRequest);

    EmailTemplate convertToEmailTemplate(@MappingTarget EmailTemplate emailTemplate,
                                         EmailTemplateRequest emailTemplateRequest);

    EmailTemplateResponse convertToEmailTemplateResponse(EmailTemplate emailTemplate);

    List<EmailTemplateResponse> convertToEmailTemplateResponses(List<EmailTemplate> emailTemplates);

    Album convertToAlbum(AlbumRequest albumRequest);

    Album convertToAlbum(@MappingTarget Album album, AlbumRequest albumRequest);

    AlbumResponse convertToAlbumResponse(Album album);

    List<AlbumResponse> convertToAlbumResponses(List<Album> albums);

    Post convertToPost(PostRequest postRequest);

    Post convertToPost(@MappingTarget Post post, PostRequest postRequest);

    PostResponse convertToPostResponse(Post post);

    List<PostResponse> convertToPostResponses(List<Post> posts);

    Tier convertToTier(TierRequest tierRequest);

    Tier convertToTier(@MappingTarget Tier tier, TierRequest tierRequest);

    TierResponse convertToTierResponse(Tier tier);

    List<TierResponse> convertToTierResponses(List<Tier> tiers);

    Comment convertToComment(CommentRequest commentRequest);

    CommentResponse convertToCommentResponse(Comment comment);

    List<CommentResponse> convertToCommentResponses(List<Comment> comments);

    DictionaryResponse convertToDictionaryResponse(Dictionary dictionary);

    List<DictionaryResponse> convertToDictionaryResponses(List<Dictionary> dictionaries);

    Group convertToGroup(GroupRequest groupRequest);

    GroupResponse convertToGroupResponse(Group group);

    List<GroupResponse> convertToGroupResponses(List<Group> groups);
}
