package com.caturday.app.capsules.dashboard.stream.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.repository.UserRepository;
import com.caturday.app.models.vote.VoteEntity;
import com.caturday.app.models.vote.repository.VoteRepository;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CatStreamInteractorImpl implements CatStreamInteractor {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final String CAT_POST = "CatPost";
    CatPostRepository catPostRepository;

    public CatStreamInteractorImpl(CatPostRepository catPostRepository,
                                   VoteRepository voteRepository,
                                   UserRepository userRepository) {

        this.catPostRepository = catPostRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Observable<List<CatPostEntity>> getCatPostPageAndType(final int page,
                                                                 final String id,
                                                                 boolean byUser) {

        if (byUser)
            return catPostRepository.getCatPostsForPageAndUserId(page, id, false);
        else
            return catPostRepository.getCatPostsForPageAndCategory(page, id, false);
    }

    @Override
    public void eraseCache() {
        catPostRepository.eraseCache();
    }

    @Override
    public Observable<CatPostEntity> getCatPost(String serverId) {
        return catPostRepository.getCatPost(serverId, false);
    }

    @Override
    public Observable<CatPostEntity> catPostVoted(String serverId) {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setPositive(true);
        voteEntity.setVoteableType(CAT_POST);
        voteEntity.setVoteableId(serverId);
        return voteRepository.sendVote(voteEntity)
                .flatMap(vote -> catPostRepository.getCatPost(serverId, true))
                .doOnNext(catPostRepository::updateCatPost);
    }

    @Override
    public boolean userLoggedIn() {
        return userRepository.userLoggedIn();
    }

    @Override
    public UserEntity getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    @Override
    public Observable<Object> deletePost(String postId) {
        return catPostRepository.deletePost(postId);
    }
}
