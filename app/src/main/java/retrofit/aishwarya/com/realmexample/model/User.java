package retrofit.aishwarya.com.realmexample.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by aishwarya on 28/6/16.
 */
public class User extends RealmObject {
    private String mName;
    private int mAge;
    @Ignore
    private int mSessionId;

    public int getSessionId() {
        return mSessionId;
    }

    public void setSessionId(int sessionId) {
        mSessionId = sessionId;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
