package com.dv.ssss.ui.other;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static javafx.scene.control.TableColumn.CellDataFeatures;

public class MethodValueFactory<S, T> implements javafx.util.Callback<CellDataFeatures<S, T>, javafx.beans.value.ObservableValue<T>> {

    private final Method method;

    public MethodValueFactory(Method method) {

        this.method = method;
    }

    @Override
    public ObservableValue<T> call(CellDataFeatures<S, T> param) {

        return getCellDataReflectively((T) param.getValue());
    }

    private ObservableValue<T> getCellDataReflectively(T rowData) {

        try {
            return new ReadOnlyObjectWrapper<>((T) method.invoke(rowData));
        } catch (IllegalStateException | InvocationTargetException | IllegalAccessException e) {
            //TODO Add logging.
            // log the warning and move on
//            if (logger.isLoggable(PlatformLogger.Level.WARNING)) {
//                logger.finest("Can not retrieve through method '" + method +
//                        "' in MethodValueFactory: " + this +
//                        " with provided class type: " + rowData.getClass(), e);
//            }
        }

        return null;
    }
}
