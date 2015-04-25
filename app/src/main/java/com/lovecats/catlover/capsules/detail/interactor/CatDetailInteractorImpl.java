package com.lovecats.catlover.capsules.detail.interactor;

import com.lovecats.catlover.models.comment.CommentEntity;

import com.lovecats.catlover.models.comment.repository.CommentRepository;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.vote.VoteEntity;
import com.lovecats.catlover.models.vote.repository.VoteRepository;

import rx.Observable;

public class CatDetailInteractorImpl implements CatDetailInteractor {

    private static final String TAG = CatDetailInteractorImpl.class.getCanonicalName();

    private final CatPostRepository catPostRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public CatDetailInteractorImpl(CatPostRepository catPostRepository,
                                   UserRepository userRepository,
                                   CommentRepository commentRepository,
                                   VoteRepository voteRepository) {

        this.catPostRepository = catPostRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Observable<CatPostEntity> getPostFromId(final String serverId) {
            return catPostRepository.getCatPost(serverId);
    }

    @Override
    public Observable<CatPostEntity> sendComment(String comment, String catPostServerId) {
        UserEntity user = userRepository.getCurrentUser();

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentableId(catPostServerId);
        commentEntity.setCommentableType(CommentEntity.COMMENTABLE_TYPE_CAT_POST);
        commentEntity.setContent(comment);
        commentEntity.setUserId(user.getServerId());

        return commentRepository.sendComment(commentEntity);
    }

    @Override
    public Observable<VoteEntity> sendVote(String serverId, boolean positive) {

        UserEntity user = userRepository.getCurrentUser();

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteableId(serverId);
        voteEntity.setVoteableType(VoteEntity.VOTEABLE_TYPE_CAT_POST);
        voteEntity.setUserId(user.getServerId());
        voteEntity.setPositive(positive);

        if (positive)
            addFavorite(serverId);
        else
            removeFavorite(serverId);

        return  voteRepository.sendVote(voteEntity);
    }

    @Override
    public Observable<Boolean> isFavorite(String serverId) {
        return userRepository.getAllFavoritePost()
                .flatMap(Observable::from)
                .filter(s -> s.equals(serverId))
                .count()
                .map(count -> count > 0);
    }

    private void addFavorite(String serverId) {
        userRepository.addFavoritePost(serverId);
    }

    private void removeFavorite(String serverId) {
        userRepository.removeFavoritePost(serverId);
    }


}
