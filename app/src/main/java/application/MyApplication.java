package application;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import models.Dog;
import models.Person;

public class MyApplication extends Application {

    public static AtomicInteger PersonID = new AtomicInteger();
    public static AtomicInteger DogID = new AtomicInteger();


    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration
                .Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);


        Realm realm = Realm.getInstance(config);
        PersonID = setAtomicId(realm, Person.class);
        DogID = setAtomicId(realm, Dog.class);

        realm.close();


    }


    private <T extends RealmObject> AtomicInteger setAtomicId(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("Id").intValue()) : new AtomicInteger();
    }


}