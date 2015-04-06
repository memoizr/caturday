package com.lovecats.catlover.ui.detail.interactor;

import android.util.Log;

import com.lovecats.catlover.ui.detail.data.CommentEntity;
import com.lovecats.catlover.ui.detail.data.repository.CommentRepository;
import com.lovecats.catlover.ui.profile.data.UserEntity;
import com.lovecats.catlover.ui.profile.data.repository.UserRepository;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.repository.CatPostRepository;
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

    public CatDetailInteractorImpl(CatPostRepository catPostRepository,
                                   UserRepository userRepository,
                                   CommentRepository commentRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {

        this.catPostRepository = catPostRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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
        Log.v(TAG, comment + " " + catPostServerId);

        UserEntity user = userRepository.getCurrentUser();

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentableId(catPostServerId);
        commentEntity.setCommentableType(CommentEntity.COMMENTABLE_TYPE_CAT_POST);
        commentEntity.setContent(comment);
        commentEntity.setUserId(user.getServerId());

        threadExecutor.execute(() -> {
            commentRepository.sendComment(commentEntity);
            postExecutionThread.post(() -> System.out.println("achieved"));
        });

        return commentRepository.sendComment(commentEntity);
    }
}
