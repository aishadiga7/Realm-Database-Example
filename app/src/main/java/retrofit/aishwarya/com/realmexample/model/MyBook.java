package retrofit.aishwarya.com.realmexample.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by aishwarya on 29/6/16.
 */
public class MyBook extends RealmObject {
    @Required
    private String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
