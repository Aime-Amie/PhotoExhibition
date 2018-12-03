package ru.uj.photoexhibition;

//import android.os.Bundle;
//import android.support.test.espresso.core.internal.deps.guava.cache.Cache;
//import android.support.test.espresso.core.internal.deps.guava.cache.CacheBuilder;
//
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * Created by ilgar on 9/12/2017.
// */
//
//public class PresenterHolder {
//    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
//    private static PresenterHolder instance;
//    private final AtomicLong currentId;
//    private final Cache<Long, BasePresenter<?>> presenters;
//
//    PresenterHolder(long maxSize, long expirationValue, TimeUnit expirationUnit) {
//        currentId = new AtomicLong();
//        presenters = CacheBuilder.newBuilder()
//                .maximumSize(maxSize)
//                .expireAfterWrite(expirationValue, expirationUnit)
//                .build();
//    }
//
//    public static PresenterHolder getInstance() {
//        if (instance == null) {
//            instance = new PresenterHolder(10, 30, TimeUnit.SECONDS);
//        }
//        return instance;
//    }
//
//    public <P extends BasePresenter<?>> P restorePresenter(Bundle savedInstanceState) {
//        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
//        P presenter = (P) presenters.getIfPresent(presenterId);
//        presenters.invalidate(presenterId);
//        return presenter;
//    }
//
//    public void savePresenter(BasePresenter<?> presenter, Bundle outState) {
//        long presenterId = currentId.incrementAndGet();
//        presenters.put(presenterId, presenter);
//        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
//    }
//}