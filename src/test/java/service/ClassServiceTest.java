package service;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.TransformationList;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassServiceTest {

    @Test
    public void test(){
        ClassService classService = ClassServiceImpl.getInstance();
        List<Class<?>> classes = new ArrayList<>();
        classes.add(List.class);
        classes.add(Collection.class);
        classes.add(FilteredList.class);
        classes.add(TransformationList.class);
        classes.add(Cloneable.class);
        List<Class<?>> sortedClasses = classService.sort(classes);
        Assert.assertArrayEquals(classes.toArray(), sortedClasses.toArray());
    }

}
