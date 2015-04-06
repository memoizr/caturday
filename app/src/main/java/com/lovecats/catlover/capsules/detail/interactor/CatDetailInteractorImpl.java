package com.lovecats.catlover.capsules.detail.interactor;

import com.lovecats.catlover.models.comment.CommentEntity;
import com.lovecats.catlover.models.comment.repository.CommentRepository;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.vote.VoteEntity;
import com.lovecats.catlover.models.vote.repository.VoteRepository;
import com.lovecats.catlover.util.concurrent.PostExecutionThread;
import com.lovecats.catlover.util.concurrent.ThreadExecutor;
import com.lovecats.catlover.util.concurrent.WorkerCallback;

import rx.Observable;

public class CatDetailInteractorImpl implements CatDetailInteractor {

    private static final String TAG = CatDetailInteractorImpl.class.getCanonicalName();

    private final CatPostRepository catPostRepository;
    private final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public CatDetailInteractorImpl(CatPostRepository catPostRepository,
                                   UserRepository userRepository,
                                   CommentRepository commentRepository,
                                   VoteRepository voteRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {

        this.catPostRepository = catPostRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void getPostFromId(final String serverId, final WorkerCallback<CatPostEntity> callback) {
        threadExecutor.execute(() -> {
            final CatPostEntity catPostEntity = catPostRepository.getCatPost(serverId);
            postExecutionThread.post(() -> callback.done(catPostEntity));
        });
    }

    @Override
    public Observable<CommentEntity> sendComment(String comment, String catPostServerId) {
        UserEntity user = userRepository.getCurrentUser();

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentableId(catPostServerId);
        commentEntity.setCommentableType(CommentEntity.COMMENTABLE_TYPE_CAT_POST);
        commentEntity.setContent(comment);
        commentEntity.setUserId(user.getServerId());

        return commentRepository.sendComment(commentEntity);
    }

    @Override
    public Observable<VoteEntity> sendVote(String serverId) {

        UserEntity user = userRepository.getCurrentUser();

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteableId(serverId);
        voteEntity.setVoteableType(VoteEntity.VOTEABLE_TYPE_CAT_POST);
        voteEntity.setUserId(user.getServerId());

        addFavorite(serverId);

        return  voteRepository.sendVote(voteEntity);
    }

    private void addFavorite(String serverId) {
        System.out.println("adding favorite to local storage");
        userRepository.addFavoritePost(serverId);
    }


}
