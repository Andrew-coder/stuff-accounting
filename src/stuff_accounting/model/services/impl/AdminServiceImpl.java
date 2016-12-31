package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.AbstractConnection;
import stuff_accounting.model.dao.AdminDao;
import stuff_accounting.model.dao.DaoFactory;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Admin;
import stuff_accounting.model.services.AdminService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class AdminServiceImpl implements AdminService {
    private DaoFactory factory;
    private static AdminServiceImpl instance;

    private AdminServiceImpl(){
        factory= DaoFactoryImpl.getInstance();
    }

    public static synchronized AdminServiceImpl getInstance(){
        if(instance==null){
            instance = new AdminServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean checkAdminInfo(Admin admin) {
        AbstractConnection connection = factory.getConnection();
        try {
            AdminDao adminDao = factory.getAdminDao(connection);
            List<Admin> allAdmins = adminDao.getAll();
            connection.close();
            if (allAdmins.contains(admin))
                return true;
            return false;
        }
        catch (Exception ex){
            throw new RuntimeException("error when checking admin info");
        }
    }
}
