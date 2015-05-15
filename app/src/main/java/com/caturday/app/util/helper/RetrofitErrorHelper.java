package com.caturday.app.util.helper;

import retrofit.RetrofitError;
import rx.Observable;

public class RetrofitErrorHelper {

    public static Observable<String> formatErrorMessageRx(RetrofitError e) {

        NetworkError error = (NetworkError) e.getBodyAs(NetworkError.class);
        if (error.getMessage() != null) {
            return Observable.just(error.getMessage());
        } else {
            return Observable.just(error)
                    .flatMap(s -> Observable.from(error.getMessages().entrySet()))
                    .flatMap(set -> {
                        Observable<String> title = Observable.just(set.getKey() + " ");
                        Observable<String> description = Observable.from(set.getValue().getAsJsonArray())
                                .map(value -> value.getAsString())
                                .reduce((head, tail) -> head + ", " + tail)
                                .map(line -> line + "\n");
                        return title.mergeWith(description);
                    })
                    .reduce((head, tail) -> head + tail);
        }
    }
}
