package com.lovecats.catlover.capsules.newpost.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.repository.UserRepository;

import rx.Observable;

public class NewPostInteractorImpl implements NewPostInteractor {

    private final CatPostRepository catPostRepository;
    private final UserRepository userRepository;

    public NewPostInteractorImpl(UserRepository userRepository,
                                 CatPostRepository catPostRepository) {
        this.userRepository = userRepository;
        this.catPostRepository = catPostRepository;
    }

    @Override
    public Observable<CatPostEntity> createPost(CatPostEntity catPostEntity) {
        return catPostRepository.createPost(catPostEntity);
    }
}
