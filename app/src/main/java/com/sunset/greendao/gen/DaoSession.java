package com.sunset.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.song.sunset.beans.Coder;
import com.song.sunset.beans.User;

import com.sunset.greendao.gen.CoderDao;
import com.sunset.greendao.gen.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig coderDaoConfig;
    private final DaoConfig userDaoConfig;

    private final CoderDao coderDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        coderDaoConfig = daoConfigMap.get(CoderDao.class).clone();
        coderDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        coderDao = new CoderDao(coderDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Coder.class, coderDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        coderDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public CoderDao getCoderDao() {
        return coderDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}