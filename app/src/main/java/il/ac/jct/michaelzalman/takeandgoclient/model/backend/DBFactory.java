package il.ac.jct.michaelzalman.takeandgoclient.model.backend;

import il.ac.jct.michaelzalman.takeandgoclient.model.DataSource.*;

/**
 * Created by zalman on 14/11/2017.
 */

public class DBFactory {
    private static IDBManager idbManager = null;

    public static IDBManager getIdbManager() {
        if(idbManager==null)idbManager = new MySQL_DBManager();

        return idbManager;
    }

    private DBFactory()
    {
    }
}
