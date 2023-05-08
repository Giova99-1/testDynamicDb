package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMapper<T> {
    // This method is already implemented in package
// but as far as I know it accepts only public class attributes
    private void setProperty(Object c, String fieldName, Object columnValue) {
        try {
            // get all fields of the class (including public/protected/private)
            Field field = c.getClass().getDeclaredField(fieldName);
            // this is necessary in case the field visibility is set at private
            field.setAccessible(true);
            field.set(c, columnValue);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public List<T> mapRersultSetToObject(ResultSet rs, Class c) {
        List<T> outputList = null;
        try {
            // make sure resultset is not null
            if (rs != null) {
                // check if Class c has the 'Entity' annotation
                if (c.isAnnotationPresent(Entity.class)) {
                    // get the resultset metadata
                    ResultSetMetaData rsmd = rs.getMetaData();
                    // get all the attributes of Class c
                    Field[] fields = c.getDeclaredFields();
                    while (rs.next()) {
                        T bean = (T) c.newInstance();
                        for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
                            // get the SQL column name
                            String columnName = rsmd.getColumnName(_iterator + 1);
                            // get the value of the SQL column
                            Object columnValue = rs.getObject(_iterator + 1);
                            // iterating over c attributes to check
                            // if any attribute has 'Column' annotation with matching 'name' value
                            for (Field field : fields) {
                                if (field.isAnnotationPresent(Column.class)) {
                                    Column column = field.getAnnotation(Column.class);
                                    if (column.name().equalsIgnoreCase(columnName)
                                            && columnValue != null) {
                                        this.setProperty(bean, field.getName(), columnValue);
                                        break;
                                    }
                                }
                            } // EndOf for(Field field : fields)
                        } // EndOf for(_iterator...)
                        if (outputList == null) {
                            outputList = new ArrayList<T>();
                        }
                        outputList.add(bean);
                    } // EndOf while(rs.next())
                } else {
                    // throw some error that Class c
                    // does not have @Entity annotation
                }
            } else {
                // ResultSet is empty
                return null;
            }
        } catch (IllegalAccessException | SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return outputList;
    }
}