package com.DataAccessLayer.NonAnomaly;

import java.util.ArrayList;

public interface INonAnomalyDao {

    boolean create (NonAnomalyBean toCreate);

    ArrayList<NonAnomalyBean> readAll();

    NonAnomalyBean read(int idToRead);

    boolean update(NonAnomalyBean toUpdate);

    boolean delete(int userId);

}
