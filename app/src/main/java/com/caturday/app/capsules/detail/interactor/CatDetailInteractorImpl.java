package com.caturday.app.capsules.detail.interactor;

import com.caturday.app.models.comment.CommentEntity;

import com.caturday.app.models.comment.repository.CommentRepository;
import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.repository.UserRepository;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.vote.VoteEntity;
import com.caturday.app.models.vote.repository.VoteRepository;

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
    public Observable<CatPostEntity> getPostFromId(final String serverId, boolean fromNetwork) {
            return catPostRepository.getCatPost(serverId, fromNetwork);
    }

    @Override
    public boolean isUserLoggedIn() {
        return userRepository.userLoggedIn();
    }

    @Override
    public Observable<CatPostEntity> sendComment(String comment, String catPostServerId) {

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentableId(catPostServerId);
        commentEntity.setCommentableType(CommentEntity.COMMENTABLE_TYPE_CAT_POST);
        commentEntity.setContent(comment);

        return commentRepository.sendComment(commentEntity)
                .doOnNext(catPostRepository::updateCatPost);
    }

    @Override
    public Observable<VoteEntity> sendVote(String serverId, boolean positive) {

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteableId(serverId);
        voteEntity.setVoteableType(VoteEntity.VOTEABLE_TYPE_CAT_POST);
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
